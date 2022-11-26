import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.OrderPage;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class ErrorMessagesTest extends BaseUrlTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private HomePage homePage;
    private OrderPage orderPage;

    @Before
    public void setUp() {
        driver = getDriver("chrome");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        homePage = new HomePage(driver);
        orderPage= new OrderPage(driver);
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        homePage.clickOrderButton("header");
        orderPage.waitUntilOrderFormVisibility();
    }

    @Test
    public void checkNameFieldErrorMessage() {
        orderPage.enterName("hxdxdxd");

        WebElement nameFieldError = orderPage.getNameFieldError();
        wait.until(ExpectedConditions.visibilityOf(nameFieldError));

        String actualErrorMessage = nameFieldError.getText();
        String expectedErrorMessage = "Введите корректное имя";

        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void checkSurnameFieldErrorMessage() {
        orderPage.enterSurname("mdgwr");

        WebElement surnameFieldError = orderPage.getSurnameFieldError();
        wait.until(ExpectedConditions.visibilityOf(surnameFieldError));

        String actualErrorMessage = surnameFieldError.getText();
        String expectedErrorMessage = "Введите корректную фамилию";

        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void checkDeliveryAddressFieldErrorMessage() {
        orderPage.enterDeliveryAddress("%6h&jh");

        WebElement deliveryAddressFieldError = orderPage.getDeliveryAddressFieldError();
        wait.until(ExpectedConditions.visibilityOf(deliveryAddressFieldError));

        String actualErrorMessage = deliveryAddressFieldError.getText();
        String expectedErrorMessage = "Введите корректный адрес";

        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void checkMetroStationFieldErrorMessage() {
        orderPage.clickContinue();

        WebElement metroStationFieldError = orderPage.getMetroStationFieldError();
        wait.until(ExpectedConditions.visibilityOf(metroStationFieldError));

        String actualErrorMessage = metroStationFieldError.getText();
        String expectedErrorMessage = "Выберите станцию";

        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void checkPhoneNumberFieldErrorMessage() {
        orderPage.enterPhone("123");

        WebElement phoneNumberFieldError = orderPage.getPhoneNumberFieldError();
        wait.until(ExpectedConditions.visibilityOf(phoneNumberFieldError));

        String actualErrorMessage = phoneNumberFieldError.getText();
        String expectedErrorMessage = "Введите корректный номер";

        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
