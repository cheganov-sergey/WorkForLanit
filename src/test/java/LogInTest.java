import PageObject.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class LogInTest {

    private MainPage mainPage;
    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void SetUp() {
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

       }

    @Test
    public void LoginErrorTest() {
        LoginPage loginPage = mainPage.LoginFromMainPage();
        loginPage.LoginInvalid("","pss");
        Assert.assertEquals("Обязательное поле", loginPage.GetErrorUserName());
    }

    @Test
    public void LogInTest() {
        LoginPage loginPage = mainPage.LoginFromMainPage();
        loginPage.inputUserName("89872504965");
        loginPage.inputPasswod("Finland06");
        AccountMainPage newPage = loginPage.LogIn();
        Assert.assertEquals("Мои резюме", newPage.MyResumeGeText());
    }

    @After
    public void EndTest() {
        driver.close();
    }
}
