package org.example;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Name: Cheryl Kong
 * Class Group: SD2B
 */
public class Question7  { // Shares Tax Calculations (Queue)
    public static void main(String[] args) {
        double total = 0;
        String prompt = "";

        Queue<Share> tracker = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("\nChoose an option: buy, sell, or quit: ");
            prompt = scanner.next();
            if (prompt.equalsIgnoreCase("buy")) {
                try {
                    System.out.print("Enter buy quantity: ");
                    int qty = scanner.nextInt();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    // add the trade to a Share object
                    tracker.add(new Share(qty, price));
                    System.out.println("Bought " + qty + " shares at $" + String.format("%.2f", price) + " per share");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please enter a valid number!");
                    scanner.next();
                }
            } else if (prompt.equalsIgnoreCase("sell")) {
                System.out.print("Enter sell quantity: ");
                int qty = scanner.nextInt();
                int totalBuyQuantity = 0;
                for (Share share : tracker) {
                    totalBuyQuantity += share.getQuantity();
                }
                // if sell quantity exceeds total shares quantity
                if (qty > totalBuyQuantity) {
                    System.out.print("Error: Cannot sell " + qty + " shares. Only " +
                            totalBuyQuantity + " shares owned.");
                    continue;
                }
                System.out.print("Enter price: ");
                double price = scanner.nextDouble();

                while (qty > 0 && !tracker.isEmpty()) {
                    Share firstShare = tracker.poll();
                    // if buy quantity is less than or equal to sell quantity
                    if (firstShare.getQuantity() <= qty) {
                        // calculate gains
                        total += (price - firstShare.getPrice()) * firstShare.getQuantity();
                        // deduct sold quantity from the sell quantity
                        qty -= firstShare.getQuantity();
                    } else {
                        total += (price - firstShare.getPrice()) * qty;
                        firstShare.setQuantity(firstShare.getQuantity() - qty);
                        tracker.add(firstShare);
                        // exit the loop when sell order is completed
                        qty = 0;
                    }
                }
                System.out.println("Total gains: $" + String.format("%.2f", total));

            }
        } while (!prompt.equalsIgnoreCase("quit"));
        scanner.close();
    }
}
