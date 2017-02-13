package org.arqrifa.datatypes;

public class DTRespuesta {

    private int id;
    private String respuesta;
    private int cantidadVotos;

    public DTRespuesta() {
        this(0, "", 0);
    }

    public DTRespuesta(int id, String respuesta, int cantidadVotos) {
        this.id = id;
        this.respuesta = respuesta;
        this.cantidadVotos = cantidadVotos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public int getCantidadVotos() {
        return cantidadVotos;
    }

    public void setCantidadVotos(int cantidadVotos) {
        this.cantidadVotos = cantidadVotos;
    }


}
