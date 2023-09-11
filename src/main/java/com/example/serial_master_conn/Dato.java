package com.example.serial_master_conn;



import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashSet;
@XmlRootElement
public abstract class Dato implements Abstract_Dato {

    private String nome;
    private boolean stato = false;
    private int registro = 0;
    private int poss = 0;

    public Dato(){}

    public Dato(String nome) {
        this.nome = nome;

    }
    public Dato(String nome, int poss) {
        this.nome = nome;
        this.poss = poss;

    }
    public Dato(String nome, int registro, int poss) {
        this.nome = nome;
        this.registro = registro;
        this.poss = poss;
    }

    public Dato(String nome, boolean stato, int registro, int poss) {
        this.nome = nome;
        this.stato = stato;
        this.registro = registro;
        this.poss = poss;
    }
    public Dato(String nome, boolean stato){
        this.nome = nome;
        this.stato = stato;
    }

    @Override
    public boolean isStato() {
        return stato;
    }

    @Override
    public void setStato(boolean stato) {
        this.stato = stato;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override @XmlTransient
    public void setRegistro(int num_reg) {
            this.registro = num_reg;
    }

    @Override
    public int getRegistro() {
            return registro;
    }

    @Override @XmlTransient
    public void setOffset(int pos) {
        this.poss = pos;
    }

    @Override
    public int getOffset() {
        return poss;
    }



    @Override
    public String toString() {
        return "Anomalia{" +
                "nome='" + nome + '\'' +
                ", stato=" + stato +
                ", registro=" + registro +
                ", poss=" + poss +
                '}';
    }
}
