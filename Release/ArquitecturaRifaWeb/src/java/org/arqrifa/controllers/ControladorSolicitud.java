package org.arqrifa.controllers;
//ENCARGADO
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.viewmodels.VMListaSolicitudes;

@WebServlet(name = "ControladorSolicitud", urlPatterns = {"/solicitud"})
public class ControladorSolicitud extends Controlador {
    
    public void confirmar_get() {
        try {
            cliente.confirmarSolicitud(cliente.buscarSolicitud(Integer.parseInt(request.getParameter("ci"))));
            mostrarVista("usuarios/solicitudes.jsp", new VMListaSolicitudes(cliente.listarSolicitudes(usuario.getGeneracion()), "Solicitud confirmada exitosamente."));
        } catch (Exception e) {
            mostrarVista("usuarios/solicitudes.jsp", new VMListaSolicitudes(new ArrayList(), e.getMessage()));
        }

    }

    public void rechazar_get() {
        VMListaSolicitudes vm = new VMListaSolicitudes();
        try {
            cliente.rechazarSolicitud(cliente.buscarSolicitud(Integer.parseInt(request.getParameter("ci"))));
            vm = new VMListaSolicitudes(cliente.listarSolicitudes(usuario.getGeneracion()), "Solicitud rechazada exitosamente.");
        } catch (NumberFormatException e) {
            vm.setMensaje("Ingrese una cédula válida.");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/solicitudes.jsp", vm);
    }
}
