import java.util.List;

public class Task04 {
    interface CompressionStrategy {
        String compress(String rawData);
    }

    static class RunLengthCompression implements CompressionStrategy {
        @Override
        public String compress(String rawData) {
            return "RLE[" + rawData.length() + ":" + rawData.hashCode() + "]";
        }
    }

    static class DictionaryCompression implements CompressionStrategy {
        @Override
        public String compress(String rawData) {
            return "DICT[" + rawData.replace(" ", "_") + "]";
        }
    }

    static class NoCompression implements CompressionStrategy {
        @Override
        public String compress(String rawData) {
            return "RAW[" + rawData + "]";
        }
    }

    static class FileReader {
        public String readAll(List<String> sourceFiles) {
            System.out.println("FileReader: reading " + sourceFiles.size() + " file(s)");
            return String.join("|", sourceFiles);
        }
    }

    static class ChecksumGenerator {
        public String generate(String data) {
            String hash = Integer.toHexString(data.hashCode());
            System.out.println("ChecksumGenerator: hash=" + hash);
            return hash;
        }
    }

    static class ArchivePackager {
        public void packageArchive(String compressedData, String checksum, String destination) {
            System.out.println("ArchivePackager: created " + destination + " | checksum=" + checksum + " | payload=" + compressedData);
        }
    }

    static class ArchiveFacade {
        private final FileReader fileReader = new FileReader();
        private final ChecksumGenerator checksumGenerator = new ChecksumGenerator();
        private final ArchivePackager archivePackager = new ArchivePackager();
        private CompressionStrategy strategy;

        public ArchiveFacade(CompressionStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(CompressionStrategy strategy) {
            this.strategy = strategy;
        }

        public void createArchive(List<String> sourceFiles, String destination) {
            String data = fileReader.readAll(sourceFiles);
            String checksum = checksumGenerator.generate(data);
            String compressed = strategy.compress(data);
            archivePackager.packageArchive(compressed, checksum, destination);
        }
    }

    public static void main(String[] args) {
        List<String> files = List.of("notes.txt", "budget.csv", "diagram.svg");
        ArchiveFacade archive = new ArchiveFacade(new RunLengthCompression());
        archive.createArchive(files, "backup.rle");
        archive.setStrategy(new DictionaryCompression());
        archive.createArchive(files, "backup.dict");
        archive.setStrategy(new NoCompression());
        archive.createArchive(files, "backup.tar");
    }
}
