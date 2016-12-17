package org.arqrifa.datatypes;

import java.util.List;

public class DTPropuesta {

    private int id;
    private String pregunta;
    private List<String> respuestas;

    public DTPropuesta(int id, String pregunta, List<String> respuestas) {
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

    public List<String> getRespuestas() {
        return respuestas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public void setRespuestas(List<String> respuestas) {
        this.respuestas = respuestas;
    }

}
