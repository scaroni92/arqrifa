package org.arqrifa.servlets;

import java.text.SimpleDateFormat;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMReunion;

public class ControladorReuniones extends Controlador {

    public void ver_get() {
        VMReunion vm = new VMReunion();
        try {
            int id;
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (Exception e) {
                throw new Exception("ID inválido.");
            }

            DTReunion reunion = cliente.buscarReunion(id);

            if (reunion == null) {
                throw new Exception("Reunión no encontrada");
            }

            if (reunion.getGeneracion() != ((DTUsuario) sesion.getAttribute("usuario")).getGeneracion()) {
                throw new Exception("La reunión no pertenece a su generación");
            }
            vm.setId(String.valueOf(id));
            vm.setTitulo(reunion.getTitulo());
            vm.setDescripcion(reunion.getDescripcion());
            vm.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(reunion.getFecha()));
            vm.setHora(new SimpleDateFormat("HH:mm").format(reunion.getFecha()));
            vm.setObligatoria(reunion.isObligatoria());
            vm.setGeneracion(String.valueOf(reunion.getGeneracion()));
            vm.setEstado(reunion.getEstado());
            vm.setLugar(reunion.getLugar());
            //vm.setActiva(reunion.getFecha().after(new Date()));
            vm.setActiva(true);

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Vistas/Reunion/ver.jsp", vm);
    }

    public void iniciar_link_post() {
        VMReunion vm = new VMReunion();
        try {
            int id;
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (Exception e) {
                throw new Exception("El ID no es válido.");
            }

            DTReunion reunion = cliente.buscarReunion(id);

            vm.setId(String.valueOf(id));
            vm.setTitulo(reunion.getTitulo());
            vm.setDescripcion(reunion.getDescripcion());
            vm.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(reunion.getFecha()));
            vm.setHora(new SimpleDateFormat("HH:mm").format(reunion.getFecha()));
            vm.setObligatoria(reunion.isObligatoria());
            vm.setGeneracion(String.valueOf(reunion.getGeneracion()));
            vm.setEstado(reunion.getEstado());
            vm.setLugar(reunion.getLugar());
            

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Vistas/Reunion/iniciar.jsp", vm);
    }

    public void iniciar_post() {
        VMReunion vm = (VMReunion) cargarModelo(new VMReunion());
        try {
            int id;
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (Exception e) {
                throw new Exception("ID de reunión no válido.");
            }

            DTReunion r = cliente.buscarReunion(id);
            cliente.iniciarReunion(r);
            vm.setMensaje("reunión iniciada");

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Vistas/Reunion/iniciar.jsp", vm);
    }

}
