import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePageFAQ;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HomePageFAQTest extends BaseUrlTest {
    private final Logger logger = LogManager.getLogger(HomePageFAQTest.class);
    private WebDriver driver;
    private HomePageFAQ homePageFAQ;

    private final List<String> expectedAnswers = new ArrayList<>(
            List.of("Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
                    "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто " +
                            "сделать несколько заказов — один за другим.",
                    "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени " +
                            "аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 " +
                            "мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
                    "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
                    "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
                    "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете " +
                            "кататься без передышек и во сне. Зарядка не понадобится.",
                    "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
                    "Да, обязательно. Всем самокатов! И Москве, и Московской области."
            )
    );

    private final List<String> expectedQuestions = new ArrayList<>(
            List.of("Сколько это стоит? И как оплатить?",
                    "Хочу сразу несколько самокатов! Так можно?",
                    "Как рассчитывается время аренды?",
                    "Можно ли заказать самокат прямо на сегодня?",
                    "Можно ли продлить заказ или вернуть самокат раньше?",
                    "Вы привозите зарядку вместе с самокатом?",
                    "Можно ли отменить заказ?",
                    "Я живу за МКАДом, привезёте?"
            )
    );

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Before
    public void setUp() {
        driver = getDriver("chrome");
        homePageFAQ = new HomePageFAQ(driver);
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void checkSubHeaderText() {
        String expectedText = "Вопросы о важном";

        String actualText = homePageFAQ.getSubHeaderText();

        assertEquals(expectedText, actualText);
    }

    @Test
    public void checkNumberOfQuestions() {
        int expectedNumber = 8;

        int actualNumber = homePageFAQ.getNumberOfQuestions();

        assertEquals(expectedNumber, actualNumber);
    }

    @Test
    public void checkAnswerTextWhenClickedOnQuestion() {
        List<WebElement> actualQuestions = homePageFAQ.getQuestions();

        for (int i = 0; i < actualQuestions.size(); i++) {
            WebElement actualQuestion = actualQuestions.get(i);
            String actualQuestionText = actualQuestion.getText();

            jsClick(actualQuestion);

            String answerLocator = String.format(".//div[@id='accordion__panel-%s']", i);
            WebElement answer = driver.findElement(By.xpath(answerLocator));
            waitUntilVisibility(answer);

            String answerText = answer.getText();

            String errorMessage = String.format("Answer to question '%s' is not as expected", actualQuestionText);
            softly.assertThat(answerText).as(errorMessage).isEqualTo(expectedAnswers.get(i));

            logger.info(String.format("Checking answer to question '%s'", actualQuestionText));
        }
    }

    @Test
    public void checkQuestionText() {
        List<WebElement> actualQuestionElements = homePageFAQ.getQuestions();

        for (int i = 0; i < actualQuestionElements.size(); i++) {
            String actualQuestionText = actualQuestionElements.get(i).getText();
            String expectedQuestionText = expectedQuestions.get(i);

            softly.assertThat(actualQuestionText)
                    .as("The text of the question is not as expected")
                    .isEqualTo(expectedQuestionText);
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void jsClick(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", element);
    }

    private void waitUntilVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
