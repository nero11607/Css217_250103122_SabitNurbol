public class TemperatureSensorAdapter implements ICelsiusSensor{
    private final FahrenheitSensor sensor;
    
    public void TemperatureSensorAdapter(FahrenheitSensor sensor){
        this.sensor = sensor;
    }
    @Override 
    public double getTemperatureInCelsius(){
        String raw = sensor.readRawTemperature();
        String numericPart = raw.replace(" F", "").trim();
        double fahrenheit = Double.parseDouble(numericPart);
        double celsius = (fahrenheit - 32) * (5.0 / 9.0);
        return Math.round(celsius * 100.0) / 100.0;
    }
}