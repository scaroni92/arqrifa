package arq.prototipo.datatypes;

import java.util.Date;

public class DTReunion implements java.io.Serializable {

    private int id;
    private Date fecha;
    private boolean obligatoria;

    //<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public int getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public boolean isObligatoria() {
        return obligatoria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setObligatoria(boolean obligatoria) {
        this.obligatoria = obligatoria;
    }
    //</editor-fold>

    public DTReunion(int id, Date fecha, boolean obligatoria) {
        setId(id);
        setFecha(fecha);
        setObligatoria(obligatoria);
    }

    public DTReunion() {
        this(0, new Date(), false);
    }

}
