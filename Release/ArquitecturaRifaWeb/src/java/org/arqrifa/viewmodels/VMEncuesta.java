package org.arqrifa.viewmodels;

import org.arqrifa.datatypes.DTEncuesta;

public class VMEncuesta extends ViewModel {

    private String reunionId;
    private DTEncuesta encuesta;

    public VMEncuesta() {
        this("", new DTEncuesta());
    }

    public VMEncuesta(String reunionId, DTEncuesta encuesta) {
        this.reunionId = reunionId;
        this.encuesta = encuesta;
    }

    public String getReunionId() {
        return reunionId;
    }

    public DTEncuesta getEncuesta() {
        return encuesta;
    }

    public void setReunionId(String reunionId) {
        this.reunionId = reunionId;
    }

    public void setEncuesta(DTEncuesta encuesta) {
        this.encuesta = encuesta;
    }

}
