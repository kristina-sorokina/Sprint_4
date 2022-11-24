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
}
