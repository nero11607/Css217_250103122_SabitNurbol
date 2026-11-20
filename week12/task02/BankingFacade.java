public class BankingFacade {
    private final AccountService accountService;
    private final CreditScoreService creditScoreService;
    private final LoanLedger loanLedger;

    public BankingFacade() {
        this.accountService = new AccountService();
        this.creditScoreService = new CreditScoreService();
        this.loanLedger = new LoanLedger();
    }

    public boolean applyForLoan(String customerId, double amount) {
        if (!accountService.hasActiveAccount(customerId)) {
            System.out.println("Loan denied: no active account.");
            return false;
        }
        if (!accountService.hasSufficientBalance(customerId, amount)) {
            System.out.println("Loan denied: amount exceeds eligible limit.");
            return false;
        }
        if (!creditScoreService.isEligible(customerId)) {
            System.out.println("Loan denied: credit score below threshold.");
            return false;
        }
        loanLedger.recordLoan(customerId, amount);
        return true;
    }
}
