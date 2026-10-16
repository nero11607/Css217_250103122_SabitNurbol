package week7;

interface Task01{
    String getDescription();
    double getCost();
}
class Espresso implements Task01 {
    public String getDescription() {
        return "Espresso";
    }

    public double getCost() {
        return 100.0;
    }
}
class MilkDecorator implements Task01{
    private Task01 beverage;
    public MilkDecorator(Task01 beverage){
        this.beverage = beverage;
    }
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }

    public double getCost() {
        return beverage.getCost() + 20.0;
    }
}   
