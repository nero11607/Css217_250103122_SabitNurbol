package week7;

interface DiscountStrategy {
    double applyDiscount (double price);
} 
class FlatDiscount implements DiscountStrategy{
    public double applyDiscount(double price){
        return Math.max(0.0, price - 50);
    }
}
class PercentageDiscount implements DiscountStrategy{
    public double applyDiscount(double price){
        return price*0.85;
    }
}
class SeosonalDiscount implements DiscountStrategy{
    public double applyDiscount(double price){
        if(price>500) return price*0.75;
        else return price*0.95;
    }
}
class ShoppingCart {
    private DiscountStrategy strategy;

    public ShoppingCart(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public double checkout(double total) {
        return strategy.applyDiscount(total);
    }
}
