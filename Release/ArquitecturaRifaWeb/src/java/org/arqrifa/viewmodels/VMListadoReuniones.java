package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTReunion;

public class VMListadoReuniones extends ViewModel {

    private List<DTReunion> reuniones;
    private String filtro;
    private Paginacion paginacion;

    public VMListadoReuniones(List<DTReunion> reuniones, String filtro, String mensaje) {
        super(mensaje);
        this.reuniones = reuniones;
        this.filtro = filtro;
        paginacion = new Paginacion(reuniones, 5);
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
        this.paginacion.setElementos(reuniones);
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public Paginacion getPaginacion() {
        return paginacion;
    }

    public void setPaginacion(Paginacion paginacion) {
        this.paginacion = paginacion;
    }

    public void setPg(String pagina) throws Exception {
        this.paginacion.setPaginaSolicitada(pagina);
    }
}
