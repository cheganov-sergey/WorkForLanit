import PageObject.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;

/**
 * проверяем корректность заполнения нового резюме
 */
@DisplayName("Проверка создания нового резюме")
public class NewResumeTest {

    private static WebDriver driver;
    private static HomePage homePage;
    private static NewResume newResume;

    /**
     * Ностройка среды тестирования;
     * авторизация.
     */
    @BeforeClass
    public static void SetUp () {

        // Инициализация Драйвера:
        System.setProperty(
                "webdriver.gecko.driver",
                "src\\main\\resources\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().
                timeouts().
                implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://ufa.hh.ru");
        homePage = new HomePage(driver);

        // Авторизация:
        LoginPage loginPage = homePage.LoginFromMainPage(); //страница ввода авторизациия
        loginPage.inputUserName("89872504965");
        loginPage.inputPasswod("Finland06");
        WaitTaim(2); // ждем, как будто мы не роботы
        AccountMainPage accountMainPage = loginPage.LogIn();  // личный кабинет
        MyResume myResume = accountMainPage.MyResumeClick();  // переходим в "Мои резюме"
        newResume = myResume.CreateResume();  // создаем новое резюме
    }

    /**
     * проверям Имя
     */
    @Description (value = "Проверяем корректность ввода Имени")
    @Test
    @DisplayName ("Вводим имя")
    public void FirstNameTest() {
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
    @Description(useJavaDoc = true)
    @Step (value = "Водим НЕкоректные данные")
    public void FirstNameExceptError (String name) {
            newResume.SetFirstName(name);
            Assert.assertTrue(0 <  newResume.GetErrorInvalidNameSize());
    }

    /**
     * Валидные данные
     * @param name Имя
     */
    @Description (useJavaDoc = true)
    @Step (value = "Водим коректные данные")
    public void FirstName (String name) {
            newResume.SetFirstName(name);
            Assert.assertEquals(0, newResume.GetErrorInvalidNameSize());
    }

    /**
     * Проверяем фамилию
     */
    @Description (value = "Проверям кооректность ввода Фамилии")
    @Test
    @DisplayName ("Вводим Фамилию")
    public void LastNameTest() {
        LastNameExpectError("1Иванов");
        LastNameExpectError(" ");
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
    @Description (value = "Ввод не верного формата")
    @Step (value = "Водим НЕкоректные данные")
    public void LastNameExpectError (String name) {
        newResume.SetLastName(name);
        Assert.assertTrue( 0 < newResume.GetLastNameErrorSize());
    }

    /**
     * проверяем фамилию (valid)
     * @param name - фамилия (только бкувы и -
     */
    @Description (value = "Ввод верного формата")
    @Step (value = "Вводим корректные данные")
    public void LastName(String name) {
        newResume.SetLastName(name);
        //wait.until(ExpectedConditions.visibilityOf(newResume.GetErrorInvalidLastName()));
        Assert.assertEquals(0, newResume.GetLastNameErrorSize());
    }

    /**
     * Проверяем название города
     */
    @Description (useJavaDoc = true)
    @Test
    @DisplayName ("Вводим город")
    public void CityTest() {
        CityNameTest("Омск");
        CityTestExceptError("1Омск");
        CityTestExceptError(" ");
    }

    /**
     * горд invalid
     * @param city название города
     */
     @Description (useJavaDoc = true)
     @Step (value = "Водим НЕкоректные данные")
      public void CityTestExceptError(String city) {
        newResume.SetCity(city);
       // newResume.waitTime(1);
        newResume.SubmitResume().click();
        // newResume.waitTime(1);
        Assert.assertNotEquals(0, newResume.GetCityErrorSize());
    }

    /**
     * город valid
     * @param city название города
     */
     @Description (useJavaDoc = true)
     @Step (value = "Вводим корректные данные")
      public void CityNameTest (String city) {
        newResume.SetCity(city);
         newResume.waitTime(1);
        Assert.assertEquals(0, newResume.GetCityErrorSize());
    }

    /**
     * Тест ввода даты рождения
     */
    @Description(useJavaDoc = true)
    @Test
    @DisplayName("Вводим дату рождения")
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
     @Description(useJavaDoc = true)
     @Step ("Вводим корректные данные")
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
    @Description(useJavaDoc = true)
     @Step (value = "Вводим некоректные данные")
      public void SetBirthDateInvalid (int dd, int mm, int yyyy) {
        newResume.InputDate(dd, mm, yyyy);
        Assert.assertTrue(0 < newResume.GetDateError());
    }

    /**
     * проверка на ввод текстовых значений
     * @param dd - день
     * @param yyyy - год
     */
      @Description (useJavaDoc = true)
      @Step (value = "Вводим текст вместо цифр")
      public void SetTextDate(String dd, String yyyy) {
        newResume.InputDateString(dd, yyyy);
        Assert.assertTrue(0 < newResume.GetDateError());
    }

    /**
     * РадиоБатон - переключатель пола М/Ж
     */
    @Description (useJavaDoc = true)
    @Test
    @DisplayName ("Выбираем пол")
    public void SexButtonTest() {
        FemaleTest();
        MaleTest();
    }

    /**
     * выбираем женский пол
     */
     @Description (useJavaDoc = true)
     @Step (value = "Выбираем Женский пол")
      public void FemaleTest() {
        newResume.FemaleRadio().click();
        Assert.assertTrue(newResume.FemeleRadioIsSelect().isSelected());
    }

    /**
     * Выбираем мужской пол
     */
     @Description (useJavaDoc = true)
     @Step (value = "Выбираем мужской пол")
      public void MaleTest() {
        newResume.MaleRadio().click();
        Assert.assertTrue(newResume.MaleRadioIsSelect().isSelected());
    }

    /**
     * РадиоБатон - выбор имеющегося опыта работы
     */
    @Description(useJavaDoc = true)
    @Test
    @DisplayName("Выбираем опыт работы")
    public void ExperienceTest() {
        ExperienceError();
        YesExperience();
        NoExperience();
    }

    /**
     * Ни один переключатель не выбран, ждем ошибку
     */
      @Description(useJavaDoc = true)
      @Step (value = "Ничего не выбрано")
      public void ExperienceError(){
          newResume.SubmitResume().click();
         Assert.assertNotEquals(0, newResume.ExperienceError());
      }

    /**
     * выбран - Есть опыт работы
     */
    @Description(useJavaDoc = true)
    @Step (value = "Выбран \"Есть опыт работы\"")
        public void YesExperience() {
          newResume.YesExperienceButton().click();
          Assert.assertTrue(newResume.YesExperienceSelect().isSelected());
      }

    /**
     * выбран - Нет опыта работы
     */
    @Description(useJavaDoc = true)
    @Step (value = "Выбран \"Нет опыта работы\"")
      public void NoExperience() {
          newResume.NoExperienceButton().click();
          Assert.assertTrue(newResume.NoExperienceIsSelec().isSelected());
      }

    /**
     * закрываем драйвер
     */
    @AfterClass
    public static void Close() {
        driver.close();
    }

    public static void WaitTaim (int sec) {
        try {
            Thread.sleep(1000 * sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
