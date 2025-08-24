package stocktrading;

import java.util.HashMap;
import java.util.Map;
public class User {
    private String name;
    private double balance;
    private Map<Stock, Integer> portfolio;
    private Map<Stock, Double> purchasePrice;

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new HashMap<>();
        this.purchasePrice = new HashMap<>();
    }

    public String getName() { return name; }
    public double getBalance() { return balance; }
    public Map<Stock, Integer> getPortfolio() { return portfolio; }

    public boolean buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if(cost <= balance) {
            balance -= cost;
            int oldQty = portfolio.getOrDefault(stock, 0);
            portfolio.put(stock, oldQty + quantity);
            double oldPrice = purchasePrice.getOrDefault(stock, 0.0);
            double newAvgPrice = (oldPrice * oldQty + stock.getPrice() * quantity) / (oldQty + quantity);
            purchasePrice.put(stock, newAvgPrice);
            return true;
        }
        return false;
    }

    public boolean sellStock(Stock stock, int quantity) {
        if(portfolio.getOrDefault(stock, 0) >= quantity) {
            portfolio.put(stock, portfolio.get(stock) - quantity);
            balance += stock.getPrice() * quantity;
            if(portfolio.get(stock) == 0) {
                purchasePrice.remove(stock);
            }
            return true;
        }
        return false;
    }

    public double getPortfolioValue() {
        double total = balance;
        for(Stock stock : portfolio.keySet()) {
            int qty = portfolio.get(stock);
            total += stock.getPrice() * qty;
        }
        return total;
    }

    public void showPortfolioWithProfitLoss() {
        System.out.println("======================================");
        System.out.println("          💼 Portfolio 💼             ");
        System.out.println("======================================");
        System.out.printf("%-10s %-10s %-12s %-12s %-10s\n", "Symbol", "Qty", "Buy Price", "Current Price", "P/L %");
        System.out.println("----------------------------------------------");
        for (Stock stock : portfolio.keySet()) {
            int qty = portfolio.get(stock);
            double buyPrice = purchasePrice.get(stock);
            double currPrice = stock.getPrice();
            double plPercent = ((currPrice - buyPrice) / buyPrice) * 100;
            String color = MainLiveDashboard.GREEN;
            if(plPercent < 0) color = MainLiveDashboard.RED;
            System.out.printf("%-10s %-10d $%-11.2f $%-11.2f %s%+6.2f%%%s\n",
                    stock.getSymbol(), qty, buyPrice, currPrice, color, plPercent, MainLiveDashboard.RESET);
        }
        System.out.println("----------------------------------------------");
        System.out.printf("Balance: $%.2f\n", balance);
        System.out.printf("Total Portfolio Value: $%.2f\n", getPortfolioValue());
        System.out.println("======================================\n");
    }
}

