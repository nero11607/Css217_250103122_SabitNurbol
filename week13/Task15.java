public class Task15 {
    interface ClimateStrategy {
        String computeState(double tempC, int humidityPct);
    }

    static class EcoAwayStrategy implements ClimateStrategy {
        @Override
        public String computeState(double tempC, int humidityPct) {
            return tempC > 26.0 ? "LOW_COOL|FAN_LOW" : "IDLE|FAN_OFF";
        }
    }

    static class ComfortHoldStrategy implements ClimateStrategy {
        @Override
        public String computeState(double tempC, int humidityPct) {
            return tempC > 23.0 ? "COOL|FAN_MED" : "HOLD|FAN_LOW";
        }
    }

    static class RapidPreconditionStrategy implements ClimateStrategy {
        @Override
        public String computeState(double tempC, int humidityPct) {
            return "TURBO_COOL|FAN_HIGH";
        }
    }

    static class ThermostatSensor {
        public double readTemperature(String zoneId) {
            System.out.println("ThermostatSensor: " + zoneId + " temp = 25.5C");
            return 25.5;
        }
    }

    static class HumidityMonitor {
        public int readHumidity(String zoneId) {
            System.out.println("HumidityMonitor: " + zoneId + " humidity = 48%");
            return 48;
        }
    }

    static class DamperActuator {
        public void adjust(String zoneId, String fanState) {
            System.out.println("DamperActuator: " + zoneId + " damper -> " + fanState);
        }
    }

    static class CompressorRelay {
        public void setMode(String zoneId, String compressorState) {
            System.out.println("CompressorRelay: " + zoneId + " compressor -> " + compressorState);
        }
    }

    static class HvacControlFacade {
        private final ThermostatSensor thermostatSensor = new ThermostatSensor();
        private final HumidityMonitor humidityMonitor = new HumidityMonitor();
        private final DamperActuator damperActuator = new DamperActuator();
        private final CompressorRelay compressorRelay = new CompressorRelay();
        private ClimateStrategy strategy;

        public HvacControlFacade(ClimateStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(ClimateStrategy strategy) {
            this.strategy = strategy;
        }

        public void regulateZone(String zoneId) {
            double temp = thermostatSensor.readTemperature(zoneId);
            int humidity = humidityMonitor.readHumidity(zoneId);
            String[] states = strategy.computeState(temp, humidity).split("\\|");
            compressorRelay.setMode(zoneId, states[0]);
            damperActuator.adjust(zoneId, states[1]);
        }
    }

    public static void main(String[] args) {
        HvacControlFacade hvac = new HvacControlFacade(new EcoAwayStrategy());
        hvac.regulateZone("Zone-A");
        hvac.setStrategy(new ComfortHoldStrategy());
        hvac.regulateZone("Zone-A");
        hvac.setStrategy(new RapidPreconditionStrategy());
        hvac.regulateZone("Zone-A");
    }
}
