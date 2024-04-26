package com.example.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * The DataEntryController class controls the data entry view and handles user actions.
 */
public class DataEntryController  {

    public Button searchButton;
    public Button addButton;
    public Button deleteButton;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField viewsField;

    @FXML
    private TextField salesField;

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> searchResults; // Assuming you want to display search results in a ListView

    @FXML
    private TextField deleteProductNameField;

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        // Initialize the controller
        // You can add any initialization logic here
    }

    /**
     * Handles the action when the "Add" button is clicked.
     */
    @FXML
    private void handleAddButtonAction() {
        // Handle the action when the "Add" button is clicked
        String productName = productNameField.getText();
        int views = Integer.parseInt(viewsField.getText());
        int sales = Integer.parseInt(salesField.getText());

        // Write the data to the CSV file
        CSVHandler.writeToCSV(productName, views, sales);

        // Clear the input fields after adding the data
        productNameField.clear();
        viewsField.clear();
        salesField.clear();
    }

    /**
     * Handles the search action.
     */
    @FXML
    private void handleSearch() {
        // Handle the search action
        String searchTerm = searchField.getText();

        // Perform the search using CSVHandler
        List<String> searchResultsList = CSVHandler.searchInCSV(searchTerm);

        // Clear the previous search results
        searchResults.getItems().clear();

        // Check if searchResultsList is empty
        if (searchResultsList.isEmpty()) {
            // If empty, add "Not found" message to the ListView
            searchResults.getItems().add("Not found");
        } else {
            // Otherwise, add all search results to the ListView
            searchResults.getItems().addAll(searchResultsList);
        }
    }

    /**
     * Handles the action when the "Delete" button is clicked.
     */
    @FXML
    private void handleDeleteButtonAction() {
        // Get the product name to delete
        String productNameToDelete = deleteProductNameField.getText();

        // Call the deleteFromCSV method to delete the product
        boolean deletionSuccessful = CSVHandler.deleteFromCSV(productNameToDelete);

        // Show a message based on the deletion result
        if (deletionSuccessful) {
            // If deletion was successful, show a success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Product deleted successfully.");
        } else {
            // If deletion was not successful, show an error message
            showAlert(Alert.AlertType.ERROR, "Error", "Product not found or deletion failed.");
        }

        // Clear the input field after attempting deletion
        deleteProductNameField.clear();
    }

    /**
     * Shows an alert dialog with the specified type, title, and message.
     *
     * @param alertType The type of alert.
     * @param title     The title of the alert.
     * @param message   The message content of the alert.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
