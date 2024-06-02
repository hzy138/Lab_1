package com.generateGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BridgeWordFinder {
    private DirectedGraph graph;

    public BridgeWordFinder(DirectedGraph graph) {
        this.graph = graph;
    }

    public String findBridgeWords(String word1, String word2) {
        // Convert words to lowercase
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();

        // Check if word1 or word2 is not in the graph
        if (!graph.containsNode(word1) || !graph.containsNode(word2)) {
            return "No word1 or word2 in the graph!";
        }

        Set<String> bridgeWords = new HashSet<>();

        // Find bridge words from word1 to word2
        Map<String, Integer> neighbors1 = graph.getNeighbors(word1);
        for (String bridgeWord : neighbors1.keySet()) {
            if (graph.containsNode(bridgeWord) && graph.hasEdge(bridgeWord, word2)) {
                bridgeWords.add(bridgeWord);
            }
        }

        // Find bridge words from word2 to word1
        Map<String, Integer> neighbors2 = graph.getNeighbors(word2);
        for (String bridgeWord : neighbors2.keySet()) {
            if (graph.containsNode(bridgeWord) && graph.hasEdge(bridgeWord, word1)) {
                bridgeWords.add(bridgeWord);
            }
        }

        if (bridgeWords.isEmpty()) {
            return "No bridge words from word1 to word2!";
        } else {
            return "The bridge words from " + word1 + " to " + word2 + " are: " + String.join(", ", bridgeWords) + ".";
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

        DirectedGraph directedGraph = new DirectedGraph();
        directedGraph.buildGraph(processedText);

        BridgeWordFinder bridgeWordFinder = new BridgeWordFinder(directedGraph);

        //String word1 = "apple";
        //String word2 = "banana";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the word1:");
        String word1=scanner.nextLine();
        System.out.println("Please input the word2:");
        String word2=scanner.nextLine();
        String bridgeWords = bridgeWordFinder.findBridgeWords(word1, word2);
        System.out.println(bridgeWords);
    }
}

