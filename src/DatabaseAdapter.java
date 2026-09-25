public class DatabaseAdapter implements IRepository {
    private final LegacyDatabaseConnection connection;

    public DatabaseAdapter(LegacyDatabaseConnection connection) {
        this.connection = connection;
    }

    @Override
    public String findById(int id) throws RecordNotFoundException, DatabaseLockedException {
        String[] outBuffer = new String[1];
        int result = connection.executeFetch(id, outBuffer);
        
        if (result == -1) {
            throw new RecordNotFoundException("Record not found for id: " + id);
        }
        if (result == -2) {
            throw new DatabaseLockedException("Database is locked for id: " + id);
        }
        
        return outBuffer[0];
    }
}