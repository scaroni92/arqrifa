package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTReunion;

public class VMListaAsistencias extends ViewModel {

    private List<DTAsistencia> asistencias;

    public VMListaAsistencias(DTReunion reunion, List<DTAsistencia> asistencias, String mensaje) {
        super(mensaje);
        this.asistencias = asistencias;
    }

    public VMListaAsistencias() {
        this(null, new ArrayList(), "");
    }

    public List<DTAsistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<DTAsistencia> asistencias) {
        this.asistencias = asistencias;
    }

}
