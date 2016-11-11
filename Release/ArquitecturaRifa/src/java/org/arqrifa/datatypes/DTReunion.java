package org.arqrifa.datatypes;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DTReunion {

    private int id;
    private String titulo;
    private String descripcion;
    private String resoluciones;
    private Date fecha;
    private boolean obligatoria;
    private int generacion;
    private String estado;
    private String lugar;

    //<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public int getId() {
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

    public Date getFecha() {
        return fecha;
    }

    public boolean isObligatoria() {
        return obligatoria;
    }

    public int getGeneracion() {
        return generacion;
    }

    public String getEstado() {
        return estado;
    }

    public String getLugar() {
        return lugar;
    }

    public void setId(int id) {
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

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setObligatoria(boolean obligatoria) {
        this.obligatoria = obligatoria;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    //</editor-fold>

    public DTReunion(int id, String titulo, String descripcion, String resoluciones, Date fecha, boolean obligatoria, int generacion, String estado, String lugar) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.resoluciones = resoluciones;
        this.fecha = fecha;
        this.obligatoria = obligatoria;
        this.generacion = generacion;
        this.estado = estado;
        this.lugar = lugar;
    }


    public DTReunion() {
        this(0, "n/d", "n/d", "n/d", new Date(), false, 0, "n/d", "n/d");
    }

}
