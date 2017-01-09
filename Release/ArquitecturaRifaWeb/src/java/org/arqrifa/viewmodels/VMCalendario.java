package org.arqrifa.viewmodels;

import java.util.List;
import org.arqrifa.datatypes.DTReunion;

public class VMCalendario extends ViewModel {

    private List<DTReunion> reuniones;

    public VMCalendario(String mensaje) {
        super(mensaje);
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
