package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.BasePage;

import java.util.concurrent.TimeUnit;

/**
 * Created by okolotovich on 3/20/18.
 */
    public class BaseTest {
    public WebDriver driver;
    String webPage = "http://localhost:8080";
    StringBuffer verificationErrors = new StringBuffer();
    FirefoxProfile profile = new FirefoxProfile();



    @BeforeClass
    //Create a driver. All test and page classes use this driver.
    public void setup() throws Exception {
        System.setProperty("webdriver.gecko.driver", "/Users/okolotovich/Documents/driver/geckodriver");
        profile.setPreference("browser.startup.homepage", "about:blank");
        driver = new FirefoxDriver(profile);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @AfterClass
    public void teardown() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assert.fail(verificationErrorString);
        }
    }
}
