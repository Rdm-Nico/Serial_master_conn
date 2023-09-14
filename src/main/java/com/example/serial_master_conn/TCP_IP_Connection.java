package com.example.serial_master_conn;

import com.ghgande.j2mod.modbus.procimg.Register;

import java.util.HashSet;

/* This class allow the Connection between the GUI and the Serial_Conn class( which is what really establishes the serial connection). In particular:
*
*       1. Handle  the connection;
*       2. Save the information data from the connection in a HashSet;
*
*@authors Rossi Nicoló
*   */

public class TCP_IP_Connection {

    private final Serial_Conn conn;
    private Register[] registers;
    private final bitreader br;
    protected HashSet<Dato> datoHashSet;

    protected TCP_IP_Connection(){
        conn = new Serial_Conn("192.168.0.236",502);
        conn.connect();
        conn.reading_registers();

        registers = conn.getRegisters();
        br = new bitreader(registers);



        System.out.println(br.toString(4));

        System.out.println(conn.toString());



        datoHashSet = new HashSet<>();
        addsAttributes();

    }
    // This method take from the bit reader class the information that we need and save it
    private void  addsAttributes(){
        // adds the attributes
        Anomalia a_Impianto_In_Cor = new Anomalia("anomalia impianto in corso",4,6);
        Pompa pomp1 = new Pompa("Pompa 1",4,13,8);
        Pompa pomp2 = new Pompa("Pompa 2",4,14,9);
        Anomalia ciclo_auto = new Anomalia("Ciclo automatico in corso",4,11);
        Anomalia livello_max = new Anomalia("Livello massimo filtrato",0,12);
        Anomalia livello_min = new Anomalia("Livello minimo filtrato",0,11);
        Anomalia emergenza_rip = new Anomalia("Emergenza non ripristinata",0,8);

        br.isValueOf(a_Impianto_In_Cor.getRegistro(),a_Impianto_In_Cor.getOffset(),Type.BIT);
        a_Impianto_In_Cor.setStato(br.getBit_value());
        datoHashSet.add(a_Impianto_In_Cor);

        br.isValueOf(pomp1.getRegistro(Type.BIT),pomp1.getOffset(),Type.BIT);
        br.isValueOf(pomp1.getRegistro(Type.INT16),pomp1.getOffset(),Type.INT16);
        pomp1.setStato(br.getBit_value());
        pomp1.setFreq(br.getInt16_value());
        datoHashSet.add(pomp1);

        br.isValueOf(pomp2.getRegistro(Type.BIT),pomp2.getOffset(),Type.BIT);
        br.isValueOf(pomp2.getRegistro(Type.INT16),pomp2.getOffset(),Type.INT16);
        pomp2.setStato(br.getBit_value());
        pomp2.setFreq(br.getInt16_value());
        datoHashSet.add(pomp2);

        br.isValueOf(ciclo_auto.getRegistro(),ciclo_auto.getOffset(),Type.BIT);
        ciclo_auto.setStato(br.getBit_value());
        datoHashSet.add(ciclo_auto);

        br.isValueOf(livello_max.getRegistro(),livello_max.getOffset(),Type.BIT);
        livello_max.setStato(br.getBit_value());
        datoHashSet.add(livello_max);

        br.isValueOf(livello_min.getRegistro(), livello_min.getOffset(), Type.BIT);
        livello_min.setStato(br.getBit_value());
        datoHashSet.add(livello_min);

        br.isValueOf(emergenza_rip.getRegistro(), emergenza_rip.getOffset(), Type.BIT);
        emergenza_rip.setStato(br.getBit_value());
        datoHashSet.add(emergenza_rip);
    }
    protected void refresh(){
        try {
            conn.refresh();
        } catch (Serial_Conn.InvalidReconnectionsExpection e) {
            throw new RuntimeException(e);
        }
        // free the memory
        datoHashSet.clear();

        registers = conn.getRegisters();
        br.registers = registers;

        addsAttributes();

    }
    protected void disconnect(){
        conn.disconnect();
    }

    /* method that take a String and return the object who is associate
    *
    * @throws Exception if  no object  find
    * */
    protected  Dato findDato(String nome, HashSet<Dato> datoHashSet) throws Exception {
        for (Dato dato : datoHashSet) {
            if (dato.getNome().equals(nome)) {
                return dato;
            }
        }
        throw new Exception("find no objects associated with this name");
    }
}
