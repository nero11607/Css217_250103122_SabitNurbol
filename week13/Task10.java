public class Task10 {
    interface RoutingStrategy {
        String calculateRoute(String origin, String destination, String trafficStatus);
    }

    static class FastestTimeRouting implements RoutingStrategy {
        @Override
        public String calculateRoute(String origin, String destination, String trafficStatus) {
            return "Fastest route " + origin + " -> " + destination + " (traffic: " + trafficStatus + ")";
        }
    }

    static class ShortestDistanceRouting implements RoutingStrategy {
        @Override
        public String calculateRoute(String origin, String destination, String trafficStatus) {
            return "Shortest route " + origin + " -> " + destination + " (14.2 km)";
        }
    }

    static class AvoidTollsRouting implements RoutingStrategy {
        @Override
        public String calculateRoute(String origin, String destination, String trafficStatus) {
            return "Toll-free route " + origin + " -> " + destination;
        }
    }

    static class GpsTracker {
        public String getCurrentLocation() {
            return "43.2389N, 76.8897E";
        }
    }

    static class MapTileLoader {
        public void loadTiles(String origin, String destination) {
            System.out.println("MapTileLoader: loaded tiles from " + origin + " to " + destination);
        }
    }

    static class TrafficFeedClient {
        public String getTrafficStatus(String destination) {
            System.out.println("TrafficFeedClient: fetched live traffic near " + destination);
            return "MODERATE";
        }
    }

    static class VoiceGuidanceUnit {
        public void announce(String route) {
            System.out.println("VoiceGuidanceUnit: " + route);
        }
    }

    static class NavigationFacade {
        private final GpsTracker gpsTracker = new GpsTracker();
        private final MapTileLoader mapTileLoader = new MapTileLoader();
        private final TrafficFeedClient trafficFeedClient = new TrafficFeedClient();
        private final VoiceGuidanceUnit voiceGuidanceUnit = new VoiceGuidanceUnit();
        private RoutingStrategy strategy;
        private String activeDestination;

        public NavigationFacade(RoutingStrategy strategy) {
            this.strategy = strategy;
        }

        public void startNavigation(String origin, String destination) {
            this.activeDestination = destination;
            System.out.println("GPS lock: " + gpsTracker.getCurrentLocation());
            mapTileLoader.loadTiles(origin, destination);
            String traffic = trafficFeedClient.getTrafficStatus(destination);
            voiceGuidanceUnit.announce(strategy.calculateRoute(origin, destination, traffic));
        }

        public void setRoutingStrategy(RoutingStrategy strategy) {
            this.strategy = strategy;
            if (activeDestination != null) {
                String traffic = trafficFeedClient.getTrafficStatus(activeDestination);
                voiceGuidanceUnit.announce("Recalculated: " + strategy.calculateRoute(gpsTracker.getCurrentLocation(), activeDestination, traffic));
            }
        }
    }

    public static void main(String[] args) {
        NavigationFacade nav = new NavigationFacade(new FastestTimeRouting());
        nav.startNavigation("Home", "Airport");
        nav.setRoutingStrategy(new AvoidTollsRouting());
        nav.setRoutingStrategy(new ShortestDistanceRouting());
    }
}
