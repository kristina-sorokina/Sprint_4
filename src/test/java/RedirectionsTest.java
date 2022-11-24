import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.HomePage;

import static org.junit.Assert.assertEquals;

public class RedirectionsTest extends BaseUrlTest {
    private WebDriver driver;
    private HomePage homePage;

    @Before
    public void setUp() {
        driver = getDriver("chrome");
        homePage = new HomePage(driver);
        driver.manage().window().maximize();
    }

    @Test
    public void clickYandexLogo() {
        String expectedTitle = "Яндекс";

        driver.get("https://qa-scooter.praktikum-services.ru/");
        homePage.clickYandexLogoButton();
        String actualTitle = driver.getTitle();

        assertEquals("Was expected to be redirected to 'Яндекс'", expectedTitle, actualTitle);
    }

    @Test
    public void clickScooterLogo() {
        String expectedUrl = "https://qa-scooter.praktikum-services.ru/";

        driver.get("https://qa-scooter.praktikum-services.ru/order");
        homePage.clickScooterLogoButton();
        String actualUrl = driver.getCurrentUrl();

        assertEquals("Was expected to be redirected to 'qa-scooter.praktikum-services.ru'", expectedUrl, actualUrl);
    }

    @After
    public void tearDown() {
        if(driver != null) {
            driver.quit();
        }
    }
}
