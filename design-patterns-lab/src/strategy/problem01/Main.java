package strategy.problem01;

import java.util.ArrayList;
import java.util.List;

// 1. The Strategy Interface
interface PaymentStrategy {
    void pay(double amount);
}

// 2a. Concrete Strategy: Credit Card
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Processing Credit Card payment of $" + amount + " for " + cardHolderName);
    }
}

// 2b. Concrete Strategy: PayPal
class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Processing PayPal payment of $" + amount + " linked to account: " + email);
    }
}

// 2c. Concrete Strategy: Crypto
class CryptoPayment implements PaymentStrategy {
    private String walletAddress;

    public CryptoPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Sending $" + amount + " in cryptocurrency to wallet: " + walletAddress);
    }
}

// 3. The Context Class
class ShoppingCart {
    private List<Double> itemPrices;
    private PaymentStrategy paymentStrategy;

    public ShoppingCart() {
        this.itemPrices = new ArrayList<>();
    }

    public void addItem(double price) {
        this.itemPrices.add(price);
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public double calculateTotal() {
        double sum = 0.0;
        for (double price : itemPrices) {
            sum += price;
        }
        return sum;
    }

    public void checkout() {
        if (paymentStrategy == null) {
            System.out.println("Error: Please select a payment strategy before checking out!");
            return;
        }
        double totalAmount = calculateTotal();
        paymentStrategy.pay(totalAmount);
    }
}

public class Main {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(45.50);
        cart.addItem(15.00);
        cart.addItem(9.50); // Total: $70.00

        System.out.println("--- Customer selects Credit Card ---");
        cart.setPaymentStrategy(new CreditCardPayment("4111-2222-3333-4444", "Alice Smith"));
        cart.checkout();

        System.out.println("\n--- Customer changes mind and switches to PayPal ---");
        cart.setPaymentStrategy(new PayPalPayment("alice.smith@example.com"));
        cart.checkout();

        System.out.println("\n--- Customer decides to pay with Crypto ---");
        cart.setPaymentStrategy(new CryptoPayment("0x71C...9B8"));
        cart.checkout();
    }
}