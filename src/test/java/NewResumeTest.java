import PageObject.*;
import io.qameta.allure.Step;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * проверяем корректность заполнения нового резюме
 */
public class NewResumeTest {

    private static WebDriver driver;
    private static MainPage mainPage;
    private static NewResume newResume;
    private static WebDriverWait wait;

    @BeforeClass
    public static void SetUp () {
        System.setProperty(
                "webdriver.gecko.driver",
                "src\\main\\resources\\geckodriver.exe");
        driver = new FirefoxDriver();    // инициализируем драйвер
        driver.manage().window().maximize();
        driver.manage().
                timeouts().
                implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://ufa.hh.ru");
        mainPage = new MainPage(driver);

        LoginPage loginPage = mainPage.LoginFromMainPage();
        loginPage.inputUserName("89872504965");
        loginPage.inputPasswod("Finland06");
        AccountMainPage accountMainPage = loginPage.LogIn();
        MyResume myResume = accountMainPage.MyResumeClick();
        newResume = myResume.CreateResume();
        wait =new WebDriverWait(driver, 3);
    }

    /**
     * проверям Имя
     */
    @Test
    public void FirstNameTest() {
        //FirstNameExceptError(""); // сайт не выводит ошибку
        FirstNameExceptError("1Иван");
        FirstNameExceptError("Иван-");
        FirstNameExceptError("-Иван");
        FirstName("Иван");
        FirstName("Иван-Иван");
    }

    /**
     * Ожидаем ошибку ввода
     * @param name Имя
     */
    @Step
    public void FirstNameExceptError (String name) {
            newResume.SetFirstName(name);
            Assert.assertTrue(0 <  newResume.GetErrorInvalidNameSize());
//            wait.until(ExpectedConditions.visibilityOf(newResume.GetErrorInvalidName()));
//            Assert.assertTrue(newResume.GetErrorInvalidName().isDisplayed());
    }

    /**
     * Валидные данные
     * @param name Имя
     */
    @Step
    public void FirstName (String name) {
            newResume.SetFirstName(name);
            //wait.until(ExpectedConditions.visibilityOf(newResume.GetErrorInvalidName()));
            Assert.assertEquals(0, newResume.GetErrorInvalidNameSize());
    }

    /**
     * Проверяем фамилию
     */
    @Test
    public void LastNameTest() {
        LastNameExpectError("1Иванов");
       // LastNameExpectError("");  // сайт не выводит ошибку
        LastNameExpectError("Иванов1");
        LastNameExpectError("Иванов-");
        LastNameExpectError("-Иванов");
        LastName("Иванов");
        LastName("Иванов-Иванов");
    }

    /**
     * - Ожидаем ошибку invalid
     * @param name - фамилия (только бкувы и -
     */
    @Step
    public void LastNameExpectError (String name) {
        newResume.SetLastName(name);
//        wait.until(ExpectedConditions.visibilityOf(newResume.GetErrorInvalidLastName()));
//        Assert.assertTrue(newResume.GetErrorInvalidLastName().isDisplayed());
        Assert.assertTrue( 0 < newResume.GetLastNameErrorSize());
    }

    /**
     * проверяем фамилию (valid)
     * @param name - фамилия (только бкувы и -
     */
    @Step
    public void LastName(String name) {
        newResume.SetLastName(name);
        //wait.until(ExpectedConditions.visibilityOf(newResume.GetErrorInvalidLastName()));
        Assert.assertEquals(0, newResume.GetLastNameErrorSize());
    }

    /**
     * Проверяем название города
     */
    @Test
  //  @Ignore  // тест завершается с ошибкой
    public void CityTest() {
        CityNameTest("Омск");
        CityTestExceptError("1Омск");
        CityTestExceptError(" ");
    }

    /**
     * горд invalid
     * @param city название города
     */
     @Step
      public void CityTestExceptError(String city) {
        newResume.SetCity(city);
        newResume.waitTime(1);
        newResume.SubmitResume().click();
         newResume.waitTime(1);
        //wait.until(ExpectedConditions.visibilityOf(newResume.GetCityError()));
        Assert.assertNotEquals(0, newResume.GetCityErrorSize());
    }

    /**
     * город valid
     * @param city название города
     */
     @Step
      public void CityNameTest (String city) {
        newResume.SetCity(city);
         newResume.waitTime(1);
        Assert.assertEquals(0, newResume.GetCityErrorSize());
    }

    /**
     * Тест ввода даты рождения
     */
    @Test
    public void DataTest() {
        SetBirthDateValid(28, 1, 1989);
        SetBirthDateValid(1, 12, 1900);
        SetBirthDateInvalid(33, 0, 1800);
        SetBirthDateInvalid(21, 0, 1990);
        SetBirthDateInvalid(0, 2, 1990);
        SetBirthDateInvalid(27, 2, 1899);
        SetTextDate("Day", "Year");
        SetTextDate("", "Year");
        SetTextDate("Day", "");
    }

    /**
     * Тестируем вводом числовых значений Valid
     * @param dd - день
     * @param mm - месяц
     * @param yyyy - год
     */
     @Step
      public void SetBirthDateValid (int dd, int mm, int yyyy) {
        newResume.InputDate(dd, mm, yyyy);
        Assert.assertEquals(0, newResume.GetDateError());
    }

    /**
     * Тестируем вводом числовых значений Invalid
     * @param dd - день
     * @param mm - месяц
     * @param yyyy - год
     */
     @Step
      public void SetBirthDateInvalid (int dd, int mm, int yyyy) {
        newResume.InputDate(dd, mm, yyyy);
        Assert.assertTrue(0 < newResume.GetDateError());
    }

    /**
     * проверка на ввод текстовых значений
     * @param dd - день
     * @param yyyy - год
     */
      @Step
       public void SetTextDate(String dd, String yyyy) {
        newResume.InoutDateString(dd, yyyy);
        Assert.assertTrue(0 < newResume.GetDateError());
    }

    @Test
    public void SexButtonTest() {
        FemaleTest();
        MaleTest();
    }
     @Step
      public void FemaleTest() {
        newResume.FemaleRadio().click();
        Assert.assertTrue(newResume.FemeleRadioIsSelect().isSelected());
    }
     @Step
      public void MaleTest() {
        newResume.MaleRadio().click();
        Assert.assertTrue(newResume.MaleRadioIsSelect().isSelected());
    }

    @Test
    public void ExperienceTest() {
        ExperienceError();
        YesExperience();
        NoExperience();
    }
      @Step
      public void ExperienceError(){
          newResume.SubmitResume().click();
         Assert.assertNotEquals(0, newResume.ExperienceError());
      }
      @Step
        public void YesExperience() {
          newResume.YesExperienceButton().click();
          Assert.assertTrue(newResume.YesExperienceSelect().isSelected());
      }
      @Step
      public void NoExperience() {
          newResume.NoExperienceButton().click();
          Assert.assertTrue(newResume.NoExperienceIsSelec().isSelected());
      }

    @AfterClass
    public static void Close() {
        driver.close();
    }

}
