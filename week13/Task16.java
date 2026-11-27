public class Task16 {
    interface RiskScoringStrategy {
        String evaluate(int creditScore, double dtiRatio);
    }

    static class ConservativeCreditStrategy implements RiskScoringStrategy {
        @Override
        public String evaluate(int creditScore, double dtiRatio) {
            return (creditScore >= 720 && dtiRatio <= 0.30) ? "APPROVED" : "DECLINED";
        }
    }

    static class StandardRetailStrategy implements RiskScoringStrategy {
        @Override
        public String evaluate(int creditScore, double dtiRatio) {
            return (creditScore >= 650 && dtiRatio <= 0.42) ? "APPROVED" : "MANUAL_REVIEW";
        }
    }

    static class CashFlowGrowthStrategy implements RiskScoringStrategy {
        @Override
        public String evaluate(int creditScore, double dtiRatio) {
            return (creditScore >= 600 && dtiRatio <= 0.50) ? "APPROVED_CONDITIONAL" : "DECLINED";
        }
    }

    static class CreditBureauAdapter {
        public int fetchScore(String applicantId) {
            int score = applicantId.endsWith("99") ? 590 : 710;
            System.out.println("CreditBureauAdapter: score for " + applicantId + " = " + score);
            return score;
        }
    }

    static class DebtToIncomeCalculator {
        public double calculate(double monthlyIncome, double monthlyDebt) {
            double dti = monthlyIncome <= 0 ? 1.0 : monthlyDebt / monthlyIncome;
            System.out.println("DebtToIncomeCalculator: DTI = " + String.format("%.2f", dti));
            return dti;
        }
    }

    static class FraudScreeningEngine {
        public boolean isFlagged(String applicantId) {
            boolean flagged = applicantId.startsWith("FRAUD");
            System.out.println("FraudScreeningEngine: " + applicantId + " flagged=" + flagged);
            return flagged;
        }
    }

    static class ComplianceAuditLogger {
        public void logImmutable(String applicantId, String decision) {
            System.out.println("ComplianceAuditLogger [IMMUTABLE]: applicant=" + applicantId + ", decision=" + decision);
        }
    }

    static class UnderwritingFacade {
        private final CreditBureauAdapter bureauAdapter = new CreditBureauAdapter();
        private final DebtToIncomeCalculator dtiCalculator = new DebtToIncomeCalculator();
        private final FraudScreeningEngine fraudEngine = new FraudScreeningEngine();
        private final ComplianceAuditLogger auditLogger = new ComplianceAuditLogger();
        private RiskScoringStrategy strategy;

        public UnderwritingFacade(RiskScoringStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(RiskScoringStrategy strategy) {
            this.strategy = strategy;
        }

        public String evaluateApplication(String applicantId, double monthlyIncome, double monthlyDebt) {
            if (fraudEngine.isFlagged(applicantId)) {
                String report = "REPORT[" + applicantId + ": REJECTED_FRAUD]";
                auditLogger.logImmutable(applicantId, report);
                return report;
            }
            int score = bureauAdapter.fetchScore(applicantId);
            double dti = dtiCalculator.calculate(monthlyIncome, monthlyDebt);
            String decision = strategy.evaluate(score, dti);
            String report = "REPORT[" + applicantId + ": " + decision + " | score=" + score + "]";
            auditLogger.logImmutable(applicantId, report);
            return report;
        }
    }

    public static void main(String[] args) {
        UnderwritingFacade underwriting = new UnderwritingFacade(new StandardRetailStrategy());
        System.out.println(underwriting.evaluateApplication("FRAUD-001", 6000, 1000));
        System.out.println(underwriting.evaluateApplication("APP-204", 6000, 1800));
        underwriting.setStrategy(new ConservativeCreditStrategy());
        System.out.println(underwriting.evaluateApplication("APP-204", 6000, 1800));
    }
}
