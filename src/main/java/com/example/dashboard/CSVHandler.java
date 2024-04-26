package com.example.dashboard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The CSVHandler class provides methods to create, read from, write to, search in, and delete from a CSV file.
 */
public class CSVHandler {

    /**
     * The path of the CSV file.
     */
    private static final String CSV_FILE_PATH = "./data.csv";

    /**
     * Method to create a new CSV file with headers if it does not exist.
     */

    public static void createCSV() {
        File file = new File(CSV_FILE_PATH);
        boolean fileExists = file.exists();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true)); // append mode
            if (!fileExists) {
                writer.write("Product, Views, Sales\n");
            }
            writer.close();
            if (!fileExists) {
                System.out.println("CSV file created successfully.");
            } else {
                System.out.println("CSV file already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method to write data to the CSV file.
     *
     * @param product The name of the product.
     * @param views   The number of views.
     * @param sales   The number of sales.
     */
    public static void writeToCSV(String product, int views, int sales) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true));
            writer.write(product + "," + views + "," + sales + "\n");
            writer.close();
            System.out.println("Data added to CSV file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to read data from the CSV file.
     *
     * @return A list of strings representing each line of the CSV file.
     */
    public static List<String> readFromCSV() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to search for a specific value in the CSV file.
     *
     * @param searchTerm The term to search for in the CSV file.
     * @return A list of strings representing lines that contain the search term.
     */
    public static List<String> searchInCSV(String searchTerm) {
        List<String> searchResults = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                for (String token : tokens) {
                    if (token.trim().equalsIgnoreCase(searchTerm)) {
                        searchResults.add(line);
                        break;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    /**
     * Method to delete a product entry from the CSV file.
     *
     * @param productName The name of the product to delete.
     * @return true if the product was found and deleted, false otherwise.
     */
    public static boolean deleteFromCSV(String productName) {
        List<String> linesToRemove = new ArrayList<>();
        boolean found = false;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length > 0 && tokens[0].trim().equalsIgnoreCase(productName)) {
                    linesToRemove.add(line);
                    found = true;
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            try {
                BufferedWriter writer = getBufferedWriter(linesToRemove);
                writer.close();
                System.out.println("Product '" + productName + "' deleted successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Product '" + productName + "' not found.");
        }
        return found;
    }

    /**
     * Helper method to get a BufferedWriter instance.
     *
     * @param linesToRemove The lines to be removed from the CSV file.
     * @return A BufferedWriter instance.
     * @throws IOException If an I/O error occurs.
     */
    private static BufferedWriter getBufferedWriter(List<String> linesToRemove) throws IOException {
        List<String> remainingLines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH));

        String line;
        while ((line = reader.readLine()) != null) {
            if (!linesToRemove.contains(line)) {
                remainingLines.add(line);
            }
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH));
        for (String remainingLine : remainingLines) {
            writer.write(remainingLine + "\n");
        }
        return writer;
    }

}