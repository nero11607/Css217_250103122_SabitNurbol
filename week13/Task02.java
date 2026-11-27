public class Task02 {
    interface ExportStrategy {
        String serialize(String stampedDocument);
    }

    static class PlainTextExport implements ExportStrategy {
        @Override
        public String serialize(String stampedDocument) {
            return stampedDocument;
        }
    }

    static class CsvExport implements ExportStrategy {
        @Override
        public String serialize(String stampedDocument) {
            return "record,\"" + stampedDocument.replace("\"", "\"\"") + "\"";
        }
    }

    static class MarkdownExport implements ExportStrategy {
        @Override
        public String serialize(String stampedDocument) {
            return "# Report\n\n" + stampedDocument;
        }
    }

    static class ContentValidator {
        public boolean isValid(String document) {
            System.out.println("ContentValidator: validating document");
            return document != null && !document.isBlank();
        }
    }

    static class MetadataStamp {
        public String apply(String document) {
            System.out.println("MetadataStamp: applying export timestamp");
            return document + " [exported=2026-11-27]";
        }
    }

    static class DiskWriter {
        public void write(String outputPath, String content) {
            System.out.println("DiskWriter: wrote " + content.length() + " chars to " + outputPath);
        }
    }

    static class DocumentExportFacade {
        private final ContentValidator validator = new ContentValidator();
        private final MetadataStamp metadataStamp = new MetadataStamp();
        private final DiskWriter diskWriter = new DiskWriter();
        private ExportStrategy strategy;

        public DocumentExportFacade(ExportStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(ExportStrategy strategy) {
            this.strategy = strategy;
        }

        public void exportReport(String document, String outputPath) {
            if (!validator.isValid(document)) {
                System.out.println("Export aborted: invalid document");
                return;
            }
            String stamped = metadataStamp.apply(document);
            String formatted = strategy.serialize(stamped);
            diskWriter.write(outputPath, formatted);
        }
    }

    public static void main(String[] args) {
        DocumentExportFacade exporter = new DocumentExportFacade(new PlainTextExport());
        exporter.exportReport("Monthly Sales Summary", "sales.txt");
        exporter.setStrategy(new CsvExport());
        exporter.exportReport("Monthly Sales Summary", "sales.csv");
        exporter.setStrategy(new MarkdownExport());
        exporter.exportReport("Monthly Sales Summary", "sales.md");
    }
}
