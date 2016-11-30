package org.arqrifa.viewmodels;

import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;

public class VMUsuarioMantenimiento extends ViewModel {

    private String ci;
    private String generacion;
    private String nombre;
    private String apellido;
    private String contrasena;
    private String email;
    private List<DTGeneracion> generaciones;

    public String getCi() {
        return ci;
    }

    public String getGeneracion() {
        return generacion;
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

    public List<DTGeneracion> getGeneraciones() {
        return generaciones;
    }

    public void setGeneraciones(List<DTGeneracion> generaciones) {
        this.generaciones = generaciones;
    }
    

    public void setCi(String ci) {
        this.ci = ci;
    }

    public void setGeneracion(String generacion) {
        this.generacion = generacion;
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

    public VMUsuarioMantenimiento(String ci, String generacion, String nombre, String apellido, String contrasena, String email, String mensaje) {
        super(mensaje);
        this.ci = ci;
        this.generacion = generacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.email = email;
    }

    public VMUsuarioMantenimiento() {
        this("", "", "", "", "", "", "");
    }

    public VMUsuarioMantenimiento(String mensaje) {
        this("", "", "", "", "", "", mensaje);
    }

}
