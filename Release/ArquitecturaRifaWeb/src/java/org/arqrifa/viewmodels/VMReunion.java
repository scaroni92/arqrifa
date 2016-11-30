package org.arqrifa.viewmodels;

import java.util.List;

public class VMReunion extends ViewModel {

    private String id;
    private String generacion;
    private String titulo;
    private String descripcion;
    private String fecha;
    private String hora;
    private String duracion;
    private String observaciones;
    private boolean obligatoria;
    private String lugar;
    private String estado;
    private List<String> temas;
    private List<String> resoluciones;
    private boolean habilitarBotonPanel;

    //<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public String getId() {
        return id;
    }

    public String getGeneracion() {
        return generacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public boolean isObligatoria() {
        return obligatoria;
    }

    public String getLugar() {
        return lugar;
    }

    public String getEstado() {
        return estado;
    }

    public boolean isHabilitarBotonPanel() {
        return habilitarBotonPanel;
    }

    public List<String> getTemas() {
        return temas;
    }

    public List<String> getResoluciones() {
        return resoluciones;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGeneracion(String generacion) {
        this.generacion = generacion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setObligatoria(boolean obligatoria) {
        this.obligatoria = obligatoria;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setHabilitarBotonPanel(boolean habilitarBotonPanel) {
        this.habilitarBotonPanel = habilitarBotonPanel;
    }

    public void setTemas(List<String> temas) {
        this.temas = temas;
    }

    public void setResoluciones(List<String> resoluciones) {
        this.resoluciones = resoluciones;
    }

    //</editor-fold>

    public VMReunion() {
        super();
    }

    public VMReunion(String id, String generacion, String titulo, String descripcion, String fecha, String hora, String duracion, String observaciones, boolean obligatoria, String lugar, String estado, List<String> temas, List<String> resoluciones, boolean habilitarBtnEfectuar) {
        this.id = id;
        this.generacion = generacion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.duracion = duracion;
        this.observaciones = observaciones;
        this.obligatoria = obligatoria;
        this.lugar = lugar;
        this.estado = estado;
        this.temas = temas;
        this.resoluciones = resoluciones;
        this.habilitarBotonPanel = habilitarBtnEfectuar;
    }
    
    
}
