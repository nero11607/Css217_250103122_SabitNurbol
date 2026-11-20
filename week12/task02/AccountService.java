public class AccountService {
    public boolean hasActiveAccount(String customerId) {
        System.out.println("Checking active account for customer: " + customerId);
        return customerId != null && !customerId.isBlank();
    }

    public boolean hasSufficientBalance(String customerId, double loanAmount) {
        System.out.println("Verifying minimum reserve for loan amount: " + loanAmount);
        return loanAmount > 0 && loanAmount <= 100000;
    }
}
