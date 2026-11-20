public class QueryExecutor {
    public String execute(String reportId) {
        System.out.println("Executing query for report: " + reportId);
        return "RawData[" + reportId + "]";
    }
}
