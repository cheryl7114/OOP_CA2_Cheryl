package org.example;

import java.util.Scanner;
import java.util.Stack;

/**
 * Name: Cheryl Kong
 * Class Group: SD2B
 */

public class Question4 // Flood Fill (Stack, 2D Array)
{
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        int[][] arr = floodFillStart();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter starting row (0-9): ");
        int startRow = scanner.nextInt();
        System.out.print("Enter starting column (0-9): ");
        int startCol = scanner.nextInt();
        fill(arr, startRow, startCol);
        display(arr);

        scanner.close();
    }

    //  create the 2D array and populate it with zeros
    public static int[][] floodFillStart() {
        int[][] arr = new int[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                arr[x][y] = 0;
            }
        }
        return arr;
    }

    private static void fill(int[][] arr, int startRow, int startCol) {
        Stack<Cell> stack = new Stack<>();
        // pushes the row and column input (e.g. 0 and 0)
        stack.push(new Cell(startRow, startCol));
        int fillNumber = 1;

        while (!stack.isEmpty()) {
            // pop the coordinates 0 and 0
            Cell cell = stack.pop();
            int row = cell.row;
            int col = cell.column;

            if (row >= 0 && row < 10 && col >= 0 && col < 10 && arr[row][col] == 0) {
                // row 0 and col 0 should be 1
                arr[row][col] = fillNumber++;
                // north, south, east, west
                stack.push(new Cell(row - 1, col));
                stack.push(new Cell(row + 1, col));
                stack.push(new Cell(row, col - 1));
                stack.push(new Cell(row, col + 1));
            }
        }
    }

    // display image
    public static void display(int[][] arr) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                System.out.printf("%4d", arr[x][y]);
            }
            System.out.println();
        }
    }

}
