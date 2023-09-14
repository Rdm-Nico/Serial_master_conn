package com.example.serial_master_conn;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/* The specific class for the Pompa(Pomp in English) information from  the dirty water filter system
* We've also another attribute telling the freq of the pomp
* @authors Rossi Nicoló
*  */
@XmlRootElement
public class Pompa extends Dato{

    short freq = 0;
    /* We take a list of pair because we've two different information to take:
    *       1. The status of the Pomp that has a specific offset and a specific register
    *       2. The freq of the Pomp that has is specific register that's different from the previous
    *
    *  */
    List<Pair<Type,Integer>> registers_pomp = new ArrayList<Pair<Type,Integer>>();

    public Pompa(String nome) {
        super(nome);
    }

    public Pompa(String nome, int register_1, int poss, int register_2) {
        super(nome,poss);
        Pair<Type,Integer> first = new Pair<>(Type.BIT,register_1);
        registers_pomp.add(first);
        Pair<Type,Integer> second = new Pair<>(Type.INT16,register_2);
        registers_pomp.add(second);
    }


    public Pompa(String nome, boolean stato, int registro, int poss) {
        super(nome, stato, registro, poss);
    }

    public Pompa(String nome, boolean stato) {
        super(nome, stato);
    }

    public short getFreq() {
        return freq;
    }

    // get the register depending on the type we provide
    public int getRegistro(Type tipo) {
        return registers_pomp.get(0).getL() == tipo ? registers_pomp.get(0).getR(): registers_pomp.get(1).getR();
    }

    public void setFreq(short freq) {
        this.freq = freq;
    }

    @Override
    public String toString() {
        return "Pompa{" +
                "freq=" + freq +
                '}';
    }
}
