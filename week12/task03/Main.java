public class Main {
    public static void main(String[] args) {
        OrderFacade orderFacade = new OrderFacade();
        System.out.println("Order placed: " + orderFacade.placeOrder("SKU-42", 2, "VISA-1234", "Almaty, Abay 10"));
    }
}
