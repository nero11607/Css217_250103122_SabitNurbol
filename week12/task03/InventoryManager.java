public class InventoryManager {
    public boolean checkStock(String productId, int quantity) {
        System.out.println("Checking stock for " + productId + " (qty: " + quantity + ")");
        return quantity > 0 && quantity <= 50;
    }

    public void reserveStock(String productId, int quantity) {
        System.out.println("Reserved " + quantity + " unit(s) of " + productId);
    }
}
