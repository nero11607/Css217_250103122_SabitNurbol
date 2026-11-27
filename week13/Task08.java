public class Task08 {
    interface PaymentStrategy {
        boolean authorizeAndPay(double amount);
    }

    static class CreditCardPayment implements PaymentStrategy {
        private final String cardNumber;

        public CreditCardPayment(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        @Override
        public boolean authorizeAndPay(double amount) {
            System.out.println("CreditCardPayment: 3DS check & charge $" + String.format("%.2f", amount) + " on " + cardNumber);
            return !cardNumber.endsWith("0000");
        }
    }

    static class DigitalWalletPayment implements PaymentStrategy {
        private final String walletId;

        public DigitalWalletPayment(String walletId) {
            this.walletId = walletId;
        }

        @Override
        public boolean authorizeAndPay(double amount) {
            System.out.println("DigitalWalletPayment: token auth & transfer $" + String.format("%.2f", amount) + " from " + walletId);
            return true;
        }
    }

    static class BankTransferPayment implements PaymentStrategy {
        private final String iban;

        public BankTransferPayment(String iban) {
            this.iban = iban;
        }

        @Override
        public boolean authorizeAndPay(double amount) {
            System.out.println("BankTransferPayment: IBAN verification & wire $" + String.format("%.2f", amount) + " from " + iban);
            return amount <= 5000.0;
        }
    }

    static class InventoryLockService {
        public boolean lock(String itemId, int quantity) {
            System.out.println("InventoryLockService: locked " + quantity + "x " + itemId);
            return quantity > 0;
        }

        public void release(String itemId, int quantity) {
            System.out.println("InventoryLockService: released " + quantity + "x " + itemId);
        }
    }

    static class TaxEngine {
        public double calculateTotalWithTax(double subtotal) {
            double total = subtotal * 1.12;
            System.out.println("TaxEngine: subtotal=$" + String.format("%.2f", subtotal) + ", total=$" + String.format("%.2f", total));
            return total;
        }
    }

    static class PaymentProcessor {
        public boolean process(PaymentStrategy strategy, double amount) {
            return strategy.authorizeAndPay(amount);
        }
    }

    static class ReceiptGenerator {
        public void printReceipt(String itemId, int quantity, double total) {
            System.out.println("ReceiptGenerator: PAID " + quantity + "x " + itemId + " = $" + String.format("%.2f", total));
        }
    }

    static class CheckoutFacade {
        private final InventoryLockService inventoryLockService = new InventoryLockService();
        private final TaxEngine taxEngine = new TaxEngine();
        private final PaymentProcessor paymentProcessor = new PaymentProcessor();
        private final ReceiptGenerator receiptGenerator = new ReceiptGenerator();
        private PaymentStrategy paymentStrategy;

        public CheckoutFacade(PaymentStrategy paymentStrategy) {
            this.paymentStrategy = paymentStrategy;
        }

        public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
            this.paymentStrategy = paymentStrategy;
        }

        public boolean checkout(String itemId, int quantity, double unitPrice) {
            if (!inventoryLockService.lock(itemId, quantity)) {
                return false;
            }
            double total = taxEngine.calculateTotalWithTax(quantity * unitPrice);
            boolean paid = paymentProcessor.process(paymentStrategy, total);
            if (!paid) {
                System.out.println("Payment failed, releasing inventory...");
                inventoryLockService.release(itemId, quantity);
                return false;
            }
            receiptGenerator.printReceipt(itemId, quantity, total);
            return true;
        }
    }

    public static void main(String[] args) {
        CheckoutFacade checkout = new CheckoutFacade(new CreditCardPayment("4111-XXXX-0000"));
        System.out.println("Attempt 1: " + checkout.checkout("LAPTOP-01", 1, 1000.0));
        checkout.setPaymentStrategy(new DigitalWalletPayment("WALLET-99"));
        System.out.println("Attempt 2: " + checkout.checkout("LAPTOP-01", 1, 1000.0));
        checkout.setPaymentStrategy(new BankTransferPayment("KZ86125KZT5004100100"));
        System.out.println("Attempt 3: " + checkout.checkout("MOUSE-02", 2, 45.0));
    }
}
