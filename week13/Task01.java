public class Task01 {
    interface LightingStrategy {
        int getBrightnessPercent(int ambientLux);
        int getColorTemperatureKelvin();
    }

    static class EcoDimStrategy implements LightingStrategy {
        @Override
        public int getBrightnessPercent(int ambientLux) {
            return Math.max(20, 60 - ambientLux / 10);
        }

        @Override
        public int getColorTemperatureKelvin() {
            return 2700;
        }
    }

    static class ReadingFocusStrategy implements LightingStrategy {
        @Override
        public int getBrightnessPercent(int ambientLux) {
            return Math.max(70, 95 - ambientLux / 20);
        }

        @Override
        public int getColorTemperatureKelvin() {
            return 4500;
        }
    }

    static class AmbientPartyStrategy implements LightingStrategy {
        @Override
        public int getBrightnessPercent(int ambientLux) {
            return 55;
        }

        @Override
        public int getColorTemperatureKelvin() {
            return 3200;
        }
    }

    static class PowerGridMonitor {
        public boolean hasStableLoad(String roomId) {
            System.out.println("PowerGridMonitor: stable load verified for " + roomId);
            return true;
        }
    }

    static class DimmerHardware {
        public void setLighting(String roomId, int brightness, int kelvin) {
            System.out.println("DimmerHardware: " + roomId + " set to " + brightness + "% at " + kelvin + "K");
        }
    }

    static class RoomSensor {
        public int readAmbientLux(String roomId) {
            int lux = 180;
            System.out.println("RoomSensor: " + roomId + " ambient light = " + lux + " lux");
            return lux;
        }
    }

    static class HomeLightingFacade {
        private final PowerGridMonitor powerGridMonitor = new PowerGridMonitor();
        private final DimmerHardware dimmerHardware = new DimmerHardware();
        private final RoomSensor roomSensor = new RoomSensor();
        private LightingStrategy strategy;

        public HomeLightingFacade(LightingStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(LightingStrategy strategy) {
            this.strategy = strategy;
        }

        public void applyScene(String roomId) {
            if (!powerGridMonitor.hasStableLoad(roomId)) {
                System.out.println("Power load unstable for " + roomId);
                return;
            }
            int lux = roomSensor.readAmbientLux(roomId);
            dimmerHardware.setLighting(roomId, strategy.getBrightnessPercent(lux), strategy.getColorTemperatureKelvin());
        }
    }

    public static void main(String[] args) {
        HomeLightingFacade lighting = new HomeLightingFacade(new EcoDimStrategy());
        lighting.applyScene("LivingRoom");
        lighting.setStrategy(new ReadingFocusStrategy());
        lighting.applyScene("Study");
        lighting.setStrategy(new AmbientPartyStrategy());
        lighting.applyScene("Patio");
    }
}
