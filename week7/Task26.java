package week7;

interface Car {
    String getConfiguration();
    double getPrice();
}

class BaseSedan implements Car {
    public String getConfiguration() {
        return "Standard Sedan";
    }

    public double getPrice() {
        return 15000.0;
    }
}

class SunroofDecorator implements Car {
    private Car car;

    public SunroofDecorator(Car car) {
        this.car = car;
    }

    public String getConfiguration() {
        return car.getConfiguration() + ", Sunroof";
    }

    public double getPrice() {
        return car.getPrice() + 1200.0;
    }
}

class AlloyWheelsDecorator implements Car {
    private Car car;

    public AlloyWheelsDecorator(Car car) {
        this.car = car;
    }

    public String getConfiguration() {
        return car.getConfiguration() + ", Alloy Wheels";
    }

    public double getPrice() {
        return car.getPrice() + 800.0;
    }
}

class DashcamDecorator implements Car {
    private Car car;

    public DashcamDecorator(Car car) {
        this.car = car;
    }

    public String getConfiguration() {
        return car.getConfiguration() + ", Dashcam";
    }

    public double getPrice() {
        return car.getPrice() + 300.0;
    }
}