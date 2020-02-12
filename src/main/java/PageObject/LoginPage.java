package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Страница авторизации пользователя
 */
public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public  LoginPage LoginInvalid(String name, String pass) {
        inputUserName(name);
        inputPasswod(pass);
        driver.findElement(singIn).click();
        return this;
    }

    private By userName = By.xpath("//input[@data-qa='login-input-username']");
    private By password = By.xpath("//input[@data-qa='login-input-password']");
    private By singIn = By.xpath("//input[@data-qa='account-login-submit']");
    private By errorName = By.xpath("//div[@data-message-name='username']");

    public LoginPage inputUserName(String name) {
        driver.findElement(userName).sendKeys(name);
        return this;
    }

    public LoginPage inputPasswod (String pass) {
        driver.findElement(password).sendKeys(pass);
        return this;
    }

    public AccountMainPage LogIn() {
        driver.findElement(singIn).click();
        return new AccountMainPage(driver);
    }

    public String GetErrorUserName() {
        return driver.findElement(errorName).getText();
    }


}
