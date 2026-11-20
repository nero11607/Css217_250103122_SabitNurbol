public class ReportFacade {
    private final DatabaseConnection connection;
    private final QueryExecutor queryExecutor;
    private final DataFormatter dataFormatter;
    private final PdfExporter pdfExporter;

    public ReportFacade() {
        this.connection = new DatabaseConnection();
        this.queryExecutor = new QueryExecutor();
        this.dataFormatter = new DataFormatter();
        this.pdfExporter = new PdfExporter();
    }

    public String generateReport(String reportId) {
        connection.connect();
        String rawData = queryExecutor.execute(reportId);
        String formatted = dataFormatter.format(rawData);
        String pdfPath = pdfExporter.export(reportId, formatted);
        connection.disconnect();
        return pdfPath;
    }
}
