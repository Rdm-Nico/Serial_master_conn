package com.example.serial_master_conn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import static java.lang.Thread.sleep;

public class Controller_MainFrame {

    // for connection:
    private TCP_IP_Connection conn;

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
        // check the buttons source
        if(event.getSource() == button_Connect){
            // start connection
             conn = new TCP_IP_Connection();
             System.out.println("Set connection!!!");

             // change the text of the labels
            visualize();

            try {
                sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

        if(event.getSource() == button_disconnect){
            conn.disconnect();

            // clear the text of the labels
            label_ciclo.setText("");
            label_emergenza.setText("");
            label_max.setText("");
            label_min.setText("");
            label_Imp.setText("");
            label_action_pomp1.setText("");
            label_freq1.setText("");
            label_action_pomp2.setText("");
            label_freq2.setText("");
        }

    }

    private void visualize(){
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
    }

}
