package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Класс страницы создания нового резюме
 */
public class NewResume {

    private WebDriver driver;
    private WebDriverWait wait2;

    public NewResume(WebDriver driver) {
        this.driver = driver;
    }

    private By firstName = By.xpath("//input[@name='firstName[0].string']");
    private By firstNameError = By.xpath("//input[contains(@name,'firstName[0].string')]/ancestor::div[@class = 'bloko-control-group__vertical-item']//div[@class = 'bloko-form-error bloko-form-error_entered']");
    private By lastName = By.xpath("//input[@name='lastName[0].string']");
    private By lastNameError = By.xpath("//input[@name='lastName[0].string']/ancestor::div[@class = 'bloko-column bloko-column_container bloko-column_xs-4 bloko-column_s-8 bloko-column_m-4 bloko-column_l-4']//div[@class = 'bloko-form-error bloko-form-error_entered']");
    private By city = By.xpath("//input[@data-qa='suggestCity resume-city']");
    private By cityError = By.xpath("//input[contains(@name,'area[0].string')]/ancestor::div[@class = 'bloko-column bloko-column_xs-4 bloko-column_s-8 bloko-column_m-4 bloko-column_l-4']//div[@class=\"bloko-form-error bloko-form-error_entered\"]");
    private By birthDay = By.xpath("//input[@data-qa='resume__birthday__day']");
    private By month = By.xpath("//select[@data-qa='resume__birthday__month-select']");
    private By birthYear = By.xpath("//input[@data-qa='resume__birthday__year']");
    private By dateError = By.xpath("//input[@data-qa='resume__birthday__day']/ancestor::div[@class='bloko-column bloko-column_xs-4 bloko-column_s-4 bloko-column_m-4 bloko-column_l-4']//div[@class='bloko-form-error bloko-form-error_entered']");
    private By maleButton = By.xpath("//label[@data-qa='resume-gender-male']/span");
    private By maleButtonIsSelect = By.xpath("//label[@data-qa='resume-gender-male']/input");
    private By femaleButton = By.xpath("//label[@data-qa='resume-gender-female']/span");
    private By femaleButtonisSelect = By.xpath("//label[@data-qa='resume-gender-female']/input");
    private By noExperienceButton = By.xpath("//span[@class='bloko-radio__text'][contains(.,'Нет опыта работы')]");
    private By noExperienceButtonIsSelect = By.xpath("//label[@data-qa='without-experience']/input");
    private By yesExperienceButton = By.xpath("//span[@class='bloko-radio__text'][contains(.,'Есть опыт работы')]");
    private By yesExperienceButtonIsSelect = By.xpath("//label[@data-qa='with-experience']/input");
    private By experinceError = By.xpath("//div[@class='bloko-column bloko-column_xs-0 bloko-column_s-8 bloko-column_m-6 bloko-column_l-6']/div[@class='bloko-form-error bloko-form-error_entered']");
    private By headerResume = By.xpath("//h1[contains(@data-qa,'resume-short-header')]");
    private By submitResume = By.xpath("//button[contains(@data-qa,'resume-submit')]");

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
     * проверяем наличие ошибки при вводе имени (invalid)
     * @return вебэлемент с ошибкой
     */
    public WebElement GetErrorInvalidName() {
       return driver.findElement(firstNameError);
    }

    /**
     * проверяем существует ли ошибка при вводе корректного имени (valid)
     * @return >=1 - есть ошибка
     */
    public int GetErrorInvalidNameSize() {
        return driver.findElements(firstNameError).size();
    }

    /**
     * ввод фамилии
     * @param name фамилия соискателя
     * @return  текущая страница
     */
    public NewResume SetLastName(String name) {
        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys(name);
        driver.findElement(lastName).sendKeys(Keys.TAB);
       return this;
    }


    /**
     * проверяем не появится ли ошибка при правильной фамилии (valid)
     * @return >=1 - есть ошибка
     */
    public int GetLastNameErrorSize() {
        return driver.findElements(lastNameError).size();
    }

    public NewResume SetCity(String c) {
        wait2 = new WebDriverWait(driver, 5);
        wait2.until(ExpectedConditions.visibilityOf(driver.findElement(city)));
        driver.findElement(city).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(city).sendKeys(Keys.DELETE);
        driver.findElement(city).sendKeys(c);
        driver.findElement(headerResume).click();
       return this;
    }

    public int GetCityErrorSize() {
        return driver.findElements(cityError).size();
    }

    public int GetDateError () {
        return driver.findElements(dateError).size();
    }

    public WebElement MaleRadio() {
       return driver.findElement(maleButton);
    }

    public WebElement MaleRadioIsSelect() {
        return driver.findElement(maleButtonIsSelect);
    }

    public WebElement FemaleRadio() {
        return driver.findElement(femaleButton);
    }
    public WebElement FemeleRadioIsSelect() {
        return driver.findElement(femaleButtonisSelect);
    }

    public WebElement NoExperienceButton() {
       return driver.findElement(noExperienceButton);
    }
    public WebElement NoExperienceIsSelec() {
        return driver.findElement(noExperienceButtonIsSelect);
    }

    public WebElement YesExperienceButton() {
        return driver.findElement(yesExperienceButton);
    }
    public WebElement YesExperienceSelect() {
        return driver.findElement(yesExperienceButtonIsSelect);
    }

    public int ExperienceError() {
       return driver.findElements(experinceError).size();
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
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
        element.sendKeys("" + dd);

         if ((mm >= 0) & (mm <= 12)) {
            Select select = new Select(driver.findElement(month));
            select.selectByIndex(mm);
        }

        element = driver.findElement(birthYear);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
        element.sendKeys("" + yyyy);
        driver.findElement(headerResume).click();

        return this;
    }
    public NewResume InoutDateString (String dd, String yyyy) {
        driver.findElement(birthDay).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(birthDay).sendKeys(Keys.DELETE);
        driver.findElement(birthDay).sendKeys(dd);

        driver.findElement(birthYear).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(birthYear).sendKeys(Keys.DELETE);
        driver.findElement(birthYear).sendKeys(yyyy);

        driver.findElement(headerResume).click();
        return this;
    }

    public WebElement SubmitResume() {
        return driver.findElement(submitResume);
    }


}

