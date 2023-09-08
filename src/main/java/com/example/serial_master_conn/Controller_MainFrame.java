package com.example.serial_master_conn;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;
import javafx.util.Duration;

import java.util.*;



public class Controller_MainFrame{



    // for connection:
    private TCP_IP_Connection conn;

    // for threads
    private RefreshScreenService refreshService;
    private ScheduledService scheduledService;

    @FXML
    private Button button_Connect;

    @FXML
    private Button button_disconnect;

    @FXML
    private Label label_Imp;

    @FXML
    private Label label_action_pomp1;

    @FXML
    private Label label_action_pomp2;

    @FXML
    private Label label_ciclo;

    @FXML
    private Label label_emergenza;

    @FXML
    private Label label_freq1;

    @FXML
    private Label label_freq2;

    @FXML
    private Label label_max;

    @FXML
    private Label label_min;


    private ArrayList<Label> labelList = new ArrayList<>();

    @FXML
    void Events_Buttons(ActionEvent event) {
        // check the buttons source
        if(event.getSource() == button_Connect){
            // start connection
             conn = new TCP_IP_Connection();
             System.out.println("Set connection!!!");

             // change the text of the labels
            visualize();


            // use of threads
            refreshService = new RefreshScreenService(this,conn);

            scheduledService =  new ScheduledService() {
                @Override
                protected Task createTask() {
                    return refreshService.createTask();
                }
            };
            scheduledService.setPeriod(Duration.millis(3000));
            scheduledService.start();


        }

        if(event.getSource() == button_disconnect){
            // disconnect the conn and  kills the threads
            if(scheduledService.isRunning())
                scheduledService.cancel();
            conn.disconnect();

            // clear the text of the labels
            for(Label l:labelList){
                l.getStyleClass().clear();
                l.getStyleClass().add("init_label");
                l.getStyleClass().add("label");
                l.setText("");
            }

            labelList.clear();
        }

    }

    private void changeEvents() {
        for (Label l: labelList){

            if(isNumeric(l.getText())){
                // then is a freq:
                int val = Integer.parseInt(l.getText());
                if( val < 0 || val > 250){
                    l.getStyleClass().add("red_label");
                }
            }
            else {
                switch (l.getText()) {
                    case "true": {
                        break;
                    }
                    case "false": {
                        // we've to change the class style
                        l.getStyleClass().add("red_label");
                    }
                    default: {
                        System.out.println("Non possible that this appen in a boolean value");
                        break;
                    }
                }
            }
        }
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void visualize(){

        // clear the list labels before
        labelList.clear();

        label_ciclo.setText(String.valueOf(conn.findDato("Ciclo automatico in corso",conn.datoHashSet).isStato()));
        label_emergenza.setText(String.valueOf(conn.findDato("Emergenza non ripristinata",conn.datoHashSet).isStato()));
        label_max.setText(String.valueOf(conn.findDato("Livello massimo filtrato",conn.datoHashSet).isStato()));
        label_min.setText(String.valueOf(conn.findDato("Livello minimo filtrato",conn.datoHashSet).isStato()));
        label_Imp.setText(String.valueOf(conn.findDato("anomalia impianto in corso",conn.datoHashSet).isStato()));

        Pompa pomp1 = (Pompa) conn.findDato("Pompa 1",conn.datoHashSet);
        label_action_pomp1.setText(String.valueOf(pomp1.isStato()));
        label_freq1.setText(String.valueOf(pomp1.getFreq()));

        Pompa pomp2 = (Pompa) conn.findDato("Pompa 2",conn.datoHashSet);
        label_action_pomp2.setText(String.valueOf(pomp2.isStato()));
        label_freq2.setText(String.valueOf(pomp2.getFreq()));


        // add the label in the labels list
        labelList.add(label_ciclo);
        labelList.add(label_emergenza);
        labelList.add(label_freq2);
        labelList.add(label_freq1);
        labelList.add(label_max);
        labelList.add(label_min);
        labelList.add(label_action_pomp1);
        labelList.add(label_action_pomp2);
        labelList.add(label_Imp);

        // change status visualized
        changeEvents();
    }

    protected void refreshScreen(){
        conn.refresh();
        visualize();
        System.out.println("Screen is refreshing");
    }

}
