package org.arqrifa.viewmodels;



public class VMCrearEncuesta extends ViewModel {
    private String titulo;
    private String duracion;

    public VMCrearEncuesta() {
    }

    public VMCrearEncuesta(String titulo, String duracion, String mensaje) {
        super(mensaje);
        this.titulo = titulo;
        this.duracion = duracion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    
    

}
