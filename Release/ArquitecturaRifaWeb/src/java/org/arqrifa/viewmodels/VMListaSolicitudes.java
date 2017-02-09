package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;

public class VMListaSolicitudes extends ViewModel {

    private List<DTSolicitud> solicitudes;

    public List<DTSolicitud> getSolicitudes() {
        return solicitudes;
    }

    public int getCantidad() {
        return solicitudes.size();
    }

    public void setSolicitudes(List<DTSolicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public VMListaSolicitudes(List<DTSolicitud> solicitudes, String mensaje) {
        super(mensaje);
        this.solicitudes = solicitudes;
    }

    public VMListaSolicitudes() {
        this(new ArrayList(), "");
    }
}
