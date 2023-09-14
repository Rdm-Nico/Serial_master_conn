package com.example.serial_master_conn;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.*;

/*
* This class has the aim of control the GUI and starting the threads and the connection
*
*
* @authors Rossi Nicoló
*  */


public class Controller_MainFrame{




    private TCP_IP_Connection conn;

    private ServiceTask refreshService;
    private ScheduledService<?> scheduledService;
    private ScheduledService<?> savedataService;


    /*
    * This Arraylist of pair  is useful  for handle the common tasks that the GUI have to do ( clear all the labels at the end of the connection, ecc...)  and the specific change in
    *  every labels
    * */
    private final List<Pair<String,Label>> labelList = new ArrayList<Pair<String,Label>>();

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




    @FXML
    void Events_Buttons(ActionEvent event) {
        if(event.getSource() == button_Connect){
             conn = new TCP_IP_Connection();
             System.out.println("Set connection!!!");


            visualize();


            // starting all the threads
            refreshService = new ServiceTask(this,conn);

            scheduledService =  new ScheduledService() {
                @Override
                protected Task createTask() {
                    return refreshService.createTask();
                }
            };
            scheduledService.setPeriod(Duration.millis(5000));
            scheduledService.start();

            savedataService = new ScheduledService() {
                @Override
                protected Task createTask() {
                    return refreshService.createSaveTask();
                }
            };
            savedataService.setPeriod(Duration.hours(8));
            savedataService.start();

        }

        if(event.getSource() == button_disconnect){
            // disconnect the conn and  kills the threads
            if(scheduledService.isRunning())
                scheduledService.cancel();

            if(savedataService.isRunning())
                savedataService.cancel();

            conn.disconnect();

            // clear the text of the labels
            for(Pair<String,Label> p:labelList){
                p.getR().getStyleClass().clear();
                p.getR().getStyleClass().add("init_label");
                p.getR().getStyleClass().add("label");
                p.getR().setText("");
            }

            labelList.clear();
        }

    }

    /* This method set the labels Text with the data from the connection */
    private void visualize(){

        // clear the list labels before
        labelList.clear();


        // this expression take an "information" of the state from the connection HashSet and change it to an "On" "Off" string
        try {
            label_ciclo.setText(OnOffChange(String.valueOf(conn.findDato("Ciclo automatico in corso",conn.datoHashSet).isStato())));

            label_emergenza.setText(OnOffChange(String.valueOf(conn.findDato("Emergenza non ripristinata",conn.datoHashSet).isStato())));
            label_max.setText(OnOffChange(String.valueOf(conn.findDato("Livello massimo filtrato",conn.datoHashSet).isStato())));
            label_min.setText(OnOffChange(String.valueOf(conn.findDato("Livello minimo filtrato",conn.datoHashSet).isStato())));
            label_Imp.setText(OnOffChange(String.valueOf(conn.findDato("anomalia impianto in corso",conn.datoHashSet).isStato())));

            //  The Pompa class has another information to take
            Pompa pomp1 = (Pompa) conn.findDato("Pompa 1",conn.datoHashSet);
            label_action_pomp1.setText(OnOffChange(String.valueOf(pomp1.isStato())));
            label_freq1.setText(String.valueOf(pomp1.getFreq()));

            Pompa pomp2 = (Pompa) conn.findDato("Pompa 2",conn.datoHashSet);
            label_action_pomp2.setText(OnOffChange(String.valueOf(pomp2.isStato())));
            label_freq2.setText(String.valueOf(pomp2.getFreq()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        labelList.add(new Pair<>("Ciclo automatico in Corso",label_ciclo));
        labelList.add(new Pair<>("Anomalia emergenza non ripristinata",label_emergenza));
        labelList.add(new Pair<>("Frequenza elettropompa 1",label_freq2));
        labelList.add(new Pair<>("Frequenza elettropompa 2",label_freq1));
        labelList.add(new Pair<>("Anomalia livello massimo filtrato",label_max));
        labelList.add(new Pair<>("Anomalia livello minimo filtrato",label_min));
        labelList.add(new Pair<>("Esclusione pompa 1",label_action_pomp1));
        labelList.add(new Pair<>("Esclusione pompa 2",label_action_pomp2));
        labelList.add(new Pair<>("Anomalia Impianto In Corso",label_Imp));


        changeEvents();
    }

    // This method change the color background label depending on the state of the label
    private void changeEvents() {
        for (Pair<String,Label> p: labelList){

            if(isNumeric(p.getR().getText())){
                // then take the freq of the Pompa
                int val = Integer.parseInt(p.getR().getText());
                if( val < 0 || val > 250){
                    p.getR().getStyleClass().add("red_label");
                }
                else if(val == 0){
                    p.getR().getStyleClass().add("yellow_label");
                }
            }
            else {
                switch (p.getR().getText()) {
                    case "On" -> {
                        if (p.getL() == "Anomalia Impianto In Corso" || p.getL() == "Anomalia livello massimo filtrato" || p.getL() == "Anomalia livello minimo filtrato" ||
                                p.getL() == "Esclusione pompa 1" || p.getL() == "Esclusione pompa 2" || p.getL() == "Anomalia emergenza non ripristinata") {
                            // we've to change the class style
                            p.getR().getStyleClass().add("red_label");
                        }
                        else{
                            p.getR().getStyleClass().add("green_label");
                        }
                    }
                    case "Off" -> {
                        if (p.getL() == "Anomalia Impianto In Corso" || p.getL() == "Anomalia livello massimo filtrato" || p.getL() == "Anomalia livello minimo filtrato" ||
                                p.getL() == "Esclusione pompa 1" || p.getL() == "Esclusione pompa 2" || p.getL() == "Anomalia emergenza non ripristinata") {
                            p.getR().getStyleClass().add("green_label");
                            break;
                        }
                        // we've to change the class style
                        p.getR().getStyleClass().add("red_label");
                    }
                    default -> {
                        System.out.println("Not possible that this append");
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

    /* For costumer requests was necessary to change the output to print in the GUI */
    private String OnOffChange(String value){
        if (value == "true")
            return "On";
        else
            return "Off";
    }

    protected void refreshScreen(){
        conn.refresh();
        visualize();
        System.out.println("Screen is refreshing");
    }

}
