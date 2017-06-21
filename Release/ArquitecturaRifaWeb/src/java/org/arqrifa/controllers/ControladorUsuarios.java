package org.arqrifa.controllers;

import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.RecursoUsuarios;
import org.arqrifa.viewmodels.VMListadoUsuarios;
import org.arqrifa.viewmodels.VMUsuario;
//Acceso: admin y encargado

@WebServlet(name = "ControladorUsuarios", urlPatterns = {"/usuarios"})
public class ControladorUsuarios extends Controlador {

    private final RecursoUsuarios recurso = new RecursoUsuarios();

    public void index_get() {
        VMListadoUsuarios vm = (VMListadoUsuarios)cargarModelo(new VMListadoUsuarios());
        try {
            vm.setUsuarios(recurso.listar(usuario.getGeneracion()));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/listado.jsp", vm);
    }
   
    public void buscar_get() {
        String ci = request.getParameter("ci");
        VMListadoUsuarios vm = new VMListadoUsuarios();
        DTUsuario dtu;
        
        try {

            if (ci.isEmpty()) {
                vm.setUsuarios(recurso.listar(usuario.getGeneracion()));
            } else {
                dtu = recurso.buscar(ci);
                if (usuario.getGeneracion() == dtu.getGeneracion()) {
                    vm.getUsuarios().add(dtu);
                }
            }
            
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/listado.jsp", vm);
    }

    public void detalles_get() {
        VMUsuario vm = new VMUsuario();
        try {
            DTUsuario dtUsuario = recurso.buscar(request.getParameter("ci"));

            if (usuario.getRol().equals(DTUsuario.ROL_ENCARGADO) && usuario.getGeneracion() != dtUsuario.getGeneracion()) {
                response.sendError(403);
                return;
            }

            vm.setUsuario(dtUsuario);
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/detalles.jsp", vm);
    }
}
