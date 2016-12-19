package org.arqrifa.datatypes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DTReunion {

    private int id;
    private int generacion;
    private String titulo;
    private String descripcion;
    private Date fecha;
    private int duracion;
    private boolean obligatoria;
    private String lugar;
    private String observaciones;
    private String estado;
    private List<String> temas;
    private List<String> resoluciones;
    private DTEncuesta encuesta;

    public DTEncuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(DTEncuesta encuesta) {
        this.encuesta = encuesta;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public int getId() {
        return id;
    }

    public int getGeneracion() {
        return generacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getDuracion() {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setDuracion(int duracion) {
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

    public DTReunion(int id, int generacion, String titulo, String descripcion, Date fecha, int duracion, boolean obligatoria, String lugar, String observaciones, String estado, List<String> temas, List<String> resoluciones, DTEncuesta encuesta) {
        this.id = id;
        this.generacion = generacion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.duracion = duracion;
        this.obligatoria = obligatoria;
        this.lugar = lugar;
        this.observaciones = observaciones;
        this.estado = estado;
        this.temas = temas;
        this.resoluciones = resoluciones;
        this.encuesta = encuesta;
    }
    
    public DTReunion(int id, int generacion, String titulo, String descripcion, Date fecha, int duracion, boolean obligatoria, String lugar, String observaciones, String estado, List<String> temas, List<String> resoluciones) {
        this(id, generacion, titulo, descripcion, fecha, duracion, obligatoria, lugar, observaciones, estado, temas, resoluciones, new DTEncuesta());
    }

    public DTReunion() {
        this(0, 0, "", "", null, 0, false, "", "", "", null, null);
    }

}
