package com.example.serial_master_conn;

import com.ghgande.j2mod.modbus.procimg.Register;

import java.util.HashSet;

public class TCP_IP_Connection {


    protected TCP_IP_Connection(){
    Serial_Conn conn = new Serial_Conn("192.168.0.236",502);
    conn.connect();
    conn.reading_registers();

    Register[] registers = conn.getRegisters();
    bitreader br = new bitreader(registers);

    System.out.println(br.toString(4));

    System.out.println(conn.toString());

    try {
        conn.refresh();
    } catch (Serial_Conn.InvalidReconnectionsExpection e) {
        throw new RuntimeException(e);
    }

        HashSet<Dato> datoHashSet = new HashSet<>();
        // aggiungiamo gli attributi
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

        Dato ris = findDato("Emergenza non ripristinata",datoHashSet);

        conn.disconnect();

    }
    private static Dato findDato(String nome, HashSet<Dato> datoHashSet) {
        for (Dato dato : datoHashSet) {
            if (dato.getNome().equals(nome)) {
                return dato;
            }
        }
        return null;
    }
}