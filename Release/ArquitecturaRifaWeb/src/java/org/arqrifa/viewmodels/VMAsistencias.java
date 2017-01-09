package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTReunion;

public class VMAsistencias extends ViewModel {

    private DTReunion reunion;
    private List<DTAsistencia> asistencias;

    public VMAsistencias(DTReunion reunion, List<DTAsistencia> asistencias, String mensaje) {
        super(mensaje);
        this.reunion = reunion;
        this.asistencias = asistencias;
    }

    public VMAsistencias() {
        this(null, new ArrayList(), "");
    }

    public DTReunion getReunion() {
        return reunion;
    }

    public void setReunion(DTReunion reunion) {
        this.reunion = reunion;
    }

    public List<DTAsistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<DTAsistencia> asistencias) {
        this.asistencias = asistencias;
    }



}
