package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By headerOrderButton = By.xpath(".//button[@class='Button_Button__ra12g']");
    private final By mainOrderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_UltraBig__UU3Lp']");
    private final By yandexLogoButton = By.xpath(".//div/a[@class='Header_LogoYandex__3TSOI']");
    private final By scooterLogoButton = By.xpath(".//div/a[@class='Header_LogoScooter__3lsAR']");


    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickOrderButton(String buttonChoice) {
        switch (buttonChoice) {
            case "header" :
                driver.findElement(headerOrderButton).click();
                break;
            case "main" :
                WebElement mainOderButton = driver.findElement(mainOrderButton);
                ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", mainOderButton);
                wait.until(ExpectedConditions.elementToBeClickable(mainOderButton));
                mainOderButton.click();
                break;
        }
    }

    public void clickYandexLogoButton() {
        driver.findElement(yandexLogoButton).click();
    }

    public void clickScooterLogoButton() {
        driver.findElement(scooterLogoButton).click();
    }
}
