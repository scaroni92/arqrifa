package arqrifa.org.arquitecturarifamobile.datatypes;

import java.util.ArrayList;
import java.util.List;

public class DTVotacion {

    private DTUsuario usuario;
    private DTReunion reunion;
    private List<DTRespuesta> respuestasEscogidas;

    public DTVotacion(DTUsuario usuario, DTReunion reunion, List<DTRespuesta> respuestasEscogidas) {
        this.usuario = usuario;
        this.reunion = reunion;
        this.respuestasEscogidas = respuestasEscogidas;
    }

    public DTVotacion() {
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
