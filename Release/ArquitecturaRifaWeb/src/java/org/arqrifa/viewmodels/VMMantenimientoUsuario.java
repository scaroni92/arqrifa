
package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;

public class VMMantenimientoUsuario extends ViewModel {

    private String ci;
    private String nombre;
    private String apellido;
    private String contrasena;
    private String email;
    private String rol;
    private String generacion;
    private String inasistencias;
    private List<DTGeneracion> generaciones;

    public VMMantenimientoUsuario(String ci, String nombre, String apellido, String contrasena, String email, String rol, String generacion, String inasistencias, List<DTGeneracion> generaciones, String mensaje) {
        super(mensaje);
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.email = email;
        this.rol = rol;
        this.generacion = generacion;
        this.inasistencias = inasistencias;
        this.generaciones = generaciones;
    }

    public VMMantenimientoUsuario() {
        this("", "", "", "", "", "", "", "", new ArrayList<>(), "");
    }

    public String getCi() {
        return ci;
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

    public String getRol() {
        return rol;
    }

    public String getGeneracion() {
        return generacion;
    }

    public String getInasistencias() {
        return inasistencias;
    }

    public List<DTGeneracion> getGeneraciones() {
        return generaciones;
    }

    public void setCi(String ci) {
        this.ci = ci;
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

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setGeneracion(String generacion) {
        this.generacion = generacion;
    }

    public void setInasistencias(String inasistencias) {
        this.inasistencias = inasistencias;
    }

    public void setGeneraciones(List<DTGeneracion> generaciones) {
        this.generaciones = generaciones;
    }
    
    

}
