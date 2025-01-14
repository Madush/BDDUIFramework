package Utils;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hooks {

    @Before
    public void setUp() {
        // Initialize the WebDriver
        WebDriver driver = DriverManager.getDriver();
        driver.manage().window().maximize(); // Maximize browser window
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();

        // Check if the scenario failed and take a screenshot if needed
        if (scenario.isFailed()) {
            System.out.println("Scenario failed! Capturing screenshot...");

            // Capture and save the screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String screenshotName = "target/screenshots/" + scenario.getName().replaceAll("[\\/:*?\"<>|]", "_") + "_" + timestamp + ".png";

            try {
                FileUtils.copyFile(screenshot, new File(screenshotName));
                System.out.println("Screenshot saved: " + screenshotName);

                // Optionally, attach screenshot to the Cucumber report
                byte[] screenshotBytes = FileUtils.readFileToByteArray(screenshot);
                scenario.attach(screenshotBytes, "image/png", scenario.getName());

            } catch (IOException e) {
                System.err.println("Failed to save screenshot: " + e.getMessage());
            }
        }

        // Quit the driver after each scenario
        DriverManager.quitDriver();

        // Logging info
        System.out.println("Tearing down after scenario: " + scenario.getName());
        System.out.println("Cucumber JSON report should be updated now.");
    }


@AfterAll
public static void generateReport() {
    try {
        PDFofScreenshots.generatePDFReport(); // Call your PDF generator class
        System.out.println("PDF Screenshot Report generated successfully.");
    } catch (IOException e) {
        System.err.println("Failed to generate PDF Screenshot Report: " + e.getMessage());
    }
  }
}

