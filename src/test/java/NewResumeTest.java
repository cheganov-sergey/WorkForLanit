import PageObject.*;
import io.qameta.allure.Step;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class NewResumeTest {

    private static WebDriver driver;
    private static MainPage mainPage;
    private static NewResume newResume;
    private static WebDriverWait vait;

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
        vait =new WebDriverWait(driver, 5);
    }

    @Test
    public void FirstNameTest() {
        //FirstNameExceptError("");
        FirstNameExceptError("1Иван");
        FirstNameExceptError("Иван-");
        FirstNameExceptError("Иван-");
        FirstNameExceptError("-Иван");
        FirstName("Иван");
        FirstName("Иван-Иван");
    }

    @Step
    public void FirstNameExceptError (String name) {
            newResume.SetFirstName(name);
            vait.until(ExpectedConditions.visibilityOf(newResume.GetErrorInvalidName()));
            Assert.assertTrue(newResume.GetErrorInvalidName().isDisplayed());
    }
    @Step
    public void FirstName (String name) {
            newResume.SetFirstName(name);
            vait.until(ExpectedConditions.visibilityOf(newResume.GetErrorInvalidName()));
            Assert.assertFalse(newResume.GetErrorInvalidName().isDisplayed());
    }

//    @AfterClass
//    public static void Close() {
//        driver.close();
//    }

}
