package org.arqrifa.datatypes;

public class DTRespuesta {

    private int id;
    private String respuesta;

    public DTRespuesta(int id, String respuesta) {
        this.id = id;
        this.respuesta = respuesta;
    }

    public DTRespuesta() {
    }

    public int getId() {
        return id;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

}
