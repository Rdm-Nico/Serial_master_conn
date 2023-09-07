package com.example.serial_master_conn;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.Timer;
import java.util.TimerTask;

public class RefreshScreenTask extends Task<Void> {
    private final Controller_MainFrame cm;
    private TCP_IP_Connection conn;
    public RefreshScreenTask(Controller_MainFrame cm, TCP_IP_Connection conn) {
        this.cm = cm;
        this.conn = conn;
    }

    @Override
    protected Void call() throws Exception {
        Thread.sleep(1000);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cm.refreshScreen();
            }
        });


        System.out.println("doing background tasks");

        return null;
    }
}
