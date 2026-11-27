import java.util.ArrayList;
import java.util.List;

public class Task18 {
    interface LogParserStrategy {
        String parse(String rawEvent);
    }

    static class JsonLogParser implements LogParserStrategy {
        @Override
        public String parse(String rawEvent) {
            return "JSON(" + rawEvent + ")";
        }
    }

    static class SyslogParser implements LogParserStrategy {
        @Override
        public String parse(String rawEvent) {
            return "SYSLOG(" + rawEvent + ")";
        }
    }

    interface RedactionStrategy {
        String redact(String parsedEvent);
    }

    static class MaskPiiStrategy implements RedactionStrategy {
        @Override
        public String redact(String parsedEvent) {
            return parsedEvent.replaceAll("user=\\w+", "user=***");
        }
    }

    static class HashIdentifierStrategy implements RedactionStrategy {
        @Override
        public String redact(String parsedEvent) {
            return parsedEvent.replaceAll("user=\\w+", "user=#" + Integer.toHexString(parsedEvent.hashCode()));
        }
    }

    static class StreamReader {
        public List<String> read(List<String> rawEvents) {
            System.out.println("StreamReader: read " + rawEvents.size() + " raw events");
            return rawEvents;
        }
    }

    static class SchemaValidator {
        public boolean isValid(String event) {
            return event != null && !event.isBlank();
        }
    }

    static class BatchBuffer {
        private final List<String> buffer = new ArrayList<>();

        public void add(String record) {
            buffer.add(record);
        }

        public List<String> flush() {
            List<String> batch = new ArrayList<>(buffer);
            buffer.clear();
            System.out.println("BatchBuffer: flushed " + batch.size() + " records");
            return batch;
        }
    }

    static class SiemForwarder {
        public boolean forwardWithRetry(List<String> batch) {
            for (int attempt = 1; attempt <= 2; attempt++) {
                System.out.println("SiemForwarder: attempt " + attempt + " sending " + batch.size() + " records -> " + batch);
                if (attempt == 1) {
                    return true;
                }
            }
            return false;
        }
    }

    static class LogIngestionFacade {
        private final StreamReader streamReader = new StreamReader();
        private final SchemaValidator schemaValidator = new SchemaValidator();
        private final BatchBuffer batchBuffer = new BatchBuffer();
        private final SiemForwarder siemForwarder = new SiemForwarder();
        private LogParserStrategy parserStrategy;
        private RedactionStrategy redactionStrategy;

        public LogIngestionFacade(LogParserStrategy parserStrategy, RedactionStrategy redactionStrategy) {
            this.parserStrategy = parserStrategy;
            this.redactionStrategy = redactionStrategy;
        }

        public void setStrategies(LogParserStrategy parserStrategy, RedactionStrategy redactionStrategy) {
            this.parserStrategy = parserStrategy;
            this.redactionStrategy = redactionStrategy;
        }

        public void ingestBatch(List<String> rawEvents) {
            for (String event : streamReader.read(rawEvents)) {
                if (schemaValidator.isValid(event)) {
                    batchBuffer.add(redactionStrategy.redact(parserStrategy.parse(event)));
                }
            }
            siemForwarder.forwardWithRetry(batchBuffer.flush());
        }
    }

    public static void main(String[] args) {
        List<String> events = List.of("event=LOGIN user=nurbol ip=10.0.0.1", " ", "event=LOGOUT user=nurbol");
        LogIngestionFacade ingestion = new LogIngestionFacade(new JsonLogParser(), new MaskPiiStrategy());
        ingestion.ingestBatch(events);
        ingestion.setStrategies(new SyslogParser(), new HashIdentifierStrategy());
        ingestion.ingestBatch(events);
    }
}
