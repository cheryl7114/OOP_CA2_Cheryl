package org.example;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/**
 * Name: Cheryl Kong
 * Class Group: SD2B
 */
public class Question8 { // Multi-Company Stock Shares Tax Calculation (Queue)
    public static void main(String[] args) {
        double total = 0;
        String prompt = "";

        Map<String, Queue<Share>> companyShares = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("\nChoose an option: buy, sell, or quit: ");
            prompt = scanner.next();

            if (prompt.equalsIgnoreCase("buy")) {
                try {
                    System.out.print("Enter company name (e.g. AAPL): ");
                    String symbol = scanner.next().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();

                    // get or create queue for this company
                    Queue<Share> companyQueue = companyShares.getOrDefault(symbol, new LinkedList<>());
                    companyQueue.add(new Share(qty, price));

                    // add company and the share object
                    companyShares.put(symbol, companyQueue);

                    System.out.println("Company " + symbol + " bought " + qty + " shares at $"
                            + String.format("%.2f", price) + " per share");

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please enter a valid number!");
                    scanner.next();
                }
            } else if (prompt.equals("sell")) {
                try {
                    System.out.print("Enter company name (e.g. AAPL): ");
                    String companyName = scanner.next().toUpperCase();

                    // check if user owns any shares for that company
                    if (!companyShares.containsKey(companyName)) {
                        System.out.println("Error: No shares found for company " + companyName);
                        continue;
                    }

                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();

                    // calculate the total shares of this company owned
                    Queue<Share> companyQueue = companyShares.get(companyName);
                    int totalBuyQuantity = 0;
                    for (Share share : companyQueue) {
                        totalBuyQuantity += share.getQuantity();
                    }

                    // if sell quantity exceeds shares owned
                    if (qty > totalBuyQuantity) {
                        System.out.println("Error: Cannot sell company " + companyName + " for " + qty + " shares. Only " +
                                totalBuyQuantity + " shares available.");
                        continue;
                    }

                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();

                    Queue<Share> updatedQueue = new LinkedList<>();

                    while (qty > 0 && !companyQueue.isEmpty()) {
                        Share firstShare = companyQueue.poll();
                        // if buy quantity less than or equal to sell quantity
                        if (firstShare.getQuantity() <= qty) {
                            // calculate the gain for this block
                            total += (price - firstShare.getPrice()) * firstShare.getQuantity();
                            qty -= firstShare.getQuantity();
                        } else {
                            total += (price - firstShare.getPrice()) * qty;
                            firstShare.setQuantity(firstShare.getQuantity() - qty);
                            updatedQueue.add(firstShare);
                            // exit the loop because sell order is completed
                            qty = 0;
                        }
                    }

                    // Add any remaining shares back to the queue
                    while (!companyQueue.isEmpty()) {
                        updatedQueue.add(companyQueue.poll());
                    }

                    // Update the company's queue
                    if (!updatedQueue.isEmpty()) {
                        companyShares.put(companyName, updatedQueue);
                    } else {
                        companyShares.remove(companyName);
                    }

                    System.out.println("Total gains: $" + total);

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please try again!");
                    scanner.next();
                }
            }
        } while (!prompt.equalsIgnoreCase("quit"));

        scanner.close();
    }
}
