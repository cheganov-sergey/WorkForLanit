package PageObject;

import PageObject.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Шлавная страница hh.ru
 */
public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private By login = By.xpath("(//a[@data-qa='login'])[2]");

    public LoginPage LoginFromMainPage() {
        this.driver.findElement(login).click();
        return new LoginPage(driver);
    }
}
