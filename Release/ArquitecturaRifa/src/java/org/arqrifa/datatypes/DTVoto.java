package org.arqrifa.datatypes;

import java.util.List;

public class DTVoto {

    private DTUsuario usuario;
    private List<DTRespuesta> respuestasEscogidas;

    public DTVoto(DTUsuario usuario, List<DTRespuesta> respuestasEscogidas) {
        this.usuario = usuario;
        this.respuestasEscogidas = respuestasEscogidas;
    }

    public DTVoto() {
    }

    public DTUsuario getUsuario() {
        return usuario;
    }

    public List<DTRespuesta> getRespuestasEscogidas() {
        return respuestasEscogidas;
    }

    public void setUsuario(DTUsuario usuario) {
        this.usuario = usuario;
    }

    public void setRespuestasEscogidas(List<DTRespuesta> respuestasEscogidas) {
        this.respuestasEscogidas = respuestasEscogidas;
    }

}
