package org.arqrifa.servlets;

import java.util.Arrays;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.viewmodels.VMCrearEncuesta;

public class ControladorEncuesta extends Controlador {

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
            String pregunta = request.getParameter("pregunta");
            if (pregunta.isEmpty()) {
                throw new Exception("Ingrese la pregunta o título de la propuesta");
            }
            String pRespuestas = request.getParameter("respuestas");
            if (pRespuestas.isEmpty()) {
                throw new Exception("Ingrese las respuestas de la pregunta");
            }
            
            DTReunion reunion = (DTReunion) sesion.getAttribute("reunion");
            reunion.getEncuesta().getPropuestas().add(new DTPropuesta(0, pregunta,  Arrays.asList(pRespuestas.split("\n"))));
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
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encuesta/agregar.jsp", vm);
    }
}
