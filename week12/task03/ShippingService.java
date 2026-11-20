public class ShippingService {
    public void dispatch(String productId, int quantity, String address) {
        System.out.println("Dispatching " + quantity + "x " + productId + " to " + address);
    }
}
