package week7;

interface MetricSpeedometer {
    double getSpeedKmh();
}
class ImperialSpeedometer{
    double getSpeedMph(){
        return 60.0;
    }
}
class SpeedAdapter implements MetricSpeedometer {
    private ImperialSpeedometer imperial;

    public SpeedAdapter(ImperialSpeedometer imperial) {
        this.imperial = imperial;
    }

    public double getSpeedKmh() {
        return imperial.getSpeedMph() * 1.60934;
    }
}
