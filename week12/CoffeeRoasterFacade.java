public class CoffeeRoasterFacade {
    private final BeanHopperGate hopperGate;
    private final RoastingDrumMotor drumMotor;
    private final HeatingChamber heatingChamber;
    private final CoolingTraySystem coolingTray;
    private final TelemetryLogger logger;

    public CoffeeRoasterFacade() {
        this.hopperGate = new BeanHopperGate();
        this.drumMotor = new RoastingDrumMotor();
        this.heatingChamber = new HeatingChamber();
        this.coolingTray = new CoolingTraySystem();
        this.logger = new TelemetryLogger();
    }

    public boolean runRoastBatch(String beanOrigin,
                                 int batchGrams,
                                 int drumRpm,
                                 int targetTempCelsius) {
        try {
            logger.resetBatchLog();
            logger.logState("BATCH_START", "Starting roast batch for " + beanOrigin + " (" + batchGrams + "g).");

            hopperGate.loadGreenBeans(batchGrams);

            drumMotor.startRotation(drumRpm);

            hopperGate.openGate();
            hopperGate.closeGate();

            heatingChamber.igniteBurner(drumMotor.isSpinning());
            logger.logState("HEATING", "Gas burner ignited with drum spinning.");

            heatingChamber.heatToTarget(targetTempCelsius);
            logger.logState("ROASTING", "Active roast phase stabilized at " + targetTempCelsius + "°C.");

            heatingChamber.extinguishBurner();

            coolingTray.startCoolingFan();
            coolingTray.openDischargeDoor(heatingChamber.isBurnerIgnited());

            drumMotor.stopRotation();
            coolingTray.finishCoolingCycle();
            logger.logState("BATCH_COMPLETE", "Roast batch completed cleanly for " + beanOrigin + ".");
            return true;
        } catch (RuntimeException ex) {
            emergencyAbort(ex.getMessage());
            return false;
        }
    }

    public boolean roastLightProfile(String beanOrigin, int batchGrams) {
        return runRoastBatch(beanOrigin, batchGrams, 55, 196);
    }

    public boolean roastDarkProfile(String beanOrigin, int batchGrams) {
        return runRoastBatch(beanOrigin, batchGrams, 42, 230);
    }

    public void emergencyAbort(String reason) {
        logger.logState("ABORT", "Emergency shutdown triggered: " + reason);
        heatingChamber.extinguishBurner();
        hopperGate.closeGate();
        drumMotor.stopRotation();
        coolingTray.finishCoolingCycle();
    }
}
