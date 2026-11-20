public class SmartHomeFacade {
    private final SmartThermostat thermostat;
    private final AutomatedBlinds blinds;
    private final CoffeeMaker coffeeMaker;
    private final SecurityAlarm alarm;

    public SmartHomeFacade() {
        this.thermostat = new SmartThermostat();
        this.blinds = new AutomatedBlinds();
        this.coffeeMaker = new CoffeeMaker();
        this.alarm = new SecurityAlarm();
    }

    public void activateMorningMode() {
        System.out.println("Activating morning mode...");
        alarm.disarm();
        blinds.open();
        thermostat.setTemperature(22);
        coffeeMaker.brew();
    }

    public void activateNightMode() {
        System.out.println("Activating night mode...");
        coffeeMaker.standby();
        blinds.close();
        thermostat.setTemperature(18);
        alarm.arm();
    }
}
