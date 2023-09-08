package com.example.serial_master_conn;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainFrame.fxml"));
        String css_style = String.valueOf(App.class.getResource("MainFrameStyle.css"));

        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(css_style);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}