package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.Color;


import pages.BasePage;
import pages.LoginPage;
import pages.WelcomePage;

/**
 * Created by okolotovich on 3/19/18.
 */



public class JenkinsTest extends BaseTest {

    @Test
    public void loginToJenkins() throws IOException {
        openInitialPage();
        checkButtonColor("//button[@id=\"yui-gen1-button\"]", "#4b758b");
        login();
        checkRefreshLink();
        checkRefreshLink();
        clickOnManageJenkinsLink();
        checkElementWithTagAndTextExists( "dt", "Manage Jenkins");
        checkElementWithTagAndTextExists( "dd","Create/delete/modify users that can log in to this Jenkins");
        clickOnElementWithXPath("//*[contains(text(),'Create/delete/modify users that can log in to this Jenkins')]");
        checkElementWithTagAndTextExists("a","Create User");
        clickOnElementWithXPath("//*[contains(text(),'Create User')]");
        checkFormExists(((driver.findElement(By.name("username")).getAttribute("type").equalsIgnoreCase("text"))&&
                (driver.findElement(By.name("password1")).getAttribute("type").equalsIgnoreCase("password"))&&
                (driver.findElement(By.name("password2")).getAttribute("type").equalsIgnoreCase("password"))&&
                (driver.findElement(By.name("fullname"))).getAttribute("type").equalsIgnoreCase("text"))&&
                (driver.findElement(By.name("email"))).getAttribute("type").equalsIgnoreCase("text"),
                "table");
        checkInputIsEmpty("//input[@id = \"username\"]");
        checkInputIsEmpty("//input[@name = \"password1\"]");
        checkInputIsEmpty("//input[@name = \"password2\"]");
        checkInputIsEmpty("//input[@name=\"fullname\"]");
        checkInputIsEmpty("//input[@name = \"email\"]");
        checkButtonColor("//button[@id = \"yui-gen1-button\"]","#4b758b");
        checkErrorMessageICreateUserWithEmptyFields();
        fillInTheForm("someuser", "somepassword", "Some Full Name","some@addr.dom");
        checkElementWithByXpathExists("//table//tr/td/*[contains(text(),\"someuser\")]");
        clickOnElementWithXPath("//a[@href=\"user/someuser/delete\"]");
        checkElementWithByXpathExists("//*[contains(text(),'Are you sure about deleting the user from Jenkins?')]");
        checkButtonColor("//button[.=\"Yes\"]","#4b758b");
        clickOnElementWithXPath("//button[.=\"Yes\"]");
        checkElementWithByXpathNotExist("//table//tr/td/*[contains(text(),\"someuser\")]");
        checkElementWithByXpathNotExist("//a[@href=\"user/someuser/delete\"]");



    }

    public void openInitialPage() {
        driver.get(webPage);
    }

    public void login() {
        LoginPage lp = new LoginPage(this.driver);

        lp.login();
    }

    public void clickOnManageJenkinsLink() {
        WelcomePage wp = new WelcomePage(this.driver);
        wp.clickManageJenkinsLink();
    }

    public void clickOnElementWithXPath(String xPath){
        driver.findElement(By.xpath(xPath)).click();
    }
    public void checkElementWithByXpathExists(String xPath){
        Collection<WebElement> elements = driver.findElements(By.xpath(xPath));
        Assert.assertFalse(elements.isEmpty(), "FAILURE, no elements with XPath " + xPath + " found!");
    }
    public void checkElementWithByXpathNotExist(String xPath){
        Collection<WebElement> elements = driver.findElements(By.xpath(xPath));
        Assert.assertTrue(elements.isEmpty(), "FAILURE, no elements with XPath " + xPath + " found!");
    }
    public void checkElementWithTagAndTextExists(String tagName, String text){
        String xpathToCheck = "//*[contains(text(),'" + text + "')]";
        Collection<WebElement> elements = driver.findElements(By.tagName(tagName));
        Assert.assertFalse(elements.isEmpty(), "OOPS, no elements found!");
        WebElement form = null;
        Iterator<WebElement> i = elements.iterator();
        boolean form_found = false;

        while (i.hasNext()) {
            form = i.next();
            if (form.findElement(By.xpath(xpathToCheck)) != null) {
                form_found = true;
                break;
            }
            Assert.assertTrue(form_found, "OOPS, no suitable <" + tagName + "> elements found!");
        }
    }
    public void checkFormExists(boolean condition, String formTag){
        Collection<WebElement> elements = driver.findElements(By.tagName(formTag));
        Assert.assertFalse(elements.isEmpty(), "FAILURE, no elements found!");
        WebElement form = null;
        Iterator<WebElement> i = elements.iterator();
        boolean form_found = false;

        while (i.hasNext()) {
            form = i.next();
            if (condition == true) {
                form_found = true;
                break;
            }
            Assert.assertTrue(form_found, "OOPS, no suitable <" + formTag + "> elements found!");
        }
    }
    public void checkInputIsEmpty(String inputXpath){
        Assert.assertTrue(driver.findElement(By.xpath(inputXpath)).getAttribute("value").contentEquals(""), "FAILURE: input with XPath "+ inputXpath + " is not empty.");
    }
    public void fillInTheForm(String username, String password, String fullName, String email){
        driver.findElement(By.xpath("//input[@id = \"username\"]")).sendKeys(username);
        driver.findElement(By.xpath("//input[@name = \"password1\"]")).sendKeys(password);
        driver.findElement(By.xpath("//input[@name = \"password2\"]")).sendKeys(password);
        driver.findElement(By.xpath("//input[@name = \"fullname\"]")).sendKeys(fullName);
        driver.findElement(By.xpath("//input[@name = \"email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//button[@id = \"yui-gen1-button\"]")).click();
    }
    public void checkButtonColor(String buttonXPath, String color){
        Assert.assertTrue(Color.fromString(driver.findElement(By.xpath(buttonXPath)).getCssValue("background-color")).asHex().equalsIgnoreCase(color),
                "FAILURE, button with XPath " + buttonXPath + " does not have color: " + color + " .");

    }
    public void checkErrorMessageICreateUserWithEmptyFields(){
        fillInTheForm("","","","");
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class=\"error\"]")).getText().equalsIgnoreCase("\"\" is prohibited as a full name for security reasons."));
    }
    public void checkRefreshLink(){
        String refreshLink = driver.findElement(By.xpath("//div[@class=\"smallfont\"]/a")).getText();
        if(refreshLink.equalsIgnoreCase("ENABLE AUTO REFRESH")){
            driver.findElement(By.xpath("//a[.='ENABLE AUTO REFRESH']")).click();
            Assert.assertTrue(driver.findElement(By.xpath("//div[@class=\"smallfont\"]/a")).getText().equalsIgnoreCase("DISABLE AUTO REFRESH"));
        } else {
            driver.findElement(By.xpath("//a[.='DISABLE AUTO REFRESH']")).click();
            Assert.assertTrue(driver.findElement(By.xpath("//div[@class=\"smallfont\"]/a")).getText().equalsIgnoreCase("ENABLE AUTO REFRESH"));
        }

    }
}
