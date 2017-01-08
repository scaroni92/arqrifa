package org.arqrifa.viewmodels;

import org.arqrifa.datatypes.DTReunion;

public class VMReunion extends ViewModel {

    private DTReunion reunion;

    public VMReunion() {
        this(null, "");
    }
    

    public VMReunion(DTReunion reunion, String mensaje) {
        super(mensaje);
        this.reunion = reunion;
    }

    public DTReunion getReunion() {
        return reunion;
    }

    public void setReunion(DTReunion reunion) {
        this.reunion = reunion;
    }

    public int getCantidadParticipantes(){
        
        int can = reunion.getParticipantes().size();
        return can;
    }
}
