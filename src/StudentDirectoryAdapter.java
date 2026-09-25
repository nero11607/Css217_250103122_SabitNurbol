public class StudentDirectoryAdapter implements IModernDirectory {
    private final LegacyStudentDirectory legacyDirectory;

    public StudentDirectoryAdapter(LegacyStudentDirectory legacyDirectory) {
        this.legacyDirectory = legacyDirectory;
    }

    @Override
    public int size() {
        return legacyDirectory.totalEntries();
    }

    @Override
    public String get(int zeroBasedIndex) {
        if (zeroBasedIndex < 0 || zeroBasedIndex >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return legacyDirectory.getStudentAt(zeroBasedIndex + 1);
    }
}