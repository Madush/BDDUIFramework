package Stepdefinitions;

import Pages.HomePage;
import Utils.ConfigLoader;
import Utils.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.assertTrue;

public class HomePSteps {

    WebDriver driver = DriverManager.getDriver();
    HomePage homePage = new HomePage(driver);

    @Given("The user is on the homepage")
    public void the_user_is_on_the_home_page() throws InterruptedException {
        // Fetch login URL from config.properties
        String loginUrl = ConfigLoader.getProperty("loginUrl");
        driver.get(loginUrl);
        Thread.sleep(1000);
    }

    @Then("the user is redirected to the homepage")
    public void the_user_is_redirected_to_the_homepage() throws InterruptedException {
        // Validate that the user is redirected to the homepage
        assertTrue(driver.getCurrentUrl().contains("https://www.elgiganten.se/"));
        Thread.sleep(1000);
    }

    @Then("Click on the Accept cookies option")
    public void click_on_the_accept_cookies_option() throws InterruptedException {
        homePage.clickontheCookieAcceptOption();
        Thread.sleep(1000);

    }

    @Then("I enter {string} in the search bar")
    public void i_enter_in_the_search_bar(String searchTerm) throws InterruptedException {
        homePage.enterSearchTerm(searchTerm);
        Thread.sleep(1000);
    }

    @Then("Click on the {string} button")
    public void click_on_the_button(String buttonname) throws InterruptedException {
        Thread.sleep(1000);
        homePage.clickontheButton(buttonname);
        Thread.sleep(1000);
    }

}
