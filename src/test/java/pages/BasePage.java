package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.xml.dom.Tag;
import tests.BaseTest;

import java.util.concurrent.TimeUnit;

/**
 * Created by okolotovich on 3/20/18.
 */
public class BasePage {

    public WebDriver driver;
    String pageTitle;


    //Constructor
    public BasePage (WebDriver driver){
        this.driver = driver;
    }

    public String getPageTitle(){
        pageTitle = driver.findElement(By.tagName("title")).getText();
        return pageTitle;
    }

    public void click (By elementLocation) {
        driver.findElement(elementLocation).click();
    }

    public void writeText (By elementLocation, String text) {
        driver.findElement(elementLocation).sendKeys(text);
    }

    public String readText (By elementLocation) {
        return driver.findElement(elementLocation).getText();
    }

}
