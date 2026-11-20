public class PaymentGateway {
    public boolean charge(String paymentInfo, double amount) {
        System.out.println("Charging " + amount + " using " + paymentInfo);
        return paymentInfo != null && !paymentInfo.isBlank();
    }
}
