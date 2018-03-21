package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by okolotovich on 3/20/18.
 */
public class WelcomePage extends BasePage {

    //*****  Constructor  *****
    public WelcomePage(WebDriver driver) {
        super(driver);
    }
    ///***  WebElements  ***
    public WebElement manageJenkinsLink = driver.findElement(By.xpath("//a[@href=\"/manage\"][@class=\"task-link\"]"));

    public void clickManageJenkinsLink(){
        manageJenkinsLink.click();
    }

}
