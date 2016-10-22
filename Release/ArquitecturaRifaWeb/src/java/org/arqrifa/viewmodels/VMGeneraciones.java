package org.arqrifa.viewmodels;

import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;

public class VMGeneraciones extends ViewModel {

    private List<DTGeneracion> generaciones;

    public List<DTGeneracion> getGeneraciones() {
        return generaciones;
    }

    public void setGeneraciones(List<DTGeneracion> generaciones) {
        this.generaciones = generaciones;
    }

    public VMGeneraciones(List<DTGeneracion> generaciones, String mensaje) {
        super(mensaje);
        this.generaciones = generaciones;
    }

    public VMGeneraciones() {

    }
}
