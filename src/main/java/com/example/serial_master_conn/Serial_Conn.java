package com.example.serial_master_conn;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

/* This class handle a Modbus Connection
*
* @authors Rossi Nicoló
*  */

public class Serial_Conn {
    private String ip_address;
    private static  ModbusTCPMaster master;
    private Register[] registers;
    private int  port;

    public Serial_Conn(String ip_address, int port) {
        master = new ModbusTCPMaster(ip_address,port);
        master.setReconnecting(true);

        this.ip_address = ip_address;
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
    // This method  enable to save all the registers from the connection, ALL because is not so memory consuming
    public void reading_registers(){
        try {
            registers = master.readMultipleRegisters(1,0,100);
        } catch (ModbusException e) {
            throw new RuntimeException(e);
        }

    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
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
    static class InvalidReconnectionsExpection extends Exception{
        public  InvalidReconnectionsExpection(String message){
            System.out.println(message);
        }
    }

    @Override
    public String toString() {
        return "Serial_Comm{" +
                "ip_adress='" + ip_address + '\'' +
                ", state=" + master.isConnected() +
                ", port=" + port +
                '}';
    }





}
