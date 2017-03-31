package arqrifa.org.arquitecturarifamobile.datatypes;

import java.io.Serializable;
import java.util.List;

public class DTPropuesta implements Serializable{

    private int id;
    private String pregunta;
    private List<DTRespuesta> respuestas;

    public DTPropuesta(int id, String pregunta, List<DTRespuesta> respuestas) {
        this.id = id;
        this.pregunta = pregunta;
        this.respuestas = respuestas;
    }

    public DTPropuesta() {
    }

    public int getId() {
        return id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public List<DTRespuesta> getRespuestas() {
        return respuestas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public void setRespuestas(List<DTRespuesta> respuestas) {
        this.respuestas = respuestas;
    }



}
