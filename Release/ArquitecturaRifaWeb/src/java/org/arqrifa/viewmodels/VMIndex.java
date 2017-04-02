package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTSolicitud;

public class VMIndex extends ViewModel {
    private DTReunion proximaReunion;
    private DTReunion ultimaReunion;
    private List<DTSolicitud> solicitudes;

    public VMIndex() {
        this(null, null, new ArrayList(), "");
    }

    

    public VMIndex(DTReunion proximaReunion, DTReunion ultimaReunion, List<DTSolicitud> solicitudes, String mensaje) {
        super(mensaje);
        this.proximaReunion = proximaReunion;
        this.ultimaReunion = ultimaReunion;
        this.solicitudes = solicitudes;
    }

    public DTReunion getProximaReunion() {
        return proximaReunion;
    }

    public DTReunion getUltimaReunion() {
        return ultimaReunion;
    }

    public List<DTSolicitud> getSolicitudes() {
        return solicitudes;
    }

    public void setProximaReunion(DTReunion proximaReunion) {
        this.proximaReunion = proximaReunion;
    }

    public void setUltimaReunion(DTReunion ultimaReunion) {
        this.ultimaReunion = ultimaReunion;
    }

    public void setSolicitudes(List<DTSolicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }
    
    
}
