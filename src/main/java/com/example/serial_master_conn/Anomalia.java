package com.example.serial_master_conn;

public class Anomalia extends Dato {
    public Anomalia(String nome) {
        super(nome);
    }

    public Anomalia(String nome, int registro, int poss){
        super(nome,registro,poss);
    }


    public Anomalia(String nome, boolean stato, int registro, int poss) {
        super(nome, stato, registro, poss);
    }

    public Anomalia(String nome, boolean stato) {
        super(nome, stato);
    }
}
