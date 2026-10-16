package week7;

interface CoolingStrategy {
    double getTargetTemp(int occupants);
}

class EcoMode implements CoolingStrategy {
    public double getTargetTemp(int occupants) {
        return 26.0;
    }
}

class ComfortMode implements CoolingStrategy {
    public double getTargetTemp(int occupants) {
        return occupants >= 1 ? 22.0 : 28.0;
    }
}

class PartyMode implements CoolingStrategy {
    public double getTargetTemp(int occupants) {
        return Math.max(18.0, 22.0 - (occupants * 0.5));
    }
}
