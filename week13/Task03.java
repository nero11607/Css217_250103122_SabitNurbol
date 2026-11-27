public class Task03 {
    interface ShippingRateStrategy {
        double calculate(double weightKg, double distanceKm);
    }

    static class StandardGroundStrategy implements ShippingRateStrategy {
        @Override
        public double calculate(double weightKg, double distanceKm) {
            return 5.0 + weightKg * 1.2 + distanceKm * 0.05;
        }
    }

    static class ExpressAirStrategy implements ShippingRateStrategy {
        @Override
        public double calculate(double weightKg, double distanceKm) {
            return 15.0 + weightKg * 2.8 + distanceKm * 0.12;
        }
    }

    static class SameDayCourierStrategy implements ShippingRateStrategy {
        @Override
        public double calculate(double weightKg, double distanceKm) {
            return 25.0 + weightKg * 3.5 + distanceKm * 0.25;
        }
    }

    static class Order {
        private final String orderId;
        private final double weightKg;
        private final double distanceKm;
        private final String destination;

        public Order(String orderId, double weightKg, double distanceKm, String destination) {
            this.orderId = orderId;
            this.weightKg = weightKg;
            this.distanceKm = distanceKm;
            this.destination = destination;
        }

        public String getOrderId() {
            return orderId;
        }

        public double getWeightKg() {
            return weightKg;
        }

        public double getDistanceKm() {
            return distanceKm;
        }

        public String getDestination() {
            return destination;
        }
    }

    static class BoxSelector {
        public String selectBox(double weightKg) {
            String box = weightKg <= 2.0 ? "SmallBox" : weightKg <= 10.0 ? "MediumBox" : "LargeBox";
            System.out.println("BoxSelector: selected " + box + " for " + weightKg + "kg");
            return box;
        }
    }

    static class RateCalculator {
        public double computeCost(Order order, ShippingRateStrategy strategy) {
            double cost = strategy.calculate(order.getWeightKg(), order.getDistanceKm());
            System.out.println("RateCalculator: computed cost = $" + String.format("%.2f", cost));
            return cost;
        }
    }

    static class LabelPrinter {
        public void print(Order order, String boxType, double cost) {
            System.out.println("LabelPrinter: [" + order.getOrderId() + "] -> " + order.getDestination()
                    + " | Box=" + boxType + " | Cost=$" + String.format("%.2f", cost));
        }
    }

    static class ShippingFacade {
        private final BoxSelector boxSelector = new BoxSelector();
        private final RateCalculator rateCalculator = new RateCalculator();
        private final LabelPrinter labelPrinter = new LabelPrinter();
        private ShippingRateStrategy strategy;

        public ShippingFacade(ShippingRateStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(ShippingRateStrategy strategy) {
            this.strategy = strategy;
        }

        public void dispatchOrder(Order order) {
            String box = boxSelector.selectBox(order.getWeightKg());
            double cost = rateCalculator.computeCost(order, strategy);
            labelPrinter.print(order, box, cost);
        }
    }

    public static void main(String[] args) {
        Order order = new Order("ORD-101", 3.5, 120.0, "Astana");
        ShippingFacade shipping = new ShippingFacade(new StandardGroundStrategy());
        shipping.dispatchOrder(order);
        shipping.setStrategy(new ExpressAirStrategy());
        shipping.dispatchOrder(order);
        shipping.setStrategy(new SameDayCourierStrategy());
        shipping.dispatchOrder(order);
    }
}
