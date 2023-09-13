package com.example.serial_master_conn;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.Timer;
import java.util.TimerTask;

public class RefreshScreenTask extends Task<Void> {
    private final Controller_MainFrame cm;
    public RefreshScreenTask(Controller_MainFrame cm) {
        this.cm = cm;
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
        return null;
    }
}
