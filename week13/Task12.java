import java.util.List;

public class Task12 {
    interface DiscountStrategy {
        double applyDiscount(double subtotal, String loyaltyTier);
    }

    static class SeasonalPercentageDiscount implements DiscountStrategy {
        @Override
        public double applyDiscount(double subtotal, String loyaltyTier) {
            return subtotal * 0.85;
        }
    }

    static class TieredLoyaltyDiscount implements DiscountStrategy {
        @Override
        public double applyDiscount(double subtotal, String loyaltyTier) {
            double rate = "GOLD".equals(loyaltyTier) ? 0.80 : 0.92;
            return subtotal * rate;
        }
    }

    static class BundleThresholdDiscount implements DiscountStrategy {
        @Override
        public double applyDiscount(double subtotal, String loyaltyTier) {
            return subtotal >= 100.0 ? subtotal - 20.0 : subtotal;
        }
    }

    static class CartAnalyzer {
        public double calculateSubtotal(List<String> cart) {
            double subtotal = cart.size() * 45.0;
            System.out.println("CartAnalyzer: " + cart.size() + " items, subtotal=$" + subtotal);
            return subtotal;
        }
    }

    static class MemberProfileService {
        public String getLoyaltyTier(String userId) {
            System.out.println("MemberProfileService: fetched tier GOLD for " + userId);
            return "GOLD";
        }
    }

    static class CouponValidator {
        public boolean isEligible(String userId, double subtotal) {
            System.out.println("CouponValidator: user " + userId + " eligible for discount");
            return subtotal > 0;
        }
    }

    static class PointsLedger {
        public void addRewardPoints(String userId, double finalAmount) {
            int points = (int) (finalAmount / 10);
            System.out.println("PointsLedger: credited " + points + " points to " + userId);
        }
    }

    static class BillingFacade {
        private final CartAnalyzer cartAnalyzer = new CartAnalyzer();
        private final MemberProfileService profileService = new MemberProfileService();
        private final CouponValidator couponValidator = new CouponValidator();
        private final PointsLedger pointsLedger = new PointsLedger();
        private DiscountStrategy strategy;

        public BillingFacade(DiscountStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(DiscountStrategy strategy) {
            this.strategy = strategy;
        }

        public double finalizeCart(String userId, List<String> cart) {
            double subtotal = cartAnalyzer.calculateSubtotal(cart);
            String tier = profileService.getLoyaltyTier(userId);
            double finalTotal = couponValidator.isEligible(userId, subtotal)
                    ? strategy.applyDiscount(subtotal, tier)
                    : subtotal;
            pointsLedger.addRewardPoints(userId, finalTotal);
            System.out.println("BillingFacade: final total = $" + String.format("%.2f", finalTotal));
            return finalTotal;
        }
    }

    public static void main(String[] args) {
        List<String> cart = List.of("Jacket", "Sneakers", "Cap");
        BillingFacade billing = new BillingFacade(new SeasonalPercentageDiscount());
        billing.finalizeCart("MEM-88", cart);
        billing.setStrategy(new TieredLoyaltyDiscount());
        billing.finalizeCart("MEM-88", cart);
        billing.setStrategy(new BundleThresholdDiscount());
        billing.finalizeCart("MEM-88", cart);
    }
}
