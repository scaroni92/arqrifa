package org.arqrifa.datatypes;

import java.util.Date;

public class DTSolicitud {

    private int ci;
    private int generacion;
    private Date fecha;
    private String nombre;
    private String apellido;
    private String contrasena;
    private String email;
    private boolean verificada;

    //<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public int getCi() {
        return ci;
    }

    public int getGeneracion() {
        return generacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getEmail() {
        return email;
    }

    public boolean isVerificada() {
        return verificada;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerificada(boolean verificada) {
        this.verificada = verificada;
    }

    //</editor-fold>
    public DTSolicitud(int ci, int generacion, Date fecha, String nombre, String apellido, String contrasena, String email, boolean verificada) {
        this.ci = ci;
        this.generacion = generacion;
        this.fecha = fecha;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.email = email;
        this.verificada = verificada;
    }

    public DTSolicitud() {
        this(0, 0, new Date(), "n/d", "n/d", "n/d", "n/d", false);
    }

}
