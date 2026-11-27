public class Task22 {
    interface TriagePriorityStrategy {
        int rankUrgency(String patientRecord, String vitals);
    }

    static class StandardSeverityIndex implements TriagePriorityStrategy {
        @Override
        public int rankUrgency(String patientRecord, String vitals) {
            return vitals.contains("CRITICAL") ? 1 : 3;
        }
    }

    static class PediatricFastTrack implements TriagePriorityStrategy {
        @Override
        public int rankUrgency(String patientRecord, String vitals) {
            return 1;
        }
    }

    static class MassCasualtyProtocol implements TriagePriorityStrategy {
        @Override
        public int rankUrgency(String patientRecord, String vitals) {
            return vitals.contains("CRITICAL") ? 1 : 2;
        }
    }

    interface BedMatchingStrategy {
        String selectBed(int urgencyLevel, String availableWards);
    }

    static class ClosestUnitMatch implements BedMatchingStrategy {
        @Override
        public String selectBed(int urgencyLevel, String availableWards) {
            return "ER-Bay-1 (urgency=" + urgencyLevel + ")";
        }
    }

    static class SpecialistWardMatch implements BedMatchingStrategy {
        @Override
        public String selectBed(int urgencyLevel, String availableWards) {
            return urgencyLevel == 1 ? "ICU-Bed-4" : "Cardio-Ward-12";
        }
    }

    static class VitalsMonitor {
        public String checkVitals(String patientRecord) {
            String status = patientRecord.contains("TRAUMA") ? "CRITICAL_BP" : "STABLE";
            System.out.println("VitalsMonitor: " + patientRecord + " -> " + status);
            return status;
        }
    }

    static class LabOrderDispatcher {
        public void dispatch(String patientRecord, int urgencyLevel) {
            System.out.println("LabOrderDispatcher: ordered stat labs for " + patientRecord + " (priority=" + urgencyLevel + ")");
        }
    }

    static class WardCapacityTracker {
        public String getAvailableWards() {
            System.out.println("WardCapacityTracker: ICU, ER, Cardio available");
            return "ICU,ER,Cardio";
        }

        public void reserveBed(String bedId, String patientRecord) {
            System.out.println("WardCapacityTracker: reserved " + bedId + " for " + patientRecord);
        }
    }

    static class AdmissionNotifier {
        public void notifyStaff(String patientRecord, String bedId, int urgencyLevel) {
            System.out.println("AdmissionNotifier: alerted team for " + patientRecord + " -> " + bedId + " (P" + urgencyLevel + ")");
        }
    }

    static class EmergencyIntakeFacade {
        private final VitalsMonitor vitalsMonitor = new VitalsMonitor();
        private final LabOrderDispatcher labDispatcher = new LabOrderDispatcher();
        private final WardCapacityTracker capacityTracker = new WardCapacityTracker();
        private final AdmissionNotifier admissionNotifier = new AdmissionNotifier();
        private TriagePriorityStrategy triageStrategy;
        private BedMatchingStrategy bedStrategy;

        public EmergencyIntakeFacade(TriagePriorityStrategy triageStrategy, BedMatchingStrategy bedStrategy) {
            this.triageStrategy = triageStrategy;
            this.bedStrategy = bedStrategy;
        }

        public void setStrategies(TriagePriorityStrategy triageStrategy, BedMatchingStrategy bedStrategy) {
            this.triageStrategy = triageStrategy;
            this.bedStrategy = bedStrategy;
        }

        public void admitPatient(String patientRecord) {
            String vitals = vitalsMonitor.checkVitals(patientRecord);
            int urgency = triageStrategy.rankUrgency(patientRecord, vitals);
            String wards = capacityTracker.getAvailableWards();
            String bed = bedStrategy.selectBed(urgency, wards);
            capacityTracker.reserveBed(bed, patientRecord);
            labDispatcher.dispatch(patientRecord, urgency);
            admissionNotifier.notifyStaff(patientRecord, bed, urgency);
        }
    }

    public static void main(String[] args) {
        EmergencyIntakeFacade intake = new EmergencyIntakeFacade(new StandardSeverityIndex(), new SpecialistWardMatch());
        intake.admitPatient("PT-901 TRAUMA");
        intake.setStrategies(new PediatricFastTrack(), new ClosestUnitMatch());
        intake.admitPatient("PT-902 PEDIATRIC");
        intake.setStrategies(new MassCasualtyProtocol(), new SpecialistWardMatch());
        intake.admitPatient("PT-903 TRIAGE");
    }
}
