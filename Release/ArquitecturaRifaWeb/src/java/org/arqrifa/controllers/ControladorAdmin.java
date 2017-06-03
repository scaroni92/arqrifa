package org.arqrifa.controllers;

import javax.servlet.annotation.WebServlet;
import org.arqrifa.rest.RecursoReuniones;
import org.arqrifa.viewmodels.VMListadoReuniones;

@WebServlet(name = "ControladorAdmin", urlPatterns = {"/admin"})
public class ControladorAdmin extends Controlador {
    
    public void listar_reuniones_get() {
        VMListadoReuniones vm = new VMListadoReuniones();
        try {
            vm.setReuniones(new RecursoReuniones().listar(0));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/listado.jsp", vm);
    }
}
