public class Task21 {
    interface DeploymentStrategy {
        void executeRollout(String buildArtifact, LoadBalancerController lb);
        void rollback(String buildArtifact, LoadBalancerController lb);
    }

    static class BlueGreenRollout implements DeploymentStrategy {
        @Override
        public void executeRollout(String buildArtifact, LoadBalancerController lb) {
            System.out.println("BlueGreenRollout: deploying " + buildArtifact + " to green environment");
            lb.shiftTraffic("green", 100);
        }

        @Override
        public void rollback(String buildArtifact, LoadBalancerController lb) {
            System.out.println("BlueGreenRollout: rolling back " + buildArtifact + " to blue environment");
            lb.shiftTraffic("blue", 100);
        }
    }

    static class CanaryTrafficRollout implements DeploymentStrategy {
        @Override
        public void executeRollout(String buildArtifact, LoadBalancerController lb) {
            System.out.println("CanaryTrafficRollout: deploying canary " + buildArtifact);
            lb.shiftTraffic("canary", 10);
        }

        @Override
        public void rollback(String buildArtifact, LoadBalancerController lb) {
            System.out.println("CanaryTrafficRollout: draining canary " + buildArtifact);
            lb.shiftTraffic("stable", 100);
        }
    }

    static class RollingBatchRollout implements DeploymentStrategy {
        @Override
        public void executeRollout(String buildArtifact, LoadBalancerController lb) {
            System.out.println("RollingBatchRollout: updating instances in batches with " + buildArtifact);
            lb.shiftTraffic("rolling-pool", 100);
        }

        @Override
        public void rollback(String buildArtifact, LoadBalancerController lb) {
            System.out.println("RollingBatchRollout: reverting updated batch for " + buildArtifact);
            lb.shiftTraffic("previous-pool", 100);
        }
    }

    static class ArtifactSignatureVerifier {
        public boolean verify(String buildArtifact) {
            System.out.println("ArtifactSignatureVerifier: verified signature for " + buildArtifact);
            return buildArtifact != null && !buildArtifact.isBlank();
        }
    }

    static class SmokeTestRunner {
        public boolean run(String buildArtifact) {
            System.out.println("SmokeTestRunner: smoke tests passed for " + buildArtifact);
            return true;
        }
    }

    static class LoadBalancerController {
        public void shiftTraffic(String targetPool, int percentage) {
            System.out.println("LoadBalancerController: shifted " + percentage + "% traffic to " + targetPool);
        }
    }

    static class HealthProbeMonitor {
        public boolean isHealthy(String buildArtifact) {
            boolean healthy = !buildArtifact.contains("bad");
            System.out.println("HealthProbeMonitor: " + buildArtifact + " healthy=" + healthy);
            return healthy;
        }
    }

    static class ReleaseFacade {
        private final ArtifactSignatureVerifier signatureVerifier = new ArtifactSignatureVerifier();
        private final SmokeTestRunner smokeTestRunner = new SmokeTestRunner();
        private final LoadBalancerController loadBalancer = new LoadBalancerController();
        private final HealthProbeMonitor healthProbeMonitor = new HealthProbeMonitor();
        private DeploymentStrategy strategy;

        public ReleaseFacade(DeploymentStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(DeploymentStrategy strategy) {
            this.strategy = strategy;
        }

        public boolean deployRelease(String buildArtifact) {
            if (!signatureVerifier.verify(buildArtifact) || !smokeTestRunner.run(buildArtifact)) {
                System.out.println("ReleaseLog: pre-deploy verification failed for " + buildArtifact);
                return false;
            }
            strategy.executeRollout(buildArtifact, loadBalancer);
            if (!healthProbeMonitor.isHealthy(buildArtifact)) {
                strategy.rollback(buildArtifact, loadBalancer);
                System.out.println("ReleaseLog: rolled back failed release " + buildArtifact);
                return false;
            }
            System.out.println("ReleaseLog: release " + buildArtifact + " succeeded");
            return true;
        }
    }

    public static void main(String[] args) {
        ReleaseFacade release = new ReleaseFacade(new BlueGreenRollout());
        release.deployRelease("payments-v2.1.0");
        release.setStrategy(new CanaryTrafficRollout());
        release.deployRelease("payments-v2.2.0-bad");
        release.setStrategy(new RollingBatchRollout());
        release.deployRelease("payments-v2.2.1");
    }
}
