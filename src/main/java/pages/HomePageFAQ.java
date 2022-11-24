package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePageFAQ {
    private final WebDriver driver;
    private final By subHeader = By.xpath(".//div[@class='Home_FourPart__1uthg']/div[@class='Home_SubHeader__zwi_E']");
    private final By questionItem = By.xpath(".//div[@class='accordion']/div[@class='accordion__item']");

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

    public HomePageFAQ(WebDriver driver) {
        this.driver = driver;
    }

    public String getSubHeaderText() {
        return driver.findElement(subHeader).getText();
    }

    public int getNumberOfQuestions() {
        List<WebElement> actualQuestionsList = driver.findElements(questionItem);
        return actualQuestionsList.size();
    }

    public List<WebElement> getQuestions() {
        List<WebElement> questions = new ArrayList<>();

        int actualNumberOfQuestions = getNumberOfQuestions();

        for (int i = 0; i < actualNumberOfQuestions; i++) {
            String locator = String.format(".//div[@id='accordion__heading-%s']", i);
            questions.add(driver.findElement(By.xpath(locator)));
        }

        return questions;
    }

    public List<String> getExpectedAnswers() {
        return expectedAnswers;
    }

    public List<String> getExpectedQuestions() {
        return expectedQuestions;
    }
}
