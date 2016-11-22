package org.arqrifa.datatypes;

import java.util.Date;

public class DTSolicitud {

    private int codigo;
    private Date fecha;
    private boolean verificada;
    DTUsuario usuario;

    //<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public int getCodigo() {
        return codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public boolean isVerificada() {
        return verificada;
    }

    public DTUsuario getUsuario() {
        return usuario;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setVerificada(boolean verificada) {
        this.verificada = verificada;
    }

    public void setUsuario(DTUsuario usuario) {
        this.usuario = usuario;
    }

    //</editor-fold>
    
    public DTSolicitud(int codigo, Date fecha, boolean verificada, DTUsuario usuario) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.verificada = verificada;
        this.usuario = usuario;
    }

    public DTSolicitud() {
        this(0, null, false, null);
    }

}
