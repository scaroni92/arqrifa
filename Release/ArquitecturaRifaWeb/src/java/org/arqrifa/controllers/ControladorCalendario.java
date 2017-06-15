package org.arqrifa.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.rest.RecursoReuniones;
import org.arqrifa.viewmodels.VMListadoReuniones;

//Acceso: Usuarios
@WebServlet(name = "ControladorCalendario", urlPatterns = {"/calendario"})
public class ControladorCalendario extends Controlador {

    public void index_get() {
        VMListadoReuniones vm = (VMListadoReuniones) cargarModelo(new VMListadoReuniones());
        List<DTReunion> reuniones;
        try {
            reuniones = new RecursoReuniones().listar(usuario.getGeneracion());

            //TODO
            if (vm.getFiltro().equals(DTReunion.ESTADO_PENDIENTE) || vm.getFiltro().equals(DTReunion.ESTADO_FINALIZADA)) {
                vm.setReuniones(reuniones.stream().filter(reunion -> reunion.getEstado().equalsIgnoreCase(vm.getFiltro())).collect(Collectors.toList()));
            }else {
                vm.setReuniones(reuniones);
            }
            
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/calendario.jsp", vm);
    }
}
