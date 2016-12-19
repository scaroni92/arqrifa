package org.arqrifa.servlets;

import java.util.Arrays;
import java.util.List;
import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.viewmodels.VMCrearEncuesta;
import org.arqrifa.viewmodels.VMEncuesta;

public class ControladorEncuesta extends Controlador {

    public void ver_get() {
        VMEncuesta vm = new VMEncuesta();
        try {

            vm.setReunionId(request.getParameter("reunion_id"));
            vm.setEncuesta(cliente.buscarReunion(Integer.parseInt(request.getParameter("reunion_id"))).getEncuesta());
        } catch (Exception e) {
        }
        mostrarVista("Encuesta/ver.jsp", vm);
    }

    public void agregar_get() {
        VMCrearEncuesta vm = new VMCrearEncuesta();
        try {
            sesion.setAttribute("reunion", cliente.buscarReunion(Integer.parseInt(request.getParameter("id"))));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encuesta/agregar.jsp", vm);
    }

    public void agregar_propuesta_post() {
        VMCrearEncuesta vm = (VMCrearEncuesta) cargarModelo(new VMCrearEncuesta());
        try {
            if (request.getParameter("pregunta").isEmpty()) {
                throw new Exception("Ingrese la pregunta o título de la propuesta");
            }
            String pRespuestas = request.getParameter("respuestas");
            if (pRespuestas.isEmpty()) {
                throw new Exception("Ingrese las respuestas de la pregunta");
            }

            List<DTPropuesta> propuestas = (((DTReunion) sesion.getAttribute("reunion")).getEncuesta()).getPropuestas();
            propuestas.add(new DTPropuesta(0, request.getParameter("pregunta"), Arrays.asList(pRespuestas.split("\n"))));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encuesta/agregar.jsp", vm);
    }

    public void crear_encuesta_post() {
        VMCrearEncuesta vm = (VMCrearEncuesta) cargarModelo(new VMCrearEncuesta());
        try {
            DTReunion reunion = (DTReunion) sesion.getAttribute("reunion");

            reunion.getEncuesta().setTitulo(vm.getTitulo());
            reunion.getEncuesta().setDuracion(Integer.parseInt(vm.getDuracion()));

            cliente.crearEncuesta(reunion);
            vm = new VMCrearEncuesta();
            vm.setMensaje("Encuesta creada exitosamente.");
            sesion.setAttribute("Reunion", new DTReunion());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encuesta/agregar.jsp", vm);
    }

    public void iniciar_votacion_post() {
        VMEncuesta vm = new VMEncuesta();
        try {
            vm.setReunionId(request.getParameter("reunion_id"));
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(vm.getReunionId()));
            cliente.iniciarVotacion(reunion);
            vm.setEncuesta(reunion.getEncuesta());
            vm.setMensaje("Votación iniciada con éxito.");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encuesta/ver.jsp", vm);
    }
}
