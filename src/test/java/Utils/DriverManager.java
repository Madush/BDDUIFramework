package Utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    // Using ThreadLocal to ensure each thread has its own WebDriver instance
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Method to initialize the WebDriver
    public static void initializeDriver() {
        if (driver.get() == null) {
            // Clear cache and set up the WebDriver
            WebDriverManager.firefoxdriver().clearDriverCache().setup();
            FirefoxOptions options = new FirefoxOptions();
            // options.addArguments("--headless");  // Uncomment to run in headless mode
            options.addArguments("--disable-gpu");  // Optional, needed for Windows OS
            driver.set(new FirefoxDriver(options));  // Set the driver instance for this thread
        }
    }

    // Method to get the driver instance
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();  // Initialize if not already done
        }
        return driver.get();  // Return the driver instance for this thread
    }

    // Method to quit the driver
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();  // Quit the driver
            driver.remove();  // Remove the driver reference for this thread
        }
    }
}
