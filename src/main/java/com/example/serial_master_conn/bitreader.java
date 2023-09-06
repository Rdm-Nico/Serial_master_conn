package com.example.serial_master_conn;

import com.ghgande.j2mod.modbus.procimg.Register;

import java.util.LinkedList;


public class bitreader {
    // una classe che come costruttore ha tutti i registri
    // ed un metodo: GetBit( int pos) che preleva l'indirizzo selezionato e il tipo che siamo interessati ad avere:
    // vogliamo:
    //              bit
    //              int16
    //              uint16

    boolean bit_value;
    short int16_value;
    char uint16_value;
    Register[] registers;
    short buffer;
    Type tipo;

    public bitreader( Register[] registers) {
        this.registers = registers;
        this.buffer = 0;
        this.tipo = Type.NAN;
    }


    public void isValueOf(int register_num,int pos, Type op) {
        int ris;
        buffer = registers[register_num].toShort();
        switch (op) {
            case BIT -> {
                buffer = (short) (buffer >> pos);
                ris = buffer & 1;

                bit_value = ris == 1;
                break;
            }
            case INT16 -> {
                int16_value = buffer;
                break;
            }
            case UINT16 -> {
                uint16_value = (char) buffer;
                break;
            }
            default -> {
                System.out.println("Error in the type passed/n");
                break;
            }
        }


    }

    public boolean getBit_value() {
        return bit_value;
    }

    public short getInt16_value() {
        return int16_value;
    }

    public char getUint16_value() {
        return uint16_value;
    }


    public String toString(int register_num) {
        short buffer_str = registers[register_num].toShort();
        String in_str = buffer_in_str(buffer_str);
        return "bitreader{" +
                "bytes=" + buffer_str +
                " to bits:" + in_str +
                '}';
    }

    private String buffer_in_str(short buffer_str) {
        LinkedList<Integer> ris = new LinkedList<>();
        int pos = 0;
        while(buffer_str != 0){
            int e =  (buffer_str & 1);

            ris.addFirst(e);


            buffer_str >>= 1;
        }
        return ris.toString();
    }
}
