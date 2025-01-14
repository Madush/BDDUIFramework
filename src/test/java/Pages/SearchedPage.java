package Pages;


import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SearchedPage {

    WebDriver driver;

    // Define locators using @FindBy (XPath, CSS, etc.)
    @FindBy(xpath = "//h1[contains(@class, 'font-headline') and contains(text(), '$')]")

    WebElement headingName;


    // Constructor to initialize elements using PageFactory
    public SearchedPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void verifytheHeading(String headname) {
        String expectedXPath = String.format("//h1[contains(@class, 'font-headline') and contains(text(), '%s')]", headname);
        WebElement headlineElement = driver.findElement(By.xpath(expectedXPath));
        String headerValue = headlineElement.getText();
        assertEquals(headname,headerValue);
    }


}
