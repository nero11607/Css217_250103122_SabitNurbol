package week7;

interface FareStrategy {
    double computeFare(double km);
}

class NormalFare implements FareStrategy {
    public double computeFare(double km) {
        return 50.0 + km * 12.0;
    }
}

class RainSurgeFare implements FareStrategy {
    public double computeFare(double km) {
        return (50.0 + km * 12.0) * 1.5;
    }
}

class MidnightFare implements FareStrategy {
    public double computeFare(double km) {
        return 80.0 + km * 15.0;
    }
}

