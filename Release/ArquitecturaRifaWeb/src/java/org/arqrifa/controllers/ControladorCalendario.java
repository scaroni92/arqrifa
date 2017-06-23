package org.arqrifa.controllers;

import java.util.List;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.rest.RecursoReuniones;
import org.arqrifa.viewmodels.VMListadoReuniones;

@WebServlet(name = "ControladorCalendario", urlPatterns = {"/calendario"})
public class ControladorCalendario extends Controlador {

    public void index_get() {
        VMListadoReuniones vm = (VMListadoReuniones) cargarModelo(new VMListadoReuniones());
        List<DTReunion> reuniones;
        try {
            reuniones = new RecursoReuniones().listar(usuario.getGeneracion());

            if (vm.getFiltro().equalsIgnoreCase(DTReunion.ESTADO_PENDIENTE) || vm.getFiltro().equalsIgnoreCase(DTReunion.ESTADO_FINALIZADA)) {
                for (DTReunion r : reuniones) {
                    if (r.getEstado().equalsIgnoreCase(vm.getFiltro())) {
                        vm.getReuniones().add(r);
                    }
                }
            }else {
                vm.setReuniones(reuniones);
            }
            
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/calendario.jsp", vm);
    }
}
