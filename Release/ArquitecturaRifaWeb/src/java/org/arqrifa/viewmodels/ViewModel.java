package org.arqrifa.viewmodels;

import java.io.Serializable;

public class ViewModel implements Serializable {

    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ViewModel() {
        this("");
    }

    public ViewModel(String mensaje) {
        this.mensaje = mensaje;
    }
}
