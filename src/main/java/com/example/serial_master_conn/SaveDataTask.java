package com.example.serial_master_conn;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.JavaFXBuilderFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/* This class handle a thread that saving the data coming from the connection in an XML file( the JAXB library allows it) with the filename  like this:
*
*           FILENAME: yyyy_MM_dd__hh__mm.xml
*
* @authors Rossi Nicoló
*   */

public class SaveDataTask extends Task<Void> {
    private File output;
    private  final TCP_IP_Connection conn;
    public SaveDataTask(TCP_IP_Connection conn) {
        this.conn = conn;
    }

    @Override
    protected Void call() throws Exception,JAXBException {
        Thread.sleep(1000);

        // allows the correct competition between the threads
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    Date date = new Date();

                    SimpleDateFormat parser = new SimpleDateFormat("yyyy_MM_dd_hh_mm");

                    output = new File("S:\\ROSSICARLO.Doc\\CNC Rettifica\\Losma\\Storico\\" + parser.format(date) + ".xml");
                    JAXBContext context = JAXBContext.newInstance(DatoHashWrapper.class);
                    Marshaller marshaller = context.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);

                    // wrapping our data
                    DatoHashWrapper wrapper = new DatoHashWrapper();
                    wrapper.setDatoHashSet(conn.datoHashSet);

                    // marshelling and saving XML to the file
                    marshaller.marshal(wrapper,output);
                    System.out.println("\n");
                    System.out.println("---------------------------------------");
                    System.out.println("save data in path:"+ output.getPath());
                    System.out.println("---------------------------------------");
                    System.out.println("\n");

                }catch (JAXBException e){
                    e.printStackTrace();
                }
            }
        });
        return null;
    }
}
