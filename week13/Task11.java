import java.util.List;

public class Task11 {
    interface BackupStrategy {
        List<String> selectFiles(List<String> allFiles);
    }

    static class FullBackupStrategy implements BackupStrategy {
        @Override
        public List<String> selectFiles(List<String> allFiles) {
            System.out.println("FullBackupStrategy: selecting all " + allFiles.size() + " files");
            return allFiles;
        }
    }

    static class IncrementalBackupStrategy implements BackupStrategy {
        @Override
        public List<String> selectFiles(List<String> allFiles) {
            List<String> changed = allFiles.isEmpty() ? List.of() : List.of(allFiles.get(allFiles.size() - 1));
            System.out.println("IncrementalBackupStrategy: selecting latest changed file(s): " + changed);
            return changed;
        }
    }

    static class DifferentialBackupStrategy implements BackupStrategy {
        @Override
        public List<String> selectFiles(List<String> allFiles) {
            List<String> diff = allFiles.subList(0, Math.min(2, allFiles.size()));
            System.out.println("DifferentialBackupStrategy: selecting diff files: " + diff);
            return diff;
        }
    }

    static class FileScanner {
        public List<String> scanDirectory(String directory) {
            System.out.println("FileScanner: scanning " + directory);
            return List.of(directory + "/db.dump", directory + "/config.yaml", directory + "/app.log");
        }
    }

    static class AesEncryptor {
        public String encrypt(List<String> files) {
            System.out.println("AesEncryptor: encrypting " + files.size() + " file(s) with AES-256");
            return "AES256{" + String.join(",", files) + "}";
        }
    }

    static class CloudStorageClient {
        public void upload(String encryptedArchive) {
            System.out.println("CloudStorageClient: uploaded " + encryptedArchive);
        }
    }

    static class RetentionCleaner {
        public void purgeExpired() {
            System.out.println("RetentionCleaner: purged snapshots older than 30 days");
        }
    }

    static class BackupFacade {
        private final FileScanner fileScanner = new FileScanner();
        private final AesEncryptor aesEncryptor = new AesEncryptor();
        private final CloudStorageClient cloudStorageClient = new CloudStorageClient();
        private final RetentionCleaner retentionCleaner = new RetentionCleaner();
        private BackupStrategy strategy;

        public BackupFacade(BackupStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(BackupStrategy strategy) {
            this.strategy = strategy;
        }

        public void runBackupJob(String directory) {
            List<String> allFiles = fileScanner.scanDirectory(directory);
            List<String> selected = strategy.selectFiles(allFiles);
            String payload = aesEncryptor.encrypt(selected);
            cloudStorageClient.upload(payload);
            retentionCleaner.purgeExpired();
        }
    }

    public static void main(String[] args) {
        BackupFacade backup = new BackupFacade(new FullBackupStrategy());
        backup.runBackupJob("/var/data");
        backup.setStrategy(new IncrementalBackupStrategy());
        backup.runBackupJob("/var/data");
        backup.setStrategy(new DifferentialBackupStrategy());
        backup.runBackupJob("/var/data");
    }
}
