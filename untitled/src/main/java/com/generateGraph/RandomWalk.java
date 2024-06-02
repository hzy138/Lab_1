package com.generateGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RandomWalk {
    private DirectedGraph graph;
    private String current;

    public RandomWalk(DirectedGraph graph) {
        this.graph = graph;
    }

    public String randomWalk() {
        //保存已经访问过的边
        Set<String> visitedEdges = new HashSet<>();
        //保存已经访问过的节点
        List<String> visitedNodes = new ArrayList<>();

        // Randomly select a starting point
        List<String> allNodes = new ArrayList<>(graph.getNodes());
        current = allNodes.get(new Random().nextInt(allNodes.size()));
        visitedNodes.add(current);

        // While current node has outgoing edges
        while (graph.hasNeighbors(current)) {
            Map<String, Integer> neighbors = graph.getNeighbors(current);
            List<String> neighborList = new ArrayList<>(neighbors.keySet());

            // Randomly select a neighboring node
            String next = neighborList.get(new Random().nextInt(neighborList.size()));

            // Check if the edge has been visited
            String edge = current + "->" + next;
            if (visitedEdges.contains(edge)) {
                break;
            }
            visitedEdges.add(edge);
            visitedNodes.add(next);
            current = next;
        }

        String path = String.join(" -> ", visitedNodes);

        try (FileWriter file = new FileWriter("random_walk.txt", false)) {
            file.write(path);
        } catch (IOException e) {
            return "Error writing to file: " + e.getMessage();
        }

        return path;
    }

    public static void main(String[] args) {
        DirectedGraph directedGraph = new DirectedGraph();
        //读入文件
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


        directedGraph.buildGraph(processedText);

        RandomWalk randomWalker = new RandomWalk(directedGraph);
        String path = randomWalker.randomWalk();
        System.out.println(path);
    }
}