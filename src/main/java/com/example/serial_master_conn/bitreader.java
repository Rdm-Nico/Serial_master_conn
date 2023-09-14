package com.example.serial_master_conn;

import com.ghgande.j2mod.modbus.procimg.Register;

import java.util.LinkedList;


/* This class has the task to read the specific register of the Modbus protocol and return some information depending on the communication table we have
*
* @authors Rossi Nicoló
* */

public class bitreader {


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

    // This method obtained from  the registers the specific information we need, the type op is useful for specify the type of data we need
    public void isValueOf(int register_num,int pos, Type op) {
        int ris;
        // save the specific register we have to work with
        buffer = registers[register_num].toShort();
        switch (op) {
            case BIT -> {
                // we right shift for the number of bits required
                buffer = (short) (buffer >> pos);

                // and extract it
                ris = buffer & 1;

                // In boolean
                bit_value = ris == 1;
            }
            case INT16 -> {
                int16_value = buffer;
            }
            case UINT16 -> {
                uint16_value = (char) buffer;
            }
            default -> {
                System.out.println("Error in the type passed/n");
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

    /* This method is helpful if you want to print every single bit in the buffer.
    *
    * Notice we give a Big Endian buffer and we return a Littel Endian string */
    private String buffer_in_str(short buffer_str) {
        LinkedList<Integer> ris = new LinkedList<>();
        while(buffer_str != 0){
            int e =  (buffer_str & 1);

            ris.addFirst(e);

            // eliminate the single bit
            buffer_str >>= 1;
        }
        return ris.toString();
    }
}
