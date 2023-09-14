package com.example.serial_master_conn;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.Timer;
import java.util.TimerTask;

/* This Class handle a thread for refreshing the data and visualize the changes in the GUI
* @authors Rossi Nicoló
* */
public class RefreshScreenTask extends Task<Void> {
    private final Controller_MainFrame cm;
    public RefreshScreenTask(Controller_MainFrame cm) {
        this.cm = cm;
    }

    @Override
    protected Void call() throws Exception {
        Thread.sleep(1000);

        // allows the correct competition between the threads
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cm.refreshScreen();
            }
        });
        return null;
    }
}
