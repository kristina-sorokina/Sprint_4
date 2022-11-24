package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By orderForm = By.xpath(".//div[@class='Order_Content__bmtHS']");
    private final By inputName = By.xpath(".//input[@placeholder='* Имя']");
    private final By inputSurname = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By inputDeliveryAddress = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By inputMetroStation = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By inputPhoneNumber = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By continueButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private final By inputDeliveryDate = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By datePicker = By.xpath(".//div[@class='react-datepicker__month-container']");
    private final By inputRentPeriod = By.xpath(".//div[@class='Dropdown-placeholder']");
    private final By rentPeriodMenu = By.xpath(".//div[@class='Dropdown-menu']");
    private final By checkBoxBlack = By.xpath(".//input[@id='black']");
    private final By checkBoxGrey = By.xpath(".//input[@id='grey']");
    private final By inputComment = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private final By orderModalWindow = By.xpath(".//div[@class='Order_Modal__YZ-d3']");
    private final By orderModalHeader = By.xpath(".//div[@class='Order_ModalHeader__3FDaJ']");
    private final By yesOrderButton = By.xpath(".//button[text()='Да']");
    private final By noOrderButton = By.xpath(".//button[text()='Нет']");
    private final By nameFieldError = By.xpath(".//input[@placeholder='* Имя']/following-sibling::div");
    private final By surnameFieldError = By.xpath(".//input[@placeholder='* Фамилия']/following-sibling::div");
    private final By deliveryAddressFieldError = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']/following-sibling::div");
    private final By metroStationFieldError = By.xpath(".//div[@class='Order_MetroError__1BtZb']");
    private final By phoneNumberFieldError = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']/following-sibling::div");

    private final String dateLabel = "//div[contains(@aria-label, '%s')]";
    private final String timePeriodText = ".//div[contains(text(), '%s')]";

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public OrderPage enterName(String name) {
        driver.findElement(inputName).sendKeys(name + Keys.TAB);
        return this;
    }

    public OrderPage enterSurname(String surname) {
        driver.findElement(inputSurname).sendKeys(surname + Keys.TAB);
        return this;
    }

    public OrderPage enterDeliveryAddress(String deliveryAddress) {
        driver.findElement(inputDeliveryAddress).sendKeys(deliveryAddress + Keys.TAB);
        return this;
    }

    public OrderPage enterMetroStation(String metroStation) {
        WebElement inputField = driver.findElement(inputMetroStation);
        inputField.sendKeys(metroStation);
        Actions actions = new Actions(driver);
        actions.sendKeys(inputField, Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
        return this;
    }

    public OrderPage enterPhone(String phone) {
        driver.findElement(inputPhoneNumber).sendKeys(phone + Keys.TAB);
        return this;
    }

    public OrderPage clickContinue() {
        driver.findElement(continueButton).click();
        return this;
    }

    public OrderPage enterDeliveryDate(LocalDate deliveryDate) {
        String formattedDeliveryDate = formatDate(deliveryDate);
        driver.findElement(inputDeliveryDate).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(datePicker)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()",
                driver.findElement(By.xpath(String.format(dateLabel, formattedDeliveryDate))));
        return this;
    }

    public OrderPage waitUntilOrderFormVisibility() {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(orderForm)));
        return this;
    }

    public OrderPage enterRentPeriod(String timePeriod) {
        driver.findElement(inputRentPeriod).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(rentPeriodMenu)));
        driver.findElement(By.xpath(String.format(timePeriodText, timePeriod))).click();
        return this;
    }

    public OrderPage choseColor(String color) {
        switch (color) {
            case "чёрный":
                driver.findElement(checkBoxBlack).click();
                break;
            case "серый":
                driver.findElement(checkBoxGrey).click();
                break;
            default:
                break;
        }
        return this;
    }

    public OrderPage enterComment(String comment) {
        if ("".equals(comment)) {
            return this;
        }
        driver.findElement(inputComment).sendKeys(comment);
        return this;
    }

    public OrderPage clickOrderButton() {
        driver.findElement(orderButton).click();
        return this;
    }

    public OrderPage clickYes() {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(orderModalWindow)));
        driver.findElement(yesOrderButton).click();
        return this;
    }

    public String getConfirmationText() {
        WebElement confirmationHeader = driver.findElement(orderModalHeader);
        wait.until(ExpectedConditions.visibilityOf(confirmationHeader));
        return confirmationHeader.getText();
    }

    public WebElement getNameFieldError() {
        return driver.findElement(nameFieldError);
    }

    public WebElement getSurnameFieldError() {
        return driver.findElement(surnameFieldError);
    }

    public WebElement getDeliveryAddressFieldError() {
        return driver.findElement(deliveryAddressFieldError);
    }

    public WebElement getMetroStationFieldError() {
        return driver.findElement(metroStationFieldError);
    }

    public WebElement getPhoneNumberFieldError() {
        return driver.findElement(phoneNumberFieldError);
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-е MMMM yyyy г.");
        return date.format(formatter);
    }
}
