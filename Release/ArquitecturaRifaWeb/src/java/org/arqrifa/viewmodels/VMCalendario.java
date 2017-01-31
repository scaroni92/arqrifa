package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTReunion;

public class VMCalendario extends ViewModel {

    private List<DTReunion> reuniones;

    public VMCalendario() {
        this(new ArrayList(), "");
    }

    public VMCalendario(List<DTReunion> reuniones, String mensaje) {
        super(mensaje);
        this.reuniones = reuniones;
    }

    public List<DTReunion> getReuniones() {
        return reuniones;
    }

    public void setReuniones(List<DTReunion> reuniones) {
        this.reuniones = reuniones;
    }

}
