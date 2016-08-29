package arq.prototipo.datatypes;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DTReunion {

    private int id;
    private Date fecha;
    private boolean obligatoria;
    private int generacion;

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
    

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }
//</editor-fold>
    
    public DTReunion(int id, Date fecha,int generacion, boolean obligatoria) {
        setId(id);
        setFecha(fecha);
        setObligatoria(obligatoria);
        setGeneracion(generacion);
    }

    public DTReunion() {
        this(0, new Date(),0, false);
    }

}
