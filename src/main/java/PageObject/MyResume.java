package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class MyResume {

    private WebDriver driver;

    public MyResume(WebDriver driver) {
        this.driver = driver;
    }

    private By headText = By.xpath("//h1[@data-qa='page-title'][contains(.,'Мои резюме')]");
    private By createResume = By.xpath("//a[@class='bloko-button bloko-button_stretched'][contains(.,'Создать резюме')]");

    public String GetHeader() {
        return driver.findElement(headText).getText();
    }

    public NewResume CreateResume() {
        driver.findElement(createResume).click();
        return new NewResume(driver);
    }

}
