package org.example;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Question11 {

    public static void main(String[] args) {
        // Prompt the user to enter the file name
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file name: ");
        String fileName = scanner.nextLine();

        // Data structure to store connections between cities
        Map<String, TreeSet<DistanceTo>> connections = new HashMap<>();
        String startingCity = null; // The first city encountered in the file will be used as the starting city

        // Read city connections from the file
        try (Scanner fileScanner = new Scanner(new FileReader(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue; // Skip empty lines

                // Parse the line to extract city1, city2, and the distance between them
                String[] parts = line.split(" ");
                String city1 = parts[0];
                String city2 = parts[1];
                int distance = Integer.parseInt(parts[2]);

                // Set the starting city if it hasn't been initialized
                if (startingCity == null) startingCity = city1;

                // Update the connections map to record the bidirectional connection
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

        // Start from the starting city with a distance of 0
        priorityQueue.add(new DistanceTo(startingCity, 0));

        // Dijkstra's Algorithm: Process cities until all reachable cities have been visited
        while (!priorityQueue.isEmpty()) {
            DistanceTo current = priorityQueue.poll();

            // Skip if the city is already finalized
            if (finalized.contains(current.getTarget())) {
                continue;
            }

            // Mark the city as finalized and record its shortest distance
            finalized.add(current.getTarget());
            shortestKnownDistance.put(current.getTarget(), current.getDistance());

            // Add neighboring cities to the priority queue with updated distances
            for (DistanceTo neighbor : connections.getOrDefault(current.getTarget(), new TreeSet<>())) {
                if (!finalized.contains(neighbor.getTarget())) {
                    int updatedDistance = current.getDistance() + neighbor.getDistance();
                    priorityQueue.add(new DistanceTo(neighbor.getTarget(), updatedDistance));

                    // Record the predecessor for the neighbor
                    if (!predecessor.containsKey(neighbor.getTarget())) {
                        predecessor.put(neighbor.getTarget(), current.getTarget());
                    }
                }
            }
        }

        // Output the shortest distances and routes for each city
        System.out.println("Shortest distances and routes from " + startingCity + ":");
        for (String city : finalized) {
            if (!city.equals(startingCity)) {
                // Reconstruct the route for the city
                List<String> route = new ArrayList<>();
                String currentCity = city;
                while (currentCity != null) {
                    route.add(currentCity);
                    currentCity = predecessor.get(currentCity);
                }
                Collections.reverse(route); // Reverse the route to start from the starting city

                // Print the shortest distance and route
                System.out.println(city + " " + shortestKnownDistance.get(city) + " (Route: " + String.join(" -> ", route) + ")");
            }
        }
    }

    // Helper class to represent a city and the distance to it
    static class DistanceTo implements Comparable<DistanceTo> {
        private final String target; // The target city
        private final int distance; // The distance to the target city

        public DistanceTo(String target, int distance) {
            this.target = target;
            this.distance = distance;
        }

        public String getTarget() {
            return target;
        }

        public int getDistance() {
            return distance;
        }

        // Compare distances to prioritize shorter distances in the priority queue
        @Override
        public int compareTo(DistanceTo other) {
            int distanceComparison = Integer.compare(this.distance, other.distance);
            if (distanceComparison != 0) {
                return distanceComparison; // Compare by distance first
            }
            return this.target.compareTo(other.target); // Compare by city name if distances are equal
        }
    }
}
