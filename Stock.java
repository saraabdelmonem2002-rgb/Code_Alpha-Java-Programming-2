package stocktrading;

import java.util.Random;
public class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }


    public void updatePriceRandomly() {
        Random rand = new Random();
        double changePercent = (rand.nextDouble() * 10) - 5; // -5% إلى +5%
        price += price * (changePercent / 100);
        if(price < 1) price = 1;
    }

    @Override
    public String toString() {
        return symbol + " : $" + String.format("%.2f", price);
    }
}

