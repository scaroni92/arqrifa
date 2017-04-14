package org.arqrifa.datatypes;

import java.util.ArrayList;
import java.util.List;

public class DTVoto {

    private DTUsuario usuario;
    private DTReunion reunion;
    private List<DTRespuesta> respuestasEscogidas;

    public DTVoto(DTUsuario usuario, DTReunion reunion, List<DTRespuesta> respuestasEscogidas) {
        this.usuario = usuario;
        this.reunion = reunion;
        this.respuestasEscogidas = respuestasEscogidas;
    }

    public DTVoto() {
        this(null, null, new ArrayList());
    }

    public DTUsuario getUsuario() {
        return usuario;
    }

    public DTReunion getReunion() {
        return reunion;
    }

    public List<DTRespuesta> getRespuestasEscogidas() {
        return respuestasEscogidas;
    }

    public void setUsuario(DTUsuario usuario) {
        this.usuario = usuario;
    }

    public void setReunion(DTReunion reunion) {
        this.reunion = reunion;
    }

    public void setRespuestasEscogidas(List<DTRespuesta> respuestasEscogidas) {
        this.respuestasEscogidas = respuestasEscogidas;
    }
    
    

    

}
