public class CreditScoreService {
    public boolean isEligible(String customerId) {
        System.out.println("Checking credit score for customer: " + customerId);
        return !customerId.endsWith("00");
    }
}
