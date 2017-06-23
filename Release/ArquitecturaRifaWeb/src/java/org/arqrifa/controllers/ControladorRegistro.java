package org.arqrifa.controllers;

import java.util.Date;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.RecursoGeneraciones;
import org.arqrifa.rest.RecursoSolicitudes;
import org.arqrifa.viewmodels.VMMantenimientoUsuario;

@WebServlet(name = "ControladorRegistro", urlPatterns = {"/registro"})
public class ControladorRegistro extends Controlador {
    
    public void index_get() {
        VMMantenimientoUsuario vm = new VMMantenimientoUsuario();
        try {
            vm.setGeneraciones(new RecursoGeneraciones().listar());
        } catch (Exception e) {
            vm.setMensaje("Error al cargar las generaciones");
        }
        mostrarVista("registro.jsp", vm);
    }

    public void index_post() {
        VMMantenimientoUsuario vm = (VMMantenimientoUsuario) cargarModelo(new VMMantenimientoUsuario());
        try {
            vm.setGeneraciones(new RecursoGeneraciones().listar());

            verificarViewModel(vm);
            
            new RecursoSolicitudes().agregar(getSolicitudFromViewModel(vm));

            sesion.setAttribute("mensaje", "Registro exitoso! <br>Se ha enviado un mail de verificación a tu correo electrónico.");
            response.sendRedirect("login");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("registro.jsp", vm);
        }
    }

    private DTSolicitud getSolicitudFromViewModel(VMMantenimientoUsuario vm) throws NumberFormatException {
        DTUsuario dtUsuario = new DTUsuario(Integer.parseInt(vm.getCi()), vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), "", Integer.parseInt(vm.getGeneracion()), 0);
        DTSolicitud solicitud = new DTSolicitud(0, new Date(), false, dtUsuario);
        return solicitud;
    }

    private void verificarViewModel(VMMantenimientoUsuario vm) throws Exception {
        if (vm.getCi().isEmpty() || vm.getNombre().isEmpty() || vm.getApellido().isEmpty() || vm.getEmail().isEmpty() || vm.getContrasena().isEmpty() || vm.getGeneracion().isEmpty()) {
            throw new Exception("Completa todos los campos obligatorios.");
        }
    }
}
