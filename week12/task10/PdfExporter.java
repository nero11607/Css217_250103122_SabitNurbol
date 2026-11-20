public class PdfExporter {
    public String export(String reportId, String formattedData) {
        String filename = reportId + ".pdf";
        System.out.println("Exporting " + formattedData + " to " + filename);
        return filename;
    }
}
