package week7;

interface CartesianPoint {
    double getX();
    double getY();
}

class PolarPoint {
    private double radius;
    private double angleRadians;

    public PolarPoint(double radius, double angleRadians) {
        this.radius = radius;
        this.angleRadians = angleRadians;
    }

    public double getRadius() {
        return radius;
    }

    public double getAngleRadians() {
        return angleRadians;
    }
}

class PolarToCartesianAdapter implements CartesianPoint {
    private PolarPoint polar;

    public PolarToCartesianAdapter(PolarPoint polar) {
        this.polar = polar;
    }

    public double getX() {
        return polar.getRadius() * Math.cos(polar.getAngleRadians());
    }

    public double getY() {
        return polar.getRadius() * Math.sin(polar.getAngleRadians());
    }
}
