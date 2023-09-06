module com.example.serial_master_conn {
    requires javafx.controls;
    requires javafx.fxml;
    requires j2mod;


    opens com.example.serial_master_conn to javafx.fxml;
    exports com.example.serial_master_conn;
}