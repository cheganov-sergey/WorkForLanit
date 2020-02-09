package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Класс страницы создания нового резюме
 */
public class NewResume {

    private WebDriver driver;

    public NewResume(WebDriver driver) {
        this.driver = driver;
    }

    private By firstName = By.xpath("//input[@name='firstName[0].string']");
    private By firstNameError = By.xpath("//input[contains(@name,'firstName[0].string')]/ancestor::div[@class = 'bloko-control-group__vertical-item']//div[@class = 'bloko-form-error bloko-form-error_entered']");
    private By lastName = By.xpath("//input[@name='lastName[0].string']");
    private By lastNameError = By.xpath("(//div[contains(@class,'bloko-form-error bloko-form-error_entered')])[2]");
    private By city = By.xpath("//input[@data-qa='suggestCity resume-city']");
    private By cityError = By.xpath("(//div[contains(@class,'bloko-form-error bloko-form-error_entered')])[5]");
    private By birthDay = By.xpath("//input[@data-qa='resume__birthday__day']");
    private By month = By.xpath("//select[@data-qa='resume__birthday__month-select']");
    private By birthYear = By.xpath("//input[@data-qa='resume__birthday__year']");
    private By dateError = By.xpath("//div[@class='bloko-form-error bloko-form-error_entered'][contains(.,'Некорректная дата')]");
    private By maleButton = By.xpath("//input[@name = 'gender[0].string'][@value = 'male']");
    private By femaleButton = By.xpath("//input[@name = 'gender[0].string'][@value = 'female']");
    private By noExperienceButton = By.xpath("//span[@class='bloko-radio__text'][contains(.,'Нет опыта работы')]");
    private By yesExperienceButton = By.xpath("//span[@class='bloko-radio__text'][contains(.,'Есть опыт работы')]");
    private By ExperiencError = By.xpath("(//div[@class='bloko-form-error bloko-form-error_entered'])[1]");

    /**
     * задаем имя
     * @param name  Имя соискателя
     * @return  текущая страница
     */
    public NewResume SetFirstName(String name) {
        driver.findElement(firstName).clear();
        driver.findElement(firstName).sendKeys(name);
        driver.findElement(firstName).sendKeys(Keys.TAB);
        return this;
    }

    /**
     * проверяем на наличие ошибки при вводе имени
     * @return вебэлемент с ошибкой
     */
    public WebElement GetErrorInvalidName() {
       return driver.findElement(firstNameError);
    }

    /**
     * ввод фамилии
     * @param name фамилия соискателя
     * @return  текущая страница
     */
    public NewResume SetLastName(String name) {
       driver.findElement(lastName).sendKeys(name);
       return this;
    }

    /**
     * проверка на наличие ошибки при вводе фамилии
     * @return вэбэлемент с ошибкой
     */
    public String GetErrorInvalidLastName() {
        return driver.findElement(lastNameError).getText();
    }

    public NewResume SetCity(String c) {
       driver.findElement(city).sendKeys(c);
       return this;
    }

    public String GetCityError() {
       return driver.findElement(cityError).getText();
    }

    public String GetDateError () {
        return driver.findElement(dateError).getText();
    }

    public void MaleRadioClick() {
       driver.findElement(maleButton).click();
    }

    public void FemaleRadioClick() {
        driver.findElement(femaleButton).click();
    }

    public void NoExperienceButton() {
       driver.findElement(noExperienceButton).click();
    }

    public void YesExperienceButton() {
        driver.findElement(yesExperienceButton).click();
    }

    public String EcpereanceError() {
       return driver.findElement(ExperiencError).getText();
    }


    /**
     * Метод устанвоки даты
     * @param dd - день родения
     * @param mm - месяц: 0 - пусто значение, 1-12 месяцы от января по декабрь.
     * @param yyyy - год рождения четырехзначный
     * @return - новая текущая страница страница
     */
    public NewResume InputDate (int dd, int mm, int yyyy) {

        WebElement element = driver.findElement(birthDay);
        element.clear();
        element.sendKeys("" + dd);
        element.sendKeys(Keys.TAB);

        if ((mm >= 0) & (mm <= 12)) {
            Select select = new Select(driver.findElement(month));
            select.selectByIndex(mm);
        }

        element = driver.findElement(birthYear);
        element.clear();
        element.sendKeys("" + yyyy);
        element.sendKeys(Keys.TAB);

        return new NewResume(driver);
    }


}

