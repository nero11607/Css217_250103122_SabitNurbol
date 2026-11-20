public class Main {
    public static void main(String[] args) {
        ReportFacade reportFacade = new ReportFacade();
        String reportFile = reportFacade.generateReport("Q4-FINANCE");
        System.out.println("Generated report: " + reportFile);
    }
}
