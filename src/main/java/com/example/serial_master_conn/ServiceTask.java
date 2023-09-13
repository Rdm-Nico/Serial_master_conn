package com.example.serial_master_conn;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ServiceTask extends Service<Void> {

    protected  Controller_MainFrame controller_mainFrame;
    protected  TCP_IP_Connection conn;
    public ServiceTask(Controller_MainFrame cm, TCP_IP_Connection conn) {
        this.controller_mainFrame = cm;
        this.conn = conn;
    }

    @Override
    protected Task<Void> createTask() {
        return new RefreshScreenTask(controller_mainFrame);
    }

    protected Task<Void> createSaveTask(){
        return new SaveDataTask(conn);
    }
}
