package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;

public class VMSolicitudes extends ViewModel {

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

    public VMSolicitudes(List<DTSolicitud> solicitudes, String mensaje) {
        super(mensaje);
        this.solicitudes = solicitudes;
    }

    public VMSolicitudes() {
        this(new ArrayList(), "");
    }
}
