import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.OrderPage;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest extends BaseUrlTest {
    private WebDriver  driver;
    private OrderPage orderPage;
    private HomePage homePage;

    private final String buttonChoice;
    private final String name;
    private final String surname;
    private final String address;
    private final String metroStation;
    private final String phoneNumber;
    private final LocalDate deliveryDate;
    private final String rentPeriod;
    private final String color;
    private final String comment;

    public OrderTest(String buttonChoice, String name, String surname, String address, String metroStation,
                     String phoneNumber, LocalDate deliveryDate, String rentPeriod,
                     String color, String comment) {
        this.buttonChoice = buttonChoice;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroStation = metroStation;
        this.phoneNumber = phoneNumber;
        this.deliveryDate = deliveryDate;
        this.rentPeriod = rentPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                // happy path - all fields filled out
                {"header", "Иван", "Иванов", "Москва, Цветочная 7", "Лубян", "12365478952",
                        LocalDate.now().plusDays(2), "трое суток", "чёрный", "Тестовый комментарий 1"},
                // happy path - no comment left
                {"main", "анна", "сидорова", "шереметьевская 786", "чистые пр", "78965412302",
                        LocalDate.now().plusDays(1), "двое суток", "серый", ""},
                // happy path - long surname, double name, no color choice
                {"header", "Анна Мария ", "Оооченьдлиннаяфамилия", "Москва, улица Карла Марква, дом 123", "Площадь рев",
                        "+79215554454", LocalDate.now().plusDays(3), "шестеро суток", "", "Тестовый комментарий 2"}
        };
    }

    @Before
    public void setUp() {
        driver = getDriver("chrome");
        orderPage = new OrderPage(driver);
        homePage = new HomePage(driver);
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void orderSubmissionHappyPathTest() {
        homePage.clickOrderButton(buttonChoice);

        String actualText = orderPage.waitUntilOrderFormVisibility()
                .enterName(name)
                .enterSurname(surname)
                .enterDeliveryAddress(address)
                .enterMetroStation(metroStation)
                .enterPhone(phoneNumber)
                .clickContinue()
                .enterDeliveryDate(deliveryDate)
                .enterRentPeriod(rentPeriod)
                .choseColor(color)
                .enterComment(comment)
                .clickOrderButton()
                .clickYes()
                .getConfirmationText();

        String expectedText = "Заказ оформлен";

        assertTrue(String.format("Expected success message should contain '%s'", expectedText),
                actualText.contains(expectedText));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
