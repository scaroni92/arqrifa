package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTReunion;

public class VMListadoReuniones extends ViewModel {

    private List<DTReunion> reuniones;
    private String filtro;

    public VMListadoReuniones(List<DTReunion> reuniones, String filtro, String mensaje) {
        super(mensaje);
        this.reuniones = reuniones;
        this.filtro = filtro;
    }

    public VMListadoReuniones() {
        this(new ArrayList(), "todas", "");
    }

    public List<DTReunion> getReuniones() {
        return reuniones;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setReuniones(List<DTReunion> reuniones) {
        this.reuniones = reuniones;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

}
