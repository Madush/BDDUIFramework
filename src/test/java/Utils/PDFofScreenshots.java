package Utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class PDFofScreenshots {
    private static final String SCREENSHOTS_DIR = "target/screenshots";
    private static final String OUTPUT_PDF = "target/FailureReport.pdf";

    public static void main(String[] args) {
        try {
            generatePDFReport();
            System.out.println("PDF Report generated successfully: " + OUTPUT_PDF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generatePDFReport() throws IOException {
        // Create a new PDF document
        PDDocument document = new PDDocument();

        // Add a title page
        addTitlePage(document);

        // Add failure scenarios with screenshots
        List<Path> screenshots = getScreenshots();
        for (Path screenshot : screenshots) {
            addScenarioPage(document, screenshot);
        }

        // Save the document
        document.save(OUTPUT_PDF);
        document.close();
    }

    private static void addTitlePage(PDDocument document) throws IOException {
        PDPage titlePage = new PDPage(PDRectangle.A4);
        document.addPage(titlePage);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, titlePage)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Failure Scenarios Report");
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 650);
            contentStream.showText("Generated on: " + java.time.LocalDateTime.now());
            contentStream.endText();

        }
    }

    private static void addScenarioPage(PDDocument document, Path screenshotPath) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // Add scenario name
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 11);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Scenario: " + screenshotPath.getFileName().toString());
            contentStream.endText();

            // Add screenshot
            PDImageXObject image = PDImageXObject.createFromFile(screenshotPath.toString(), document);
            contentStream.drawImage(image, 50, 400, 500, 300);
        }
    }

    private static List<Path> getScreenshots() throws IOException {
        return Files.list(Paths.get(SCREENSHOTS_DIR))
                .filter(path -> path.toString().endsWith(".png"))
                .collect(Collectors.toList());
    }

}

