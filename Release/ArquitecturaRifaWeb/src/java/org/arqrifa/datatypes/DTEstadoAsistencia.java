package org.arqrifa.datatypes;

public class DTEstadoAsistencia {

    public static String PRESENTE = "Presente";
    public static String AUSENTE = "Ausente";

    private DTUsuario estudiante;
    private String estado;

    public DTEstadoAsistencia(DTUsuario estudiante, String estado) {
        this.estudiante = estudiante;
        this.estado = estado;
    }

    public DTEstadoAsistencia() {
        this(null, "");
    }

    public DTUsuario getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(DTUsuario estudiante) {
        this.estudiante = estudiante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
