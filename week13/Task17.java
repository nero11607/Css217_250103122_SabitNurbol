public class Task17 {
    interface MatchmakingStrategy {
        String matchPlayers(int partySize, int avgPingMs, int waitSeconds);
    }

    static class StrictSkillMmrStrategy implements MatchmakingStrategy {
        @Override
        public String matchPlayers(int partySize, int avgPingMs, int waitSeconds) {
            return "STRICT_MMR_LOBBY(party=" + partySize + ", ping=" + avgPingMs + "ms)";
        }
    }

    static class LowLatencyPriorityStrategy implements MatchmakingStrategy {
        @Override
        public String matchPlayers(int partySize, int avgPingMs, int waitSeconds) {
            return "LOW_LATENCY_LOBBY(ping=" + avgPingMs + "ms)";
        }
    }

    static class CasualQuickFillStrategy implements MatchmakingStrategy {
        @Override
        public String matchPlayers(int partySize, int avgPingMs, int waitSeconds) {
            return "CASUAL_QUICKFILL_LOBBY(wait=" + waitSeconds + "s)";
        }
    }

    static class PlayerQueueManager {
        public int getQueueWaitSeconds(String partyId) {
            int wait = partyId.contains("LONG") ? 95 : 15;
            System.out.println("PlayerQueueManager: " + partyId + " waitTime = " + wait + "s");
            return wait;
        }
    }

    static class RegionPingEvaluator {
        public int measurePing(String partyId) {
            System.out.println("RegionPingEvaluator: avg ping = 32ms");
            return 32;
        }
    }

    static class PartyBalanceValidator {
        public boolean validate(int partySize) {
            System.out.println("PartyBalanceValidator: validated party size = " + partySize);
            return partySize > 0 && partySize <= 5;
        }
    }

    static class GameServerProvisioner {
        public void provision(String lobbySpec) {
            System.out.println("GameServerProvisioner: provisioned server for " + lobbySpec);
        }
    }

    static class MatchmakingFacade {
        private final PlayerQueueManager queueManager = new PlayerQueueManager();
        private final RegionPingEvaluator pingEvaluator = new RegionPingEvaluator();
        private final PartyBalanceValidator balanceValidator = new PartyBalanceValidator();
        private final GameServerProvisioner serverProvisioner = new GameServerProvisioner();
        private MatchmakingStrategy strategy;

        public MatchmakingFacade(MatchmakingStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(MatchmakingStrategy strategy) {
            this.strategy = strategy;
        }

        public void findMatchAndProvision(String partyId, int partySize) {
            if (!balanceValidator.validate(partySize)) {
                System.out.println("Invalid party size");
                return;
            }
            int waitSeconds = queueManager.getQueueWaitSeconds(partyId);
            int ping = pingEvaluator.measurePing(partyId);
            MatchmakingStrategy active = strategy;
            if (waitSeconds > 60 && !(strategy instanceof CasualQuickFillStrategy)) {
                System.out.println("Wait threshold exceeded; falling back to CasualQuickFillStrategy");
                active = new CasualQuickFillStrategy();
            }
            serverProvisioner.provision(active.matchPlayers(partySize, ping, waitSeconds));
        }
    }

    public static void main(String[] args) {
        MatchmakingFacade matchmaking = new MatchmakingFacade(new StrictSkillMmrStrategy());
        matchmaking.findMatchAndProvision("PARTY-FAST", 3);
        matchmaking.findMatchAndProvision("PARTY-LONG", 3);
    }
}
