package com.example.serial_master_conn;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

public class Serial_Conn {
    String ip_adress;
    ModbusTCPMaster master;
    Register[] registers;
    int  port;

    public Serial_Conn(String ip_adress, int port) {
        this.master = new ModbusTCPMaster(ip_adress,port);
        master.setReconnecting(true);

        this.ip_adress = ip_adress;
        this.port = port;
    }

    public void connect(){
        try {
            master.connect();
            System.out.println("Connection is On: \n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void reading_registers(){
        try {
            registers = master.readMultipleRegisters(1,0,100);
        } catch (ModbusException e) {
            throw new RuntimeException(e);
        }

    }

    public String getIp_adress() {
        return ip_adress;
    }

    public void setIp_adress(String ip_adress) {
        this.ip_adress = ip_adress;
    }

    public Register[] getRegisters() {
        return registers;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean IsOn(){
        return master.isConnected();
    }
    public void disconnect(){
        if(master != null){
            master.disconnect();
            System.out.println("Connection is closed\n");
        }
    }

    public void refresh() throws InvalidReconnectionsExpection {
        if(!master.isReconnecting()){
            throw new InvalidReconnectionsExpection("Error: the reconnection phase is not correct\n");
        }

        reading_registers();
    }

    @Override
    public String toString() {
        return "Serial_Comm{" +
                "ip_adress='" + ip_adress + '\'' +
                ", state=" + master.isConnected() +
                ", port=" + port +
                '}';
    }


    static class InvalidReconnectionsExpection extends Exception{
        public  InvalidReconnectionsExpection(String message){
            System.out.println(message);
        }
    }


}
