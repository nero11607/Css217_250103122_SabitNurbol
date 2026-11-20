public class OrderFacade {
    private final InventoryManager inventoryManager;
    private final PaymentGateway paymentGateway;
    private final ShippingService shippingService;

    public OrderFacade() {
        this.inventoryManager = new InventoryManager();
        this.paymentGateway = new PaymentGateway();
        this.shippingService = new ShippingService();
    }

    public boolean placeOrder(String productId, int quantity, String paymentInfo, String address) {
        if (!inventoryManager.checkStock(productId, quantity)) {
            System.out.println("Order failed: insufficient stock.");
            return false;
        }
        double totalAmount = quantity * 29.99;
        if (!paymentGateway.charge(paymentInfo, totalAmount)) {
            System.out.println("Order failed: payment declined.");
            return false;
        }
        inventoryManager.reserveStock(productId, quantity);
        shippingService.dispatch(productId, quantity, address);
        return true;
    }
}
