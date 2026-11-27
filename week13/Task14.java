public class Task14 {
    interface CalorieCalculationStrategy {
        double calculateCalories(double avgHeartRate, int durationMinutes, double weightKg);
    }

    static class CardioMetStrategy implements CalorieCalculationStrategy {
        @Override
        public double calculateCalories(double avgHeartRate, int durationMinutes, double weightKg) {
            return (avgHeartRate * 0.07) * durationMinutes * (weightKg / 70.0);
        }
    }

    static class StrengthLoadStrategy implements CalorieCalculationStrategy {
        @Override
        public double calculateCalories(double avgHeartRate, int durationMinutes, double weightKg) {
            return (avgHeartRate * 0.05) * durationMinutes * (weightKg / 70.0);
        }
    }

    static class HiitIntervalStrategy implements CalorieCalculationStrategy {
        @Override
        public double calculateCalories(double avgHeartRate, int durationMinutes, double weightKg) {
            return (avgHeartRate * 0.095) * durationMinutes * (weightKg / 70.0);
        }
    }

    static class HeartRateSensor {
        public double computeAverageBpm(int[] telemetrySamples) {
            int sum = 0;
            for (int sample : telemetrySamples) {
                sum += sample;
            }
            double avg = telemetrySamples.length == 0 ? 0 : (double) sum / telemetrySamples.length;
            System.out.println("HeartRateSensor: avg BPM = " + String.format("%.1f", avg));
            return avg;
        }
    }

    static class SessionTimer {
        public int getDurationMinutes(int sampleCount) {
            int minutes = Math.max(10, sampleCount * 5);
            System.out.println("SessionTimer: duration = " + minutes + " min");
            return minutes;
        }
    }

    static class UserBiometricsStore {
        public double getUserWeightKg(String userId) {
            System.out.println("UserBiometricsStore: weight for " + userId + " = 75.0 kg");
            return 75.0;
        }
    }

    static class ActivityLogRepository {
        public void saveSession(String userId, double calories) {
            System.out.println("ActivityLogRepository: saved " + String.format("%.1f", calories) + " kcal for " + userId);
        }
    }

    static class WorkoutSessionFacade {
        private final HeartRateSensor heartRateSensor = new HeartRateSensor();
        private final SessionTimer sessionTimer = new SessionTimer();
        private final UserBiometricsStore biometricsStore = new UserBiometricsStore();
        private final ActivityLogRepository logRepository = new ActivityLogRepository();
        private CalorieCalculationStrategy strategy;

        public WorkoutSessionFacade(CalorieCalculationStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(CalorieCalculationStrategy strategy) {
            this.strategy = strategy;
        }

        public double completeWorkout(String userId, int[] telemetrySamples) {
            double avgBpm = heartRateSensor.computeAverageBpm(telemetrySamples);
            int minutes = sessionTimer.getDurationMinutes(telemetrySamples.length);
            double weightKg = biometricsStore.getUserWeightKg(userId);
            double calories = strategy.calculateCalories(avgBpm, minutes, weightKg);
            logRepository.saveSession(userId, calories);
            return calories;
        }
    }

    public static void main(String[] args) {
        int[] samples = {130, 145, 155, 160, 150};
        WorkoutSessionFacade workout = new WorkoutSessionFacade(new CardioMetStrategy());
        workout.completeWorkout("ATH-01", samples);
        workout.setStrategy(new StrengthLoadStrategy());
        workout.completeWorkout("ATH-01", samples);
        workout.setStrategy(new HiitIntervalStrategy());
        workout.completeWorkout("ATH-01", samples);
    }
}
