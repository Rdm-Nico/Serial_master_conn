package com.example.serial_master_conn;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainFrame {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}