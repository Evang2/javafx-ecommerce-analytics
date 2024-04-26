package com.example.dashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;



/**
 * The Main class serves as the entry point for the JavaFX application.
 */
public class Main extends Application {

    /**
     * Default constructor inherited from the Application class.
     * This constructor is provided by the superclass Application.
     */
    public Main() {
        // Default constructor provided by the Application class
    }

    /**
     * The start method is called by the JavaFX runtime when the application is launched.
     *
     * @param primaryStage The primary stage for the application, onto which the application scene can be set.
     * @throws Exception If an error occurs during the initialization of the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create an instance of FXMLLoader to load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1.fxml"));

        // Load the FXML file and obtain the root element of the scene graph
        Parent root = loader.load();

        // Get the controller associated with the FXML file
        Controller controller = loader.getController();

        // Pass the stage reference to the controller for later use
        controller.setStage(primaryStage);

        // Create a new Scene using the root element
        Scene scene = new Scene(root);

        // Set the scene for the primary stage
        primaryStage.setScene(scene);

        primaryStage.setResizable(true); // Lock resizing

        // Make the primary stage visible
        primaryStage.show();

        // Set a custom controller factory to perform additional initialization
        loader.setControllerFactory(_ -> {
            // Create a new instance of the Controller class
            Controller customController = new Controller();

            // Pass the stage reference to the custom controller
            customController.setStage(primaryStage);

            // Return the custom controller
            return customController;
        });
    }

    /**
     * The entry point of the JavaFX application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Create CSv file if it does not exist
        CSVHandler.createCSV();
        // Launch the JavaFX application
        launch(args);
    }
}