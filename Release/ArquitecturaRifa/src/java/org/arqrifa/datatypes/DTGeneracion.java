package org.arqrifa.datatypes;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DTGeneracion {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DTGeneracion() {
        this(0);
    }

    public DTGeneracion(int id) {
        this.id = id;
    }
    
    
}
