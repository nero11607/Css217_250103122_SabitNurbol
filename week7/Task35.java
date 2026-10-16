package week7;

interface Dessert {
    String describe();
    int getCalories();
}

class VanillaScoop implements Dessert {
    public String describe() {
        return "Vanilla Scoop";
    }

    public int getCalories() {
        return 150;
    }
}

class HotFudgeDecorator implements Dessert {
    private Dessert dessert;

    public HotFudgeDecorator(Dessert dessert) {
        this.dessert = dessert;
    }

    public String describe() {
        return dessert.describe() + ", Hot Fudge";
    }

    public int getCalories() {
        return dessert.getCalories() + 120;
    }
}

class SprinklesDecorator implements Dessert {
    private Dessert dessert;

    public SprinklesDecorator(Dessert dessert) {
        this.dessert = dessert;
    }

    public String describe() {
        return dessert.describe() + ", Sprinkles";
    }

    public int getCalories() {
        return dessert.getCalories() + 45;
    }
}

class WaffleConeDecorator implements Dessert {
    private Dessert dessert;

    public WaffleConeDecorator(Dessert dessert) {
        this.dessert = dessert;
    }

    public String describe() {
        return dessert.describe() + ", Waffle Cone";
    }

    public int getCalories() {
        return dessert.getCalories() + 110;
    }
}