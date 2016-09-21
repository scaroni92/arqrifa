package org.arqrifa.logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;

public class Reunion {

    private int id;
    private String titulo;
    private String descripcion;
    private String resoluciones;
    private Date fecha;
    private boolean obligatoria;
    private int generacion;
    private String estado;
    private List<Usuario> lista;

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

    public List<Usuario> getLista() {
        return lista;
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

    //</editor-fold>
    
    public Reunion() {
        this(0, "n/d", "n/d", "n/d", new Date(), false, 0, "n/d");
    }

    public Reunion(int id, String titulo, String descripcion, String resoluciones, Date fecha, boolean obligatoria, int generacion, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.resoluciones = resoluciones;
        this.fecha = fecha;
        this.obligatoria = obligatoria;
        this.generacion = generacion;
        this.estado = estado;
        lista = new ArrayList();
    }

    public Reunion(DTReunion dt) {
        this(dt.getId(), dt.getTitulo(), dt.getDescripcion(), dt.getResoluciones(), dt.getFecha(), dt.isObligatoria(), dt.getGeneracion(), dt.getEstado());
    }

    public DTReunion getDataType() {
        return new DTReunion(id, titulo, descripcion, resoluciones, fecha, obligatoria, generacion, estado);
    }

    public void marcarAsistencia(DTUsuario usuario) throws Exception {
        for (Usuario u : this.getLista()) {
            if (u.getCi() == usuario.getCi()) {
                throw new Exception("El estudiante con CI: " + usuario.getCi() + " marcó su asistencia previamente.");
            }
        }
        lista.add(new Usuario(usuario));
    }
}
