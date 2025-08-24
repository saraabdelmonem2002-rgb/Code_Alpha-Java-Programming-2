package stocktrading;

import java.time.LocalDateTime;
public class Transaction {
    private Stock stock;
    private int quantity;
    private String type;
    private LocalDateTime dateTime;

    public Transaction(Stock stock, int quantity, String type) {
        this.stock = stock;
        this.quantity = quantity;
        this.type = type;
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return dateTime + " - " + type + " " + quantity + " of " + stock.getSymbol() + " @ $" + String.format("%.2f", stock.getPrice());
    }
}

