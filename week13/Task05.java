public class Task05 {
    interface BrewingStrategy {
        int getWaterTemperatureCelsius();
        int getExtractionSeconds();
        String getGrindSize();
    }

    static class EspressoPullStrategy implements BrewingStrategy {
        @Override
        public int getWaterTemperatureCelsius() {
            return 93;
        }

        @Override
        public int getExtractionSeconds() {
            return 28;
        }

        @Override
        public String getGrindSize() {
            return "Fine";
        }
    }

    static class PourOverStrategy implements BrewingStrategy {
        @Override
        public int getWaterTemperatureCelsius() {
            return 96;
        }

        @Override
        public int getExtractionSeconds() {
            return 180;
        }

        @Override
        public String getGrindSize() {
            return "Medium-Fine";
        }
    }

    static class FrenchPressStrategy implements BrewingStrategy {
        @Override
        public int getWaterTemperatureCelsius() {
            return 94;
        }

        @Override
        public int getExtractionSeconds() {
            return 240;
        }

        @Override
        public String getGrindSize() {
            return "Coarse";
        }
    }

    static class BeanGrinder {
        public void grind(String beanType, String grindSize) {
            System.out.println("BeanGrinder: grinding " + beanType + " (" + grindSize + ")");
        }
    }

    static class WaterBoiler {
        public void heat(int temperatureCelsius) {
            System.out.println("WaterBoiler: heated water to " + temperatureCelsius + "C");
        }
    }

    static class BrewChamber {
        public void brew(String beanType, int seconds) {
            System.out.println("BrewChamber: extracting " + beanType + " for " + seconds + "s");
        }
    }

    static class BaristaFacade {
        private final BeanGrinder beanGrinder = new BeanGrinder();
        private final WaterBoiler waterBoiler = new WaterBoiler();
        private final BrewChamber brewChamber = new BrewChamber();
        private BrewingStrategy strategy;

        public BaristaFacade(BrewingStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(BrewingStrategy strategy) {
            this.strategy = strategy;
        }

        public void prepareBeverage(String beanType) {
            beanGrinder.grind(beanType, strategy.getGrindSize());
            waterBoiler.heat(strategy.getWaterTemperatureCelsius());
            brewChamber.brew(beanType, strategy.getExtractionSeconds());
        }
    }

    public static void main(String[] args) {
        BaristaFacade barista = new BaristaFacade(new EspressoPullStrategy());
        barista.prepareBeverage("Ethiopia Yirgacheffe");
        barista.setStrategy(new PourOverStrategy());
        barista.prepareBeverage("Colombia Huila");
        barista.setStrategy(new FrenchPressStrategy());
        barista.prepareBeverage("Sumatra Mandheling");
    }
}
