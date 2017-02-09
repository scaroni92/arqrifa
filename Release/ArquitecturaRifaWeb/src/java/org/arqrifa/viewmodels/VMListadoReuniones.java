package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTReunion;

public class VMListadoReuniones extends ViewModel {

    private List<DTReunion> reuniones;

    public VMListadoReuniones() {
        this(new ArrayList(), "");
    }


    public VMListadoReuniones(List<DTReunion> reuniones, String mensaje) {
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
