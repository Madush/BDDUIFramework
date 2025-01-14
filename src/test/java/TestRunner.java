import Utils.PDFReportGenerator;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"Stepdefinitions", "Utils"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-report.html", // HTML report
                "json:target/cucumber-reports/cucumber.json",// JSON report (required for Jenkins)
        },
        monochrome = true
)
public class TestRunner {

        @AfterClass
        public static void generatePDFReport() {
                // Generate PDF report after all tests
                PDFReportGenerator.generatePDFReport();
                System.out.println("PDF report generated after all tests.");
        }
}

