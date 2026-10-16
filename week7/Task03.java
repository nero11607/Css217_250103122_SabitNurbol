package week7;

interface Task03 {
    double getTemperatureC();
}
class FahrenheitSensor {
    public double readFahrenheit(){
        return 98.6;
    }
}
class SensorAdapter implements Task03{
    private FahrenheitSensor sensor;
    public SensorAdapter(FahrenheitSensor sensor){
        this.sensor = sensor;
    }
    public double getTemperatureC(){
        return (sensor.readFahrenheit() - 32.0) * 5.0 / 9.0;
    }
}
