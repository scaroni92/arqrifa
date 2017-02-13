package org.arqrifa.datatypes;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DTMensajeError {

    private String mensaje;

    public DTMensajeError() {
    }

    public DTMensajeError(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
