package com.example.serial_master_conn;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;

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
            //refreshService.createTask();
            //refreshService.start();

            scheduledService =  new ScheduledService() {
                @Override
                protected Task createTask() {
                    return refreshService.createTask();
                }
            };
            scheduledService.setPeriod(Duration.millis(3000));
            scheduledService.start();



            /*

            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<>() {
                        @Override
                        protected Void call() throws Exception {
                            Thread.sleep(1000);
                            // background work
                            refreshScreen();

                            Timeline timeline = new Timeline();
                            timeline.setCycleCount(3);

                            timeline.play();
                            //System.out.println("Screen is refreshing");



                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try{

                                            TimerTask timerTask = new TimerTask() {
                                                @Override
                                                public void run() {
                                                    refreshScreen();
                                                }
                                            };

                                            Timer clock = new Timer(true);
                                            clock.scheduleAtFixedRate(timerTask, 1000, 3*2000);




                                    } finally {
                                        latch.countDown();
                                    }
                                }
                            });
                            latch.await();
                            return null;
                        }
                    };
                }
            };
            service.start(); */
        }

        if(event.getSource() == button_disconnect){
            if(scheduledService.isRunning())
                scheduledService.cancel();
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

    protected void refreshScreen(){
        conn.refresh();
        visualize();
        System.out.println("Screen is refreshing");
    }

}
