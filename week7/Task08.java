package week7;

interface Pizza {
    String getDetails();
    int getPrice();
}

class Margherita implements Pizza {
    public String getDetails() {
        return "Margherita";
    }

    public int getPrice() {
        return 200;
    }
}

class ExtraCheese implements Pizza {
    private Pizza pizza;

    public ExtraCheese(Pizza pizza) {
        this.pizza = pizza;
    }

    public String getDetails() {
        return pizza.getDetails() + ", Extra Cheese";
    }

    public int getPrice() {
        return pizza.getPrice() + 40;
    }
}

class Olives implements Pizza {
    private Pizza pizza;

    public Olives(Pizza pizza) {
        this.pizza = pizza;
    }

    public String getDetails() {
        return pizza.getDetails() + ", Olives";
    }

    public int getPrice() {
        return pizza.getPrice() + 30;
    }
}

class Jalapenos implements Pizza {
    private Pizza pizza;

    public Jalapenos(Pizza pizza) {
        this.pizza = pizza;
    }

    public String getDetails() {
        return pizza.getDetails() + ", Jalapenos";
    }

    public int getPrice() {
        return pizza.getPrice() + 25;
    }
}

