package org.arqrifa.controllers;

import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.rest.RecursoReuniones;
import org.arqrifa.rest.RecursoSolicitudes;
import org.arqrifa.viewmodels.VMIndex;
import org.arqrifa.viewmodels.VMUsuario;
import org.arqrifa.viewmodels.VMVerificacion;

@WebServlet(name = "ControladorIndex", urlPatterns = {"/index"})
public class ControladorIndex extends Controlador {
    
    public void index_get() {
        VMIndex vm = new VMIndex();
        try {
            if (!usuario.isAdmin()) {
                vm.setProximaReunion(new RecursoReuniones().siguiente(usuario.getGeneracion()));
                vm.setUltimaReunion(new RecursoReuniones().ultimaFinalizada(usuario.getGeneracion()));
            }
            if (usuario.isEncargado()) {
                DTReunion reunionActiva = new RecursoReuniones().buscarActual(usuario.getGeneracion());
                if (!reunionActiva.isFinalizada()) {
                    sesion.setAttribute("reunionActiva", reunionActiva);
                }
            }
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista(usuario == null ? "login.jsp" : usuario.getRol().toLowerCase() + "/index.jsp", vm);
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
    
     public void perfil_get() {
        mostrarVista("usuarios/detalles.jsp", new VMUsuario(usuario, ""));
    }
}
