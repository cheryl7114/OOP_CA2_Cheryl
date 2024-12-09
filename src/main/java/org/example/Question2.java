package org.example;

import java.util.Stack;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Name: Cheryl Kong
 * Class Group: SD2B
 */
public class Question2 // Car Parking - Stack
{
    public static void runSimulation(Scanner sc) {
        int carNum = 0;
        Stack<Integer> driveway = new Stack<>();
        Stack<Integer> street = new Stack<>();
        while (true) {
            try {
                System.out.print("\nEnter car number (+num to add, -num to remove, 0 to stop): ");
                carNum = sc.nextInt();

                // handle add cars to driveway
                if (carNum > 0) {
                    driveway.push(carNum);
                    System.out.println("Car number " + carNum + " has been added to driveway!");
                    System.out.println("Driveway " + driveway);
                    // handle removing cars
                } else if (carNum < 0) {
                    if (!driveway.contains(-carNum)) {
                        System.out.println("Car number " + -carNum + " is not in driveway!");
                    } else {
                        int carToRetrieve = -carNum;
                        // remove all the cars till it finds the car to retrieve
                        while (!driveway.isEmpty() && driveway.peek() != carToRetrieve) {
                            int carStreet = driveway.pop();
                            street.push(carStreet);
                            System.out.println("Car number " + carStreet + " has been moved to street!");
                            System.out.println("Street " + street);
                        }
                        // remove the car from driveway when it reaches the car to retrieve
                        if (!driveway.isEmpty() && driveway.peek() == carToRetrieve) {
                            driveway.pop();
                            System.out.println("Car number " + carToRetrieve + " has been retrieved!");
                        }
                        // add back the cars from street to driveway
                        while (!street.isEmpty()) {
                            int carBackDriveway = street.pop();
                            driveway.push(carBackDriveway);
                            System.out.println("Car number " + carBackDriveway + " has been moved back to driveway!");
                        }
                        System.out.println("Driveway: " + driveway);
                        System.out.println("Street: " + street);
                    }
                } else {
                    System.out.println("Simulation stopped!");
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter an integer!");
                // clear invalid input
                sc.next();
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        runSimulation(sc);
        sc.close();
    }
}
