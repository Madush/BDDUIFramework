package Pages;


import net.bytebuddy.asm.Advice;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertEquals;

public class HomePage {

    WebDriver driver;

    // Define locators using @FindBy (XPath, CSS, etc.)
    @FindBy(xpath = "//input[@id='query-header']")
    WebElement searchBar;

    @FindBy(xpath = "//button[@class='coi-banner__accept' and @aria-label='OK']")
    WebElement cookieAccept;

    @FindBy(xpath = "//input[@id='login-button']")
    WebElement loginButton;

    // Constructor to initialize elements using PageFactory
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickontheCookieAcceptOption() {
        cookieAccept.click();
    }

    public void enterSearchTerm(String searchTerm) throws InterruptedException {
        searchBar.sendKeys(searchTerm);
        searchBar.sendKeys(Keys.ENTER);
        Thread.sleep(50000);
    }

    public void clickontheButton(String buttonname) throws InterruptedException {
        Thread.sleep(1000);
        String expectedXPath = String.format("//a[contains(@class, 'grid') and .//span[text()='%s']]", buttonname);
        WebElement buttonselection = driver.findElement(By.xpath(expectedXPath));
        buttonselection.click();
    }

}
