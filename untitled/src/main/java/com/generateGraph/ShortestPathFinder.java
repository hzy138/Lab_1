package com.generateGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class ShortestPathFinder {
    private DirectedGraph graph;

    public ShortestPathFinder(DirectedGraph graph) {
        this.graph = graph;
    }

    public String calcShortestPath(String word1, String word2) {
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();

        if (!graph.containsNode(word1) || !graph.containsNode(word2)) {
            return "The two words are not reachable.";
        }

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<String> visited = new HashSet<>();

        // Initialize distances and previous nodes
        for (String node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
            previous.put(node, null);
        }
        distances.put(word1, 0);
        queue.add(word1);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(word2)) {
                break;
            }
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            Map<String, Integer> neighbors = graph.getNeighbors(current);

            if (!graph.hasNeighbors(current)) {
                continue; // Skip to the next iteration
            }
            for (String neighbor : neighbors.keySet()) {
                int newDistance = distances.get(current) + 1; // Assuming edge weight is 1

                if (newDistance < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        // Reconstruct the path
        List<String> path = new ArrayList<>();
        String current = word2;
        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);

        // Highlight the path in the graph
        //graph.highlightPath(path);

        // Calculate and display the path's length
        int pathLength = distances.get(word2);
        return "The shortest path from " + word1 + " to " + word2 + " is: " + String.join(" -> ", path) +
                "\nLength of the path: " + pathLength;
    }

    public static void main(String[] args) {

        String inputFile;
        if (args.length == 1) {
            inputFile = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the path and name of the input text file:");
            inputFile = scanner.nextLine();

        }

        System.out.println("Attempting to read file: " + inputFile);

        StringBuilder proText = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Process each line
                proText.append(line).append(" ");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        String processedText=proText.toString();

        // Assume processedText contains the processed text from the previous steps
        DirectedGraph directedGraph = new DirectedGraph();
        directedGraph.buildGraph(processedText);

        ShortestPathFinder calculator = new ShortestPathFinder(directedGraph);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the first word:");
        String word1 = scanner.nextLine();
        System.out.println("Please enter the second word:");
        String word2 = scanner.nextLine();

        String shortestPath = calculator.calcShortestPath(word1, word2);
        System.out.println(shortestPath);
    }
}
