package org.arqrifa.datatypes;

// Este DataType es utilizado unicamente como wrapper en el servicio

public class DTAsistenciaWrapper {
    private DTUsuario usuario;
    private DTReunion reunion;

    public DTAsistenciaWrapper(DTUsuario usuario, DTReunion reunion) {
        this.usuario = usuario;
        this.reunion = reunion;
    }

    public DTAsistenciaWrapper() {
        this(null, null);
    }

    public DTUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(DTUsuario usuario) {
        this.usuario = usuario;
    }

    public DTReunion getReunion() {
        return reunion;
    }

    public void setReunion(DTReunion reunion) {
        this.reunion = reunion;
    }
    
    
}
