package com.example.serial_master_conn;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/* This class extends the Service class of javafx and does what you would expect from the Service class: Handle the tasks/threads of the project
* notice that the constructor take:
* @params Controller_MainFrame for change the GUI, TCP_IP_Connection for take the data from the connection
*  @authors Rossi Nicoló
*   */
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
