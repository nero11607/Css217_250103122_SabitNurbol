import java.util.List;

public class Task19 {
    interface FlightPathStrategy {
        String computeFlightCommand(List<String> waypoints, double altitudeMeters);
    }

    static class EnergyOptimalFlight implements FlightPathStrategy {
        @Override
        public String computeFlightCommand(List<String> waypoints, double altitudeMeters) {
            return "ECO_CRUISE(" + waypoints.size() + " pts @ " + altitudeMeters + "m)";
        }
    }

    static class WindCompensationFlight implements FlightPathStrategy {
        @Override
        public String computeFlightCommand(List<String> waypoints, double altitudeMeters) {
            return "WIND_STABILIZED(" + waypoints.size() + " pts @ " + altitudeMeters + "m)";
        }
    }

    static class HighObstacleClearanceFlight implements FlightPathStrategy {
        @Override
        public String computeFlightCommand(List<String> waypoints, double altitudeMeters) {
            return "HIGH_ALTITUDE(" + waypoints.size() + " pts @ " + (altitudeMeters + 40.0) + "m)";
        }
    }

    static class GpsBarometerUnit {
        public double readAltitudeMeters() {
            System.out.println("GpsBarometerUnit: altitude = 55.0m");
            return 55.0;
        }
    }

    static class BatteryHealthMonitor {
        private final double voltage;

        public BatteryHealthMonitor(double voltage) {
            this.voltage = voltage;
        }

        public double readVoltage() {
            System.out.println("BatteryHealthMonitor: voltage = " + voltage + "V");
            return voltage;
        }
    }

    static class PayloadLatchController {
        public boolean verifyLocked() {
            System.out.println("PayloadLatchController: payload latch locked and verified");
            return true;
        }
    }

    static class RotorEscDriver {
        public void sendCommand(String command) {
            System.out.println("RotorEscDriver: executing " + command);
        }

        public void emergencyLand() {
            System.out.println("RotorEscDriver: CRITICAL BATTERY! Executing emergency landing");
        }
    }

    static class FlightControlFacade {
        private final GpsBarometerUnit gpsBarometer = new GpsBarometerUnit();
        private final BatteryHealthMonitor batteryMonitor;
        private final PayloadLatchController latchController = new PayloadLatchController();
        private final RotorEscDriver rotorEscDriver = new RotorEscDriver();
        private FlightPathStrategy strategy;

        public FlightControlFacade(FlightPathStrategy strategy, double batteryVoltage) {
            this.strategy = strategy;
            this.batteryMonitor = new BatteryHealthMonitor(batteryVoltage);
        }

        public void setStrategy(FlightPathStrategy strategy) {
            this.strategy = strategy;
        }

        public void executeDeliveryLeg(List<String> waypoints) {
            if (!latchController.verifyLocked()) {
                System.out.println("Pre-flight failed: payload latch unlocked");
                return;
            }
            if (batteryMonitor.readVoltage() < 14.0) {
                rotorEscDriver.emergencyLand();
                return;
            }
            double altitude = gpsBarometer.readAltitudeMeters();
            rotorEscDriver.sendCommand(strategy.computeFlightCommand(waypoints, altitude));
        }
    }

    public static void main(String[] args) {
        List<String> route = List.of("WP-1", "WP-2", "DROP-ZONE");
        FlightControlFacade drone = new FlightControlFacade(new EnergyOptimalFlight(), 16.4);
        drone.executeDeliveryLeg(route);
        drone.setStrategy(new HighObstacleClearanceFlight());
        drone.executeDeliveryLeg(route);
        FlightControlFacade lowBatteryDrone = new FlightControlFacade(new WindCompensationFlight(), 13.2);
        lowBatteryDrone.executeDeliveryLeg(route);
    }
}
