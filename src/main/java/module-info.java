module com.example.serial_master_conn {
    requires javafx.controls;
    requires javafx.fxml;
    requires j2mod;
    requires java.xml.bind;


    opens com.example.serial_master_conn to javafx.fxml, java.xml.bind;
    exports com.example.serial_master_conn;
}