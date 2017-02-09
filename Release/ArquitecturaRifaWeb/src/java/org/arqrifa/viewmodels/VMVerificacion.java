package org.arqrifa.viewmodels;

public class VMVerificacion extends ViewModel {

    private boolean verificada;
    private String descripcion;

    public VMVerificacion(boolean verificada, String descripcion, String mensaje) {
        super(mensaje);
        this.verificada = verificada;
        this.descripcion = descripcion;
    }

    public VMVerificacion() {
        this(false, "", "");
    }

    public void setVerificada(boolean verificada) {
        this.verificada = verificada;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isVerificada() {
        return verificada;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
