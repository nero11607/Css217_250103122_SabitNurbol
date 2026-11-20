public class Main {
    public static void main(String[] args) {
        BankingFacade banking = new BankingFacade();
        System.out.println("Approved: " + banking.applyForLoan("CUST-101", 25000));
        System.out.println();
        System.out.println("Approved: " + banking.applyForLoan("CUST-200", 15000));
    }
}
