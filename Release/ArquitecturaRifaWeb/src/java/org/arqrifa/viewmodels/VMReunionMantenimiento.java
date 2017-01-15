package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class VMReunionMantenimiento extends ViewModel {

    private String id;
    private String generacion;
    private String titulo;
    private String descripcion;
    private String fecha;
    private String hora;
    private String duracion;
    private boolean obligatoria;
    private String lugar;
    private String observaciones;
    private String estado;
    private List<String> temas;
    private List<String> resoluciones;

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

    public boolean isObligatoria() {
        return obligatoria;
    }

    public String getLugar() {
        return lugar;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getEstado() {
        return estado;
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

    public void setObligatoria(boolean obligatoria) {
        this.obligatoria = obligatoria;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setTemas(List<String> temas) {
        this.temas = temas;
    }

    public void setResoluciones(List<String> resoluciones) {
        this.resoluciones = resoluciones;
    }
    //</editor-fold>

    public VMReunionMantenimiento(String id, String generacion, String titulo, String descripcion, String fecha, String hora, String duracion, boolean obligatoria, String lugar, String observaciones, String estado, List<String> temas, List<String> resoluciones, String mensaje) {
        super(mensaje);
        this.id = id;
        this.generacion = generacion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.duracion = duracion;
        this.obligatoria = obligatoria;
        this.lugar = lugar;
        this.observaciones = observaciones;
        this.estado = estado;
        this.temas = temas;
        this.resoluciones = resoluciones;
    }

    public VMReunionMantenimiento() {
        this("", "", "", "", "", "", "", false, "", "", "", new ArrayList(), new ArrayList(), "");
    }
    
    

}
