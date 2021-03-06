package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Страница "Мои резюме"
 */
public class MyResume {

    private WebDriver driver;

    public MyResume(WebDriver driver) {
        this.driver = driver;
    }

    private By createResume = By.xpath("//a[@class='bloko-button bloko-button_stretched'][contains(.,'Создать резюме')]");

    public NewResume CreateResume() {
        driver.findElement(createResume).click();
        return new NewResume(driver);
    }

}
