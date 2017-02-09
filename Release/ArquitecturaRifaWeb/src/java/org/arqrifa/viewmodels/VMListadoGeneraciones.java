package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;

public class VMListadoGeneraciones extends ViewModel{
        private List<DTGeneracion> generaciones;

    public VMListadoGeneraciones() {
        this(new ArrayList<>(), "");
    }
    public VMListadoGeneraciones(List<DTGeneracion> generaciones, String mensaje) {
        super(mensaje);
        this.generaciones = generaciones;
    }

    public List<DTGeneracion> getGeneraciones() {
        return generaciones;
    }

    public void setGeneraciones(List<DTGeneracion> generaciones) {
        this.generaciones = generaciones;
    }

}
