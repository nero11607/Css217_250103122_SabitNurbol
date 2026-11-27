public class Task20 {
    interface AllocationStrategy {
        String getTargetWeights();
    }

    static class AggressiveEquityStrategy implements AllocationStrategy {
        @Override
        public String getTargetWeights() {
            return "85% Equity / 15% Bonds";
        }
    }

    static class BalancedIndexStrategy implements AllocationStrategy {
        @Override
        public String getTargetWeights() {
            return "60% Equity / 40% Bonds";
        }
    }

    static class CapitalPreservationStrategy implements AllocationStrategy {
        @Override
        public String getTargetWeights() {
            return "25% Equity / 75% Bonds";
        }
    }

    static class MarketPriceFeed {
        public double getPortfolioValue(String accountId) {
            System.out.println("MarketPriceFeed: fetched live valuation for " + accountId + " = $120000");
            return 120000.0;
        }
    }

    static class DriftAnalyzer {
        public String analyzeDrift(double valuation, String targetWeights) {
            System.out.println("DriftAnalyzer: analyzing $" + valuation + " against [" + targetWeights + "]");
            return "SELL_EQ_4000|BUY_BOND_4000";
        }
    }

    static class TaxLotEvaluator {
        public String prioritizeTaxLossLots(String netDriftOrders) {
            System.out.println("TaxLotEvaluator: prioritized tax-loss harvesting lots for " + netDriftOrders);
            return "TLH(" + netDriftOrders + ")";
        }
    }

    static class OrderExecutionBroker {
        public void submitOrders(String accountId, String orders) {
            System.out.println("OrderExecutionBroker: submitted for " + accountId + " -> " + orders);
        }
    }

    static class PortfolioFacade {
        private final MarketPriceFeed priceFeed = new MarketPriceFeed();
        private final DriftAnalyzer driftAnalyzer = new DriftAnalyzer();
        private final TaxLotEvaluator taxLotEvaluator = new TaxLotEvaluator();
        private final OrderExecutionBroker executionBroker = new OrderExecutionBroker();
        private AllocationStrategy strategy;

        public PortfolioFacade(AllocationStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(AllocationStrategy strategy) {
            this.strategy = strategy;
        }

        public void rebalanceAccount(String accountId) {
            double value = priceFeed.getPortfolioValue(accountId);
            String driftOrders = driftAnalyzer.analyzeDrift(value, strategy.getTargetWeights());
            String optimizedOrders = taxLotEvaluator.prioritizeTaxLossLots(driftOrders);
            executionBroker.submitOrders(accountId, optimizedOrders);
        }
    }

    public static void main(String[] args) {
        PortfolioFacade portfolio = new PortfolioFacade(new BalancedIndexStrategy());
        portfolio.rebalanceAccount("ACC-771");
        portfolio.setStrategy(new AggressiveEquityStrategy());
        portfolio.rebalanceAccount("ACC-771");
        portfolio.setStrategy(new CapitalPreservationStrategy());
        portfolio.rebalanceAccount("ACC-771");
    }
}
