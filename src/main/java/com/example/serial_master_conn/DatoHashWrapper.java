package com.example.serial_master_conn;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;

@XmlRootElement(name = "dati")
public class DatoHashWrapper {
    private HashSet<Dato> datoHashSet;

    @XmlElement(name = "misura")
    public HashSet<Dato> getDatoHashSet() {
        return datoHashSet;
    }

    public void setDatoHashSet(HashSet<Dato> datoHashSet) {
        this.datoHashSet = datoHashSet;
    }
}
