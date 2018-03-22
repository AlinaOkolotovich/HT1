package pages;

import com.sun.xml.internal.rngom.parse.host.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by okolotovich on 3/20/18.
 */
public class LoginPage extends BasePage {
    String name = "alina";
    String password = "ftall123123";
    public WebElement loginButton = driver.findElement(By.xpath("//span[@id=\"yui-gen1\"]"));

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(){
        WebElement loginField = driver.findElement(By.xpath("//form[@name=\"login\"]//input[@id=\"j_username\"]"));
        loginField.sendKeys(name);
        WebElement passwordField = driver.findElement(By.xpath("//form[@name=\"login\"]//input[@name=\"j_password\"]"));
        passwordField.sendKeys(password);
        WebElement loginButton = driver.findElement(By.xpath("//span[@id=\"yui-gen1\"]"));
        loginButton.click();
    }
    public WebElement getLoginButton(){
        return loginButton;
    }
}
