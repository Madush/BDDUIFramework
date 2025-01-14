package Stepdefinitions;

import Pages.SearchedPage;
import Utils.DriverManager;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class SearchedPageSteps {

    WebDriver driver = DriverManager.getDriver();
    SearchedPage searchedPage = new SearchedPage(driver);

    @Then("I should see search results related to {string}")
    public void i_should_see_search_results_related_to(String headname) {
       searchedPage.verifytheHeading(headname);
    }

}
