import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class MainClass {

    static WebDriver driver;

    public static void main(String[] args) {

        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Software\\chrome\\chromedriver.exe");

        driver = new ChromeDriver();    // инициализируем драйвер

        driver.manage().window().maximize();

        driver.manage().
                timeouts().
                implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("https://ufa.hh.ru");

    }

    public void WaitTime(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
