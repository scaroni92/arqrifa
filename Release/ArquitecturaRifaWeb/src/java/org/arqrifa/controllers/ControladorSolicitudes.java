package org.arqrifa.controllers;

import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.RecursoSolicitudes;
import org.arqrifa.viewmodels.VMListadoSolicitudes;
import org.arqrifa.viewmodels.VMUsuario;

//Acceso: Encargados
@WebServlet(name = "ControladorSolicitudes", urlPatterns = {"/solicitudes"})
public class ControladorSolicitudes extends Controlador {

    private final RecursoSolicitudes recurso = new RecursoSolicitudes();

    public void index_get() {
        VMListadoSolicitudes vm = new VMListadoSolicitudes();
        try {
            vm.setSolicitudes(recurso.listar(usuario.getGeneracion()));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/solicitudes.jsp", vm);
    }

    public void detalles_get() {
        VMUsuario vm = new VMUsuario();
        try {
            
            DTUsuario usu = recurso.buscar(request.getParameter("ci")).getUsuario();

            if (usu.getGeneracion() != usu.getGeneracion()) {
                response.sendError(403);
                return;
            }

            vm.setUsuario(usu);

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/detalles.jsp", vm);
    }

    public void confirmar_get() {
        try {
            //pasar id
            recurso.confirmar(recurso.buscar(request.getParameter("ci")));
            sesion.setAttribute("mensaje", "Solicitud confirmada exitosamente");
        } catch (Exception e) {
            sesion.setAttribute("mensaje", e.getMessage());
        }
        index_get();
    }

    public void rechazar_get() {
        try {
            recurso.eliminar(request.getParameter("ci"));
            sesion.setAttribute("mensaje", "Solicitud rechazada exitosamente");
        } catch (Exception e) {
            sesion.setAttribute("mensaje", e.getMessage());
        }
        index_get();
    }
}
