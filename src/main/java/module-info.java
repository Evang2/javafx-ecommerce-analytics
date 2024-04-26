/**
 * This module contains classes for the dashboard application.
 * It requires JavaFX and desktop modules.
 */
module com.example.dashboard {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.dashboard to javafx.fxml;
    exports com.example.dashboard;
}