package com.example.serial_master_conn;


public interface Abstract_Dato {

    boolean isStato();
    void setStato(boolean stato);
    void setNome(String nome);
    String getNome();
    void setRegistro(int num_reg);
     int getRegistro();
    void setOffset(int pos);
    int getOffset();
}
