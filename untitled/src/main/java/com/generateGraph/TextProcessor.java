package com.generateGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextProcessor {

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

        StringBuilder processedText = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Process each line
                processedText.append(processLine(line)).append(" ");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        // Print the processed text
        System.out.println("Processed text:");
        System.out.println(processedText.toString().trim());

        // Write processed text to a new file
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the path and name of the output text file:");
            String outputFile = scanner.nextLine();


            FileWriter writer = new FileWriter(outputFile);
            writer.write(processedText.toString().trim());
            writer.close();

            System.out.println("Processed text has been written to the file: " + outputFile);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    private static String processLine(String line) {
        StringBuilder result = new StringBuilder();
        for (char ch : line.toCharArray()) {
            if (Character.isLetter(ch)) {
                result.append(ch);
            } else {
                result.append(' ');
            }
        }
        return result.toString().replaceAll("\\s+", " ");
    }
}
