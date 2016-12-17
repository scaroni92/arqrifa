package org.arqrifa.datatypes;

import java.util.ArrayList;
import java.util.List;

public class DTEncuesta {
    private int id;
    private String titulo;
    private String estado;
    private int duracion;
    private List<DTPropuesta> propuestas;

    public DTEncuesta(int id, String titulo, String estado, int duracion, List<DTPropuesta> propuestas) {
        this.id = id;
        this.titulo = titulo;
        this.estado = estado;
        this.duracion = duracion;
        this.propuestas = propuestas;
    }

    public DTEncuesta() {
        this(0, "", "", 0, new ArrayList());
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEstado() {
        return estado;
    }

    public int getDuracion() {
        return duracion;
    }

    public List<DTPropuesta> getPropuestas() {
        return propuestas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setPropuestas(List<DTPropuesta> propuestas) {
        this.propuestas = propuestas;
    }
    
    
}