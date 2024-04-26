package com.example.dashboard;

import javafx.scene.chart.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The CSVChart class provides methods to create line charts and stacked bar charts from CSV data.
 */
public class CSVChart {

    /**
     * Creates a line chart from a CSV file.
     *
     * @param csvFile       The path to the CSV file.
     * @param xAxisColumn   The index of the column containing data for the X-axis.
     * @param yAxisColumn   The index of the column containing data for the Y-axis.
     * @return A LineChart object representing the line chart created from the CSV data.
     */
    public static LineChart<String, Number> createLineChart(String csvFile, int xAxisColumn, int yAxisColumn) {
        // Create axes for the chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        // Create a line chart with the axes
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        // Create a series to hold the data points
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        String line;
        String cvsSplitBy = ",";
        boolean firstLineSkipped = false; // Flag to track whether the first line has been skipped

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read each line from the CSV file
            while ((line = br.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue; // Skip the first line
                }
                // Split the line into data elements
                String[] data = line.split(cvsSplitBy);
                // Add a data point to the series
                series.getData().add(new XYChart.Data<>(data[xAxisColumn], Double.parseDouble(data[yAxisColumn])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add the series to the line chart
        lineChart.getData().add(series);
        return lineChart;
    }

    /**
     * Creates a stacked bar chart from a CSV file.
     *
     * @param csvFile          The path to the CSV file.
     * @param xAxisColumnIndex The index of the column containing data for the X-axis.
     * @param yAxisColumnIndex The index of the column containing data for the Y-axis.
     * @return A StackedBarChart object representing the stacked bar chart created from the CSV data.
     */
    public static StackedBarChart<String, Number> createStackedBarChart(String csvFile, int xAxisColumnIndex, int yAxisColumnIndex) {
        // Create axes for the chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        // Create a stacked bar chart with the axes
        StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        // Create a series to hold the data points
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        String line;
        String cvsSplitBy = ",";
        boolean firstLineSkipped = false; // Flag to track whether the first line has been skipped

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read each line from the CSV file
            while ((line = br.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue; // Skip the first line
                }
                // Split the line into data elements
                String[] data = line.split(cvsSplitBy);
                // Add a data point to the series
                series.getData().add(new XYChart.Data<>(data[0], Double.parseDouble(data[yAxisColumnIndex])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add the series to the stacked bar chart
        stackedBarChart.getData().add(series);
        return stackedBarChart;
    }


    /**
     * Finds the most viewed product in a stacked bar chart.
     *
     * @param chart The stacked bar chart containing the data.
     * @return The name of the most viewed product.
     */
    static String findMostViewedProduct(StackedBarChart<String, Number> chart) {
        // Get the data from the chart
        XYChart.Series<String, Number> series = chart.getData().getFirst(); // Assuming there's only one series

        // Variables to hold the maximum views and the corresponding product
        int maxViews = Integer.MIN_VALUE;
        String mostViewedProduct = "";

        // Iterate through the data to find the product with the most views
        for (XYChart.Data<String, Number> data : series.getData()) {
            // Get the value (views) for each data point
            int views = data.getYValue().intValue();

            // Check if the current views are greater than the maximum views found so far
            if (views > maxViews) {
                // Update the maximum views and the corresponding product
                maxViews = views;
                mostViewedProduct = data.getXValue();
            }
        }

        // Return the product with the most views
        return mostViewedProduct;
    }

    /**
     * Finds the most popular product based on sales in a stacked bar chart.
     *
     * @param chart The stacked bar chart containing the data.
     * @return The name of the most popular product based on sales.
     */
    public static String findMostPopularProduct(StackedBarChart<String, Number> chart) {
        // Initialize variables to keep track of the most popular product and its sales count
        String mostPopularProduct = "";
        double maxSales = Double.MIN_VALUE;

        // Get the series from the chart
        XYChart.Series<String, Number> series = chart.getData().getFirst();

        // Iterate through the data to find the product with the maximum sales
        for (XYChart.Data<String, Number> data : series.getData()) {
            String product = data.getXValue();
            double sales = data.getYValue().doubleValue();

            // Update the most popular product if the current product has more sales
            if (sales > maxSales) {
                mostPopularProduct = product;
                maxSales = sales;
            }
        }

        return mostPopularProduct;
    }
}
