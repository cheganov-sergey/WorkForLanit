import PageObject.*;
import io.qameta.allure.Step;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
                "webdriver.chrome.driver",
                "C:\\Software\\chrome\\chromedriver.exe");
        driver = new ChromeDriver();    // инициализируем драйвер
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
        FirstNameExceptError("");
        FirstNameExceptError("1Иван");
        FirstNameExceptError("Иван-");
        FirstNameExceptError("Иван");
        FirstNameExceptError("-Иван");
        FirstName("Иван");
        FirstName("Иван-Иван1");
    }

    /**
     * Ожидаем ошибку ввода
     * @param name Имя
     */
    @Step
    public void FirstNameExceptError (String name) {
            newResume.SetFirstName(name);
            wait.until(ExpectedConditions.visibilityOf(newResume.GetErrorInvalidName()));
            Assert.assertTrue(newResume.GetErrorInvalidName().isDisplayed());
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
        LastNameExpectError("");
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
        wait.until(ExpectedConditions.visibilityOf(newResume.GetErrorInvalidLastName()));
        Assert.assertTrue(newResume.GetErrorInvalidLastName().isDisplayed());
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
    public void CityTest() {
        CityTestExceptError("1Омск");
        CityTestExceptError("");
        CityNameTest("Омск");
    }

    /**
     * горд invalid
     * @param city название города
     */
    @Step
    public void CityTestExceptError(String city) {
        newResume.SetCity(city);
        wait.until(ExpectedConditions.visibilityOf(newResume.GetCityError()));
        Assert.assertTrue(newResume.GetCityError().isDisplayed());
    }

    /**
     * город valid
     * @param city название города
     */
    @Step
    public void CityNameTest (String city) {
        newResume.SetCity(city);
        Assert.assertEquals(0, newResume.GeetCityErrorSize());
    }


//    @AfterClass
//    public static void Close() {
//        driver.close();
//    }

}
