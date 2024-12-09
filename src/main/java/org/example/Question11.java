package org.example;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *  Name: Cheryl Kong
 *  Class Group: SD2B
 */
public class Question11 {

    public static void main(String[] args) {
        // prompt the user to enter the file name
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file name: ");
        String fileName = scanner.nextLine();

        // store connections between cities
        Map<String, TreeSet<DistanceTo>> connections = new HashMap<>();
        String startingCity = null; // first city in the file will be used as the starting city

        // read city connections from the file
        try (Scanner fileScanner = new Scanner(new FileReader(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue; // skip empty lines

                // extract city1, city2, and the distance between them
                String[] parts = line.split(" ");
                String city1 = parts[0];
                String city2 = parts[1];
                int distance = Integer.parseInt(parts[2]);

                // set the starting city if it hasn't been initialized
                if (startingCity == null) startingCity = city1;

                // update and record the connection
                connections.putIfAbsent(city1, new TreeSet<>());
                connections.putIfAbsent(city2, new TreeSet<>());
                connections.get(city1).add(new DistanceTo(city2, distance));
                connections.get(city2).add(new DistanceTo(city1, distance));
            }
        } catch (IOException e) {
            System.out.println("Error: file not found/ unreadable");
            return;
        }

        // Map to store the shortest known distance to each city from the starting city
        Map<String, Integer> shortestKnownDistance = new HashMap<>();
        // PriorityQueue to process cities in the order of their distances
        PriorityQueue<DistanceTo> priorityQueue = new PriorityQueue<>();
        // Set to track cities whose shortest distances have been finalized
        Set<String> finalized = new HashSet<>();
        // Map to store the predecessor of each city in the shortest path
        Map<String, String> predecessor = new HashMap<>();

        // start from the starting city with a distance of 0
        priorityQueue.add(new DistanceTo(startingCity, 0));

        // Dijkstra's Algorithm: process cities until all reachable cities have been visited
        while (!priorityQueue.isEmpty()) {
            DistanceTo current = priorityQueue.poll();

            // skip if the city is already finalized (shortest route found)
            if (finalized.contains(current.getTarget())) {
                continue;
            }

            // mark the city as finalized and record its shortest distance
            finalized.add(current.getTarget());
            shortestKnownDistance.put(current.getTarget(), current.getDistance());

            // add neighboring cities to the priority queue with updated distances
            for (DistanceTo neighbor : connections.getOrDefault(current.getTarget(), new TreeSet<>())) {
                if (!finalized.contains(neighbor.getTarget())) {
                    int updatedDistance = current.getDistance() + neighbor.getDistance();
                    priorityQueue.add(new DistanceTo(neighbor.getTarget(), updatedDistance));

                    // record the predecessor for the neighbor
                    if (!predecessor.containsKey(neighbor.getTarget())) {
                        predecessor.put(neighbor.getTarget(), current.getTarget());
                    }
                }
            }
        }

        System.out.println("Shortest distances and routes from " + startingCity + ":");
        for (String city : finalized) {
            if (!city.equals(startingCity)) {
                // reconstruct the route for the city
                List<String> route = new ArrayList<>();
                String currentCity = city;
                while (currentCity != null) {
                    route.add(currentCity);
                    currentCity = predecessor.get(currentCity);
                }
                Collections.reverse(route); // reverse the route to start from the starting city

                // print the shortest distance and route
                System.out.println(city + " " + shortestKnownDistance.get(city) + " (Route: " + String.join(" -> ", route) + ")");
            }
        }
    }
}
