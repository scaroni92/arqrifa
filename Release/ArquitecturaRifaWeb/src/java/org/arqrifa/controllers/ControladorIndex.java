package org.arqrifa.controllers;

import java.util.Date;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.RecursoGeneraciones;
import org.arqrifa.rest.RecursoReuniones;
import org.arqrifa.rest.RecursoSolicitudes;
import org.arqrifa.rest.RecursoUsuarios;
import org.arqrifa.viewmodels.VMIndex;
import org.arqrifa.viewmodels.VMMantenimientoUsuario;
import org.arqrifa.viewmodels.VMVerificacion;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorIndex", urlPatterns = {"/index"})
public class ControladorIndex extends Controlador {
    
    public void index_get() {
        VMIndex vm = new VMIndex();
        try {
            if (!usuario.getRol().equals(DTUsuario.ADMIN)) {
                vm.setProximaReunion(new RecursoReuniones().siguiente(usuario.getGeneracion()));
                vm.setUltimaReunion(new RecursoReuniones().ultimaFinalizada(usuario.getGeneracion()));
            }
            
            if (usuario.getRol().equals(DTUsuario.ENCARGADO)) {
                vm.setSolicitudes(new RecursoSolicitudes().listar(usuario.getGeneracion()));
            }
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista(usuario == null ? "login.jsp" : usuario.getRol().toLowerCase() + "/index.jsp", vm);
    }
    
    public void ingresar_get() {
        mostrarVista("login.jsp");
    }
    
    public void ingresar_post() {
        try {
            usuario = new RecursoUsuarios().login(request.getParameter("ci"), request.getParameter("pass"));
            if (usuario == null) {
                throw new Exception("Usuario o contraseña incorrectos");
            }
            sesion.setAttribute("usuario", usuario);
            //mostrarVista(usuario.getRol().toLowerCase() + "/index.jsp");
            index_get();
        } catch (Exception ex) {
            mostrarVista("login.jsp", new ViewModel(ex.getMessage()));
        }
    }
    
    public void registro_get() {
        VMMantenimientoUsuario vm = new VMMantenimientoUsuario();
        try {
            vm.setGeneraciones(new RecursoGeneraciones().listar());
        } catch (Exception e) {
            vm.setMensaje("Error al cargar las generaciones");
        }
        mostrarVista("registro.jsp", vm);
        
    }
    
    public void registro_post() {
        VMMantenimientoUsuario vm = (VMMantenimientoUsuario) cargarModelo(new VMMantenimientoUsuario());
        try {
            vm.setGeneraciones(new RecursoGeneraciones().listar());
            
            if (vm.getCi().isEmpty() || vm.getNombre().isEmpty() || vm.getApellido().isEmpty() || vm.getEmail().isEmpty() || vm.getContrasena().isEmpty() || vm.getGeneracion().isEmpty()) {
                throw new Exception("Completa todos los campos obligatorios.");
            }
            
            DTUsuario dtUsuario = new DTUsuario(Integer.parseInt(vm.getCi()), vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), "", Integer.parseInt(vm.getGeneracion()), 0);
            new RecursoSolicitudes().agregar(new DTSolicitud(0, new Date(), false, dtUsuario));
            mostrarVista("login.jsp", new ViewModel("Registro exitoso! <br>Se ha enviado un mail de verificación a tu correo electrónico."));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("registro.jsp", vm);
        }
    }
    
    public void verificar_get() {
        VMVerificacion vm;
        try {
            new RecursoSolicitudes().verificar(request.getParameter("codigo"));
            vm = new VMVerificacion(true, "Ahora solo debes esperar a que sea aprobada por el encargado.", "¡Solicitud verificada exitosamente!");
        } catch (Exception ex) {
            vm = new VMVerificacion(false, "Quizas la solicitud fue rechazada.", "Código de verificación incorrecto");
        }
        mostrarVista("verificar.jsp", vm);
    }
    
}
