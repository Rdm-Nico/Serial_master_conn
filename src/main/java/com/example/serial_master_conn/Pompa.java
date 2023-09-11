package com.example.serial_master_conn;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Pompa extends Dato{

    short freq = 0;
    List<Pair<Type,Integer>> registri = new ArrayList<Pair<Type,Integer>>();

    public Pompa(String nome) {
        super(nome);
    }

    public Pompa(String nome, int registro1, int poss, int registro2) {
        super(nome,poss);
        Pair<Type,Integer> first = new Pair<>(Type.BIT,registro1);
        registri.add(first);
        Pair<Type,Integer> second = new Pair<>(Type.INT16,registro2);
        registri.add(second);
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


    public int getRegistro(Type tipo) {
        return registri.get(0).getL() == tipo ? registri.get(0).getR(): registri.get(1).getR();
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
