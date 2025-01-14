package Utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class PDFReportGenerator {

    private static final String JSON_REPORT_PATH = "target/cucumber-reports/cucumber.json";
    private static final String PDF_REPORT_PATH = "target/TestResultsReport.pdf";
    private static final String BACKGROUND_IMAGE_PATH = "src/test/java/Resource/QA5.jpg";
    private static final float MARGIN = 50;
    private static final float LEADING = 14.5f;
    private static final float STARTING_Y_POSITION = 750;
    private static final float LINE_HEIGHT = 15;
    private static final float PAGE_BOTTOM_MARGIN = 100;

    public static void generatePDFReport() {
        try {
            // Load JSON data
            String jsonData = new String(Files.readAllBytes(Paths.get(JSON_REPORT_PATH)));
            JSONArray testResults = new JSONArray(jsonData);

            // Load the background image
            BufferedImage backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));

            // Create PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = createContentStreamWithBackground(document, page, backgroundImage);

            // Start text
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
            contentStream.setLeading(LEADING);
            contentStream.newLineAtOffset(MARGIN, STARTING_Y_POSITION);
            contentStream.showText("Test Results Report");
            contentStream.newLine();
            contentStream.newLine();

            int totalScenarios = 0, passedScenarios = 0, failedScenarios = 0;
            float yPosition = STARTING_Y_POSITION - LINE_HEIGHT;

            for (int i = 0; i < testResults.length(); i++) {
                JSONArray scenarios = testResults.getJSONObject(i).getJSONArray("elements");
                for (int j = 0; j < scenarios.length(); j++) {
                    totalScenarios++;
                    yPosition -= LINE_HEIGHT;

                    // If yPosition is too low, create a new page
                    if (yPosition < PAGE_BOTTOM_MARGIN) {
                        contentStream = addNewPage(document, backgroundImage, contentStream);
                        yPosition = STARTING_Y_POSITION - LINE_HEIGHT;
                    }

                    String scenarioName = scenarios.getJSONObject(j).getString("name");
                    contentStream.setNonStrokingColor(51, 51, 255); // Green
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 11);
                    contentStream.showText("Scenario: " + scenarioName);
                    contentStream.newLine();

                    boolean isPassed = true;
                    JSONArray steps = scenarios.getJSONObject(j).getJSONArray("steps");
                    for (int k = 0; k < steps.length(); k++) {
                        JSONObject step = steps.getJSONObject(k);
                        String stepName = step.getString("name");
                        String stepStatus = step.getJSONObject("result").getString("status");

                        // Change color based on status
                        if (stepStatus.equals("passed")) {
                            contentStream.setNonStrokingColor(0, 128, 0); // Green
                        } else if (stepStatus.equals("failed")) {
                            contentStream.setNonStrokingColor(255, 0, 0); // Red
                            isPassed = false;
                        } else {
                            contentStream.setNonStrokingColor(128, 128, 128); // Gray
                        }

                        // Add step
                        contentStream.setFont(PDType1Font.HELVETICA, 10);
                        contentStream.showText(" - Step: " + stepName + " [" + stepStatus + "]");
                        contentStream.newLine();
                        yPosition -= LINE_HEIGHT;

                        // New page if needed
                        if (yPosition < PAGE_BOTTOM_MARGIN) {
                            contentStream = addNewPage(document, backgroundImage, contentStream);
                            yPosition = STARTING_Y_POSITION - LINE_HEIGHT;
                        }
                    }

                    if (isPassed) passedScenarios++;
                    else failedScenarios++;
                }
            }

            // Summary Section
            if (yPosition < PAGE_BOTTOM_MARGIN) {
                contentStream = addNewPage(document, backgroundImage, contentStream);
                yPosition = STARTING_Y_POSITION - LINE_HEIGHT;
            }

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.setNonStrokingColor(3, 3, 3);
            contentStream.newLine();
            contentStream.showText("Total Scenarios: " + totalScenarios);
            contentStream.newLine();
            contentStream.setNonStrokingColor(0, 128, 0); // Green
            contentStream.showText("Passed: " +passedScenarios );
            contentStream.newLine();
            contentStream.setNonStrokingColor(255, 0, 0); // Red
            contentStream.showText("Failed: " + failedScenarios);
            contentStream.newLine();

            double passRate = (double) passedScenarios / totalScenarios * 100;
            double failRate = 100 - passRate;
            contentStream.newLine();
            contentStream.setNonStrokingColor(0, 128, 0); // Green
            contentStream.showText(String.format("Pass Rate: %.2f%%", passRate ));
            contentStream.newLine();
            contentStream.setNonStrokingColor(255, 0, 0); // Red
            contentStream.showText(String.format("Fail Rate: %.2f%%", failRate));

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 9);
            contentStream.setNonStrokingColor(3, 3, 3);
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText("Created By :");
            contentStream.newLine();
            contentStream.showText("Madushka Dissanayake");
            contentStream.newLine();
            contentStream.showText("(Senior Software Quality Assurance Engineer)");

            // Close and save
            contentStream.endText();
            contentStream.close();
            document.save(PDF_REPORT_PATH);
            document.close();

            System.out.println("Report Generated Successfully: " + PDF_REPORT_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PDPageContentStream createContentStreamWithBackground(PDDocument doc, PDPage page, BufferedImage bg) throws Exception {
        PDPageContentStream stream = new PDPageContentStream(doc, page);
        stream.drawImage(LosslessFactory.createFromImage(doc, bg), 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
        return stream;
    }

    private static PDPageContentStream addNewPage(PDDocument doc, BufferedImage bg, PDPageContentStream stream) throws Exception {
        stream.endText();
        stream.close();
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);
        PDPageContentStream newStream = createContentStreamWithBackground(doc, page, bg);
        newStream.beginText(); // Ensure text mode starts for new page
        newStream.setLeading(14.5f);
        newStream.newLineAtOffset(MARGIN, STARTING_Y_POSITION);
        return newStream;
    }
}
