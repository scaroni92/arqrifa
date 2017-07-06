package org.arqrifa.datatypes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DTReunion {

    public static String ESTADO_PENDIENTE = "Pendiente";
    public static String ESTADO_INICIADA = "Iniciada";
    public static String ESTADO_LISTADO = "Listado";
    public static String ESTADO_VOTACION = "Votacion";
    public static String ESTADO_FINALIZADA = "Finalizada";

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
    private DTEncuesta encuesta;
    private List<String> temas;
    private List<String> resoluciones;
    private List<DTUsuario> participantes;

    public DTReunion() {
        this(0, 0, "", "", null, 0, false, "", "", "", null, new ArrayList(), new ArrayList(), new ArrayList());
    }

    public DTReunion(int id, int generacion, String titulo, String descripcion, Date fecha, int duracion, boolean obligatoria, String lugar, String observaciones, String estado, DTEncuesta encuesta, List<String> temas, List<String> resoluciones, List<DTUsuario> participantes) {
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
        this.encuesta = encuesta;
        this.temas = temas;
        this.resoluciones = resoluciones;
        this.participantes = participantes;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
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

    public List<DTUsuario> getParticipantes() {
        return participantes;
    }

    public DTEncuesta getEncuesta() {
        return encuesta;
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

    public void setParticipantes(List<DTUsuario> participantes) {
        this.participantes = participantes;
    }

    public void setEncuesta(DTEncuesta encuesta) {
        this.encuesta = encuesta;
    }
    //</editor-fold>

    public boolean isPendiente() {
        return estado.equals(DTReunion.ESTADO_PENDIENTE);
    }

    public boolean isIniciada() {
        return estado.equals(DTReunion.ESTADO_INICIADA);
    }

    public boolean isListado() {
        return estado.equals(DTReunion.ESTADO_LISTADO);
    }

    public boolean isVotacion() {
        return estado.equals(DTReunion.ESTADO_VOTACION);
    }

    public boolean isFinalizada() {
        return estado.equals(DTReunion.ESTADO_FINALIZADA);
    }
}
