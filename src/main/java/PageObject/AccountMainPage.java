package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * страница доступная после авторизации пользователя (личный кабинет)
 */
public class AccountMainPage {

    private WebDriver driver;

    public AccountMainPage(WebDriver driver) {
        this.driver = driver;
    }

    private By myResume = By.xpath("//a[contains(@data-qa,'myResumes')]");

    public MyResume MyResumeClick() {
        driver.findElement(myResume).click();
        return new MyResume(driver);
    }

}
