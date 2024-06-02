package com.generateGraph;

import java.io.*;
import java.util.*;

public class DirectedGraph {
    private Map<String, Map<String, Integer>> graph;

    public DirectedGraph() {
        graph = new HashMap<>();
    }
//应用到另外一个类中
    public boolean containsNode(String node) {
        return graph.containsKey(node);
    }

    public boolean hasNeighbors(String node) {
        return graph.containsKey(node) && !graph.get(node).isEmpty();
    }
    public Set<String> getNodes() {
        return graph.keySet();
    }

    public Map<String, Integer> getNeighbors(String node) {
        // Check if the node exists in the graph
        /*if (!this.containsNode(node)) {
            System.out.println("Error");
            throw new NoSuchElementException("The node does not exist in the graph.");

        }*/
        // Return the neighbors of the node from adjacency list
        return this.graph.get(node);
    }
    public boolean hasEdge(String nodeA, String nodeB) {
        // Check if nodeA exists in the graph, if not, throw an error
        if (!this.containsNode(nodeA)) {
            throw new NoSuchElementException("The node does not exist in the graph.");
        }
        // Check if there exists an edge from nodeA to nodeB
        return this.graph.get(nodeA).containsKey(nodeB);
    }
//应用到另外一个类中
    public void addEdge(String from, String to) {
        graph.putIfAbsent(from.toLowerCase(), new HashMap<>());
        Map<String, Integer> neighbors = graph.get(from.toLowerCase());
        neighbors.put(to.toLowerCase(), neighbors.getOrDefault(to.toLowerCase(), 0) + 1);
    }
    public void buildGraphFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                buildGraph(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void buildGraph(String text) {
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length - 1; i++) {
            String from = words[i].toLowerCase();
            String to = words[i + 1].toLowerCase();
            addEdge(from, to);
        }
    }

    public void printGraph() {
        for (String from : graph.keySet()) {
            Map<String, Integer> neighbors = graph.get(from);
            for (String to : neighbors.keySet()) {
                int weight = neighbors.get(to);
                System.out.println(from + " -> " + to + " : " + weight);
            }
        }
    }

    public void saveGraphImage(String outputPath) {
        try {
            PrintWriter writer = new PrintWriter("graph.dot", "UTF-8");
            writer.println("digraph G {");
            for (String from : graph.keySet()) {
                Map<String, Integer> neighbors = graph.get(from);
                for (String to : neighbors.keySet()) {
                    int weight = neighbors.get(to);
                    writer.println(String.format("  \"%s\" -> \"%s\" [label=\"%d\"];", from, to, weight));
                }
            }
            writer.println("}");
            writer.close();

            // Use Graphviz to generate the graph image
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", "graph.dot", "-o", outputPath);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Assume processedText contains the processed text from the previous steps

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
        //
        DirectedGraph directedGraph = new DirectedGraph();
        directedGraph.buildGraph(processedText);
        directedGraph.printGraph();
        directedGraph.saveGraphImage("graph.png");
        System.out.println("Graph image saved as graph.png");
    }
}
