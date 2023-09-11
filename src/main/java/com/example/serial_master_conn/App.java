package com.example.serial_master_conn;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainFrame.fxml"));

        String mainframefxml = "file:/C:/Users/nicor/IdeaProjects/Serial_master_conn/src/main/resources/com/example/serial_master_conn/MainFrame.fxml";
        URL url = new URL(mainframefxml);
        fxmlLoader.setLocation(url);

        String css_style = String.valueOf(App.class.getResource("MainFrameStyle.css"));


        String css_file = "file:/C:/Users/nicor/IdeaProjects/Serial_master_conn/src/main/resources/com/example/serial_master_conn/MainFrameStyle.css";

        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(css_file);
        scene.setFill(Paint.valueOf("#676767"));
        stage.setTitle("Losma Communication App");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}