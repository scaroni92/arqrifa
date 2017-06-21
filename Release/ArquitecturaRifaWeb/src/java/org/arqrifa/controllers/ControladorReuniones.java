package org.arqrifa.controllers;

import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.RecursoReuniones;
import org.arqrifa.viewmodels.VMListadoReuniones;
import org.arqrifa.viewmodels.VMReunion;

@WebServlet(name = "ControladorReuniones", urlPatterns = {"/reuniones"})
public class ControladorReuniones extends Controlador {
    
    private final RecursoReuniones recurso = new RecursoReuniones();
    
    public void index_get() {
        VMListadoReuniones vm = (VMListadoReuniones) cargarModelo(new VMListadoReuniones());
        try {
            vm.setReuniones(recurso.listar(usuario.getGeneracion()));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/listado.jsp", vm);
    }
    
    public void detalles_get() {
        VMReunion vm = new VMReunion();
        try {
            DTReunion reunion = recurso.buscar(request.getParameter("id"));
            
            if (reunion == null) {
                response.sendError(404);
                return;
            }
            
            if (!usuario.getRol().equals(DTUsuario.ROL_ADMIN) && usuario.getGeneracion() != reunion.getGeneracion()) {
                response.sendError(403);
                return;
            }
            
            vm.setReunion(reunion);
            
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/detalles.jsp", vm);
    }
    
    public void encuesta_get() {
        //todo
        DTReunion reunion = null;
        try {
            reunion = recurso.buscar(request.getParameter("id"));
            if (reunion == null) {
                throw new Exception("Reunion no encontrada");
            }
            
            if (reunion.getGeneracion() != usuario.getGeneracion()) {
                response.sendError(403);
                return;
            }
            mostrarVista("encuestas/detalles.jsp", new VMReunion(reunion, ""));
        } catch (Exception e) {
            mostrarVista("reuniones/detalles.jsp", new VMReunion(reunion, e.getMessage()));
        }
    }
}
