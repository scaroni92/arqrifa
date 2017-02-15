package org.arqrifa.controllers;

import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMListaSolicitudes;
import org.arqrifa.viewmodels.VMUsuario;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorSolicitud", urlPatterns = {"/solicitud"})
public class ControladorSolicitud extends Controlador {

    public void detalles_get() {
        VMUsuario vm = new VMUsuario();
        try {
            DTUsuario dtUsuario = cliente.buscarSolicitud(Integer.parseInt(request.getParameter("ci"))).getUsuario();

            if (this.usuario.getGeneracion() == dtUsuario.getGeneracion()) {
                vm.setUsuario(dtUsuario);
            }

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/detalles.jsp", vm);
    }

    public void confirmar_get() {
        try {
            cliente.confirmarSolicitud(cliente.buscarSolicitud(Integer.parseInt(request.getParameter("ci"))));
            mostrarVista("usuarios/solicitudes.jsp", new VMListaSolicitudes(cliente.listarSolicitudes(usuario.getGeneracion()), "Solicitud confirmada exitosamente."));
        } catch (Exception e) {
            try {
                mostrarVista("usuarios/solicitudes.jsp", new VMListaSolicitudes(cliente.listarSolicitudes(usuario.getGeneracion()), e.getMessage()));
            } catch (Exception ex) {
                mostrarVista("encargado/index.jsp", new ViewModel(e.getMessage()));
            }
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
