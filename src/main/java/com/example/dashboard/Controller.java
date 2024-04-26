package com.example.dashboard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The Controller class controls the main dashboard view and handles user actions.
 */
public class Controller {

    private final String csvFile = "./data.csv"; // Update with your CSV file path

    /**
     * Button to switch to scene1.
     */
    public Button switchButton;

    /**
     * Button to switch to scene2.
     */
    public Button switchButton2;

    /**
     * Button to switch to scene3.
     */
    public Button switchButton3;

    /**
     * Button to switch to scene4.
     */
    public Button switchButton4;

    @FXML
    private VBox chartContainer;

    @FXML
    private Label sceneLabel;

    // Flag to keep track of whether Scene4 stage is open
    private boolean scene4Open = false;

    @FXML
    private Label dataStatusLabel; // New label for displaying data status

    @FXML
    private Label dataStatusLabel2;

    /**
     * Method to set the stage reference.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    public void setStage(Stage primaryStage) {
    }

    /**
     * Initializes the controller.
     */
    public void initialize() {
        // Check if CSV file has data and update label visibility
        boolean hasData = hasDataInCSV();
        if (hasData) {
            dataStatusLabel.setVisible(false);
            dataStatusLabel2.setVisible(true);
            System.out.println("CSV file has data.");
        } else {
            dataStatusLabel.setVisible(true);
            dataStatusLabel2.setVisible(false);

            System.out.println("CSV file is empty or does not exist.");
        }
    }

    /**
     * Event handler for switching to scene1.
     */
    public void switchToScene1() {
        // Views-sales chart for scene1
        LineChart<String, Number> lineChart = CSVChart.createLineChart(csvFile, 1, 2);

        // Clear existing charts and add the line chart to the chart container
        chartContainer.getChildren().clear();
        chartContainer.getChildren().addAll(lineChart);
        sceneLabel.setText("Customer Behaviour");
    }

    /**
     * Event handler for switching to scene2.
     */
    public void switchToScene2() {
        // Views-products chart for scene2
        StackedBarChart<String, Number> stackedBarChart = CSVChart.createStackedBarChart(csvFile, 0, 2); // Assuming the third column is sales

        // Clear existing charts and add the stacked bar chart to the chart container
        chartContainer.getChildren().clear();
        chartContainer.getChildren().addAll(stackedBarChart);

        // Find the most viewed product
        String mostViewedProduct = CSVChart.findMostViewedProduct(stackedBarChart);

        // Create a label to display the most viewed product
        Label mostViewedLabel = new Label(mostViewedProduct + " is the most trending.");
        mostViewedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        // Add the label to the chart container
        chartContainer.getChildren().add(mostViewedLabel);
        sceneLabel.setText("Trends");
    }

    /**
     * Event handler for switching to scene3.
     */
    public void switchToScene3() {
        // Views-products chart for scene3
        StackedBarChart<String, Number> stackedBarChart = CSVChart.createStackedBarChart(csvFile, 0, 1); // Assuming the third column is views

        // Clear existing charts and add the stacked bar chart to the chart container
        chartContainer.getChildren().clear();
        chartContainer.getChildren().addAll(stackedBarChart);

        // Create a label to display the most popular product based on sales
        String mostPopularProduct = CSVChart.findMostPopularProduct(stackedBarChart);
        Label mostPopularLabel = new Label(mostPopularProduct + " is the most popular based on sales.");
        mostPopularLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Add the label to the chart container
        chartContainer.getChildren().add(mostPopularLabel);
        sceneLabel.setText("Product Popularity");
    }

    /**
     * Event handler for switching to scene4.
     *
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public void switchToScene4() throws IOException {
        if (!scene4Open) {
            // Load the FXML file for the Data Entry scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene4.fxml"));
            Parent root = loader.load();

            // Create a new stage for the Data Entry scene
            Stage scene4Stage = new Stage();

            // Set the title of the stage
            scene4Stage.setTitle("Data Entry");

            // Set the scene for the stage
            scene4Stage.setScene(new Scene(root, 1039, 533));

            scene4Stage.setResizable(false); // Lock resizing

            // Center the stage on the screen
            scene4Stage.centerOnScreen();

            // Show the stage
            scene4Stage.show();

            // Set scene4Open flag to true
            scene4Open = true;

            // Add listener to handle stage close event
            scene4Stage.setOnCloseRequest(_ -> scene4Open = false);
        }
    }

    public boolean hasDataInCSV() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            String line = reader.readLine(); // Read the first line
            if (line != null) {
                // Skip the first line and check for data in subsequent lines
                line = reader.readLine();
                reader.close();
                return line != null; // If line is not null, file has data
            } else {
                reader.close();
                return false; // File is empty
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error occurred, file might not exist or inaccessible
        }
    }
}
