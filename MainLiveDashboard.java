package stocktrading;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class MainLiveDashboard {
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Stock apple = new Stock("AAPL", 150);
        Stock tesla = new Stock("TSLA", 700);
        Stock amazon = new Stock("AMZN", 3300);
        List<Stock> market = List.of(apple, tesla, amazon);

        User user = new User("Sara", 10000);
        List<Transaction> transactions = new ArrayList<>();

        while(true) {

            market.forEach(Stock::updatePriceRandomly);


            System.out.println(CYAN + "======================================" + RESET);
            System.out.println(CYAN + "          📈 Market Prices 📈          " + RESET);
            System.out.println(CYAN + "======================================" + RESET);
            System.out.printf("%-10s %-10s\n", "Symbol", "Price");
            System.out.println("--------------------------------------");
            market.forEach(s -> System.out.printf("%-10s $%-10.2f\n", s.getSymbol(), s.getPrice()));

            System.out.println("\nChoose an option:");
            System.out.println("1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. Show Portfolio");
            System.out.println("4. Show Transactions");
            System.out.println("5. Exit");

            int choice = 0;
            while(true) {
                System.out.print(YELLOW + "Enter number: " + RESET);
                String input = sc.nextLine();
                try {
                    choice = Integer.parseInt(input);
                    break;
                } catch(NumberFormatException e) {
                    System.out.println(RED + "Please enter a valid number!" + RESET);
                }
            }

            switch(choice) {
                case 1:
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = sc.nextLine();
                    int buyQty = 0;
                    while(true) {
                        System.out.print("Enter quantity: ");
                        String q = sc.nextLine();
                        try {
                            buyQty = Integer.parseInt(q);
                            break;
                        } catch(NumberFormatException e) {
                            System.out.println(RED + "Please enter a valid number!" + RESET);
                        }
                    }
                    Stock buyStock = market.stream().filter(s -> s.getSymbol().equalsIgnoreCase(buySymbol)).findFirst().orElse(null);
                    if(buyStock != null && user.buyStock(buyStock, buyQty)) {
                        transactions.add(new Transaction(buyStock, buyQty, "BUY"));
                        System.out.println(GREEN + "Bought successfully!" + RESET);
                    } else {
                        System.out.println(RED + "Failed to buy stock!" + RESET);
                    }
                    break;

                case 2:
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = sc.nextLine();
                    int sellQty = 0;
                    while(true) {
                        System.out.print("Enter quantity: ");
                        String q = sc.nextLine();
                        try {
                            sellQty = Integer.parseInt(q);
                            break;
                        } catch(NumberFormatException e) {
                            System.out.println(RED + "Please enter a valid number!" + RESET);
                        }
                    }
                    Stock sellStock = market.stream().filter(s -> s.getSymbol().equalsIgnoreCase(sellSymbol)).findFirst().orElse(null);
                    if(sellStock != null && user.sellStock(sellStock, sellQty)) {
                        transactions.add(new Transaction(sellStock, sellQty, "SELL"));
                        System.out.println(GREEN + "Sold successfully!" + RESET);
                    } else {
                        System.out.println(RED + "Failed to sell stock!" + RESET);
                    }
                    break;

                case 3:
                    user.showPortfolioWithProfitLoss();
                    break;

                case 4:
                    System.out.println(CYAN + "======================================" + RESET);
                    System.out.println(CYAN + "         📝 Transactions 📝           " + RESET);
                    System.out.println(CYAN + "======================================" + RESET);
                    if(transactions.isEmpty()) {
                        System.out.println("No transactions yet.");
                    } else {
                        transactions.forEach(System.out::println);
                    }
                    System.out.println(CYAN + "======================================" + RESET + "\n");
                    break;

                case 5:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println(RED + "Invalid option!" + RESET);
            }

            System.out.println("\nPress Enter to continue...");
            sc.nextLine();
        }
    }
}

