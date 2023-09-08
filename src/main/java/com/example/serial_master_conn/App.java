package com.example.serial_master_conn;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainFrame.fxml"));
        String css_style = String.valueOf(App.class.getResource("MainFrameStyle.css"));

        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(css_style);
        scene.setFill(Paint.valueOf("#676767"));
        stage.setTitle("Losma Communication Center");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {

        launch();
    }
}