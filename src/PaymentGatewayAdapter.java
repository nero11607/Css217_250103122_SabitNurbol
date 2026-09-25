import java.math.BigDecimal;
public class PaymentGatewayAdapter implements IPaymentGateway { private final LegacyBillingSystem legacySystem;
public PaymentGatewayAdapter(LegacyBillingSystem legacySystem) {
    this.legacySystem = legacySystem;
}
@Override
public void processPayment(int customerId, BigDecimal amountInDollars) {
    if (amountInDollars == null || amountInDollars.compareTo(BigDecimal.ZERO) < 0) {
        throw new IllegalArgumentException();
    }
    long amountInCents = amountInDollars.multiply(BigDecimal.valueOf(100)).longValueExact();
    legacySystem.chargeCustomerInCents(customerId, amountInCents);
}
}