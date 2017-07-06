package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTSolicitud;

public class VMIndex extends ViewModel {
    private DTReunion proximaReunion;
    private DTReunion ultimaReunion;

    public VMIndex() {
        this(null, null, new ArrayList(), "");
    }

    public VMIndex(DTReunion proximaReunion, DTReunion ultimaReunion, List<DTSolicitud> solicitudes, String mensaje) {
        super(mensaje);
        this.proximaReunion = proximaReunion;
        this.ultimaReunion = ultimaReunion;
    }

    public DTReunion getProximaReunion() {
        return proximaReunion;
    }

    public DTReunion getUltimaReunion() {
        return ultimaReunion;
    }

    public void setProximaReunion(DTReunion proximaReunion) {
        this.proximaReunion = proximaReunion;
    }

    public void setUltimaReunion(DTReunion ultimaReunion) {
        this.ultimaReunion = ultimaReunion;
    }
    
}
