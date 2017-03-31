package arqrifa.org.arquitecturarifamobile.datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DTEncuesta implements Serializable{

    private int id;
    private String titulo;
    private int duracion;
    private boolean habilitada;
    private List<DTPropuesta> propuestas;

    public DTEncuesta(int id, String titulo, int duracion, boolean habilitada, List<DTPropuesta> propuestas) {
        this.id = id;
        this.titulo = titulo;
        this.duracion = duracion;
        this.habilitada = habilitada;
        this.propuestas = propuestas;
    }

    public DTEncuesta() {
        this(0, "", 0, false, new ArrayList());
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracion() {
        return duracion;
    }

    public boolean isHabilitada() {
        return habilitada;
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

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setHabilitada(boolean habilitada) {
        this.habilitada = habilitada;
    }

    public void setPropuestas(List<DTPropuesta> propuestas) {
        this.propuestas = propuestas;
    }

}
