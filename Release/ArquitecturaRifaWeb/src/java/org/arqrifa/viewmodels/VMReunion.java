package org.arqrifa.viewmodels;

public class VMReunion extends ViewModel {

    private String id;
    private String titulo;
    private String descripcion;
    private String resoluciones;
    private String fecha;
    private String hora;
    private boolean obligatoria;
    private String generacion;
    private String estado;
    private String lugar;

    //<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getResoluciones() {
        return resoluciones;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public boolean isObligatoria() {
        return obligatoria;
    }

    public String getGeneracion() {
        return generacion;
    }

    public String getEstado() {
        return estado;
    }

    public String getLugar() {
        return lugar;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setResoluciones(String resoluciones) {
        this.resoluciones = resoluciones;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setObligatoria(boolean obligatoria) {
        this.obligatoria = obligatoria;
    }

    public void setGeneracion(String generacion) {
        this.generacion = generacion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    //</editor-fold>
    
    public VMReunion(String id, String titulo, String descripcion, String fecha, String hora, boolean obligatoria, String generacion, String estado, String lugar, String mensaje) {
        super(mensaje);
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.obligatoria = obligatoria;
        this.generacion = generacion;
        this.estado = estado;
        this.lugar = lugar;
    }

    public VMReunion() {
        super();
    }

}
