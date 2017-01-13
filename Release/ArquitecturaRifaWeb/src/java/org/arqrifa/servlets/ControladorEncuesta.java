package org.arqrifa.servlets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTRespuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTVoto;
import org.arqrifa.viewmodels.VMCrearEncuesta;
import org.arqrifa.viewmodels.VMEncuesta;
import org.arqrifa.viewmodels.ViewModel;

public class ControladorEncuesta extends Controlador {

    public void ver_get() {
        VMEncuesta vm = new VMEncuesta();
        try {
            vm = new VMEncuesta(request.getParameter("reunion_id"), cliente.buscarReunion(Integer.parseInt(request.getParameter("reunion_id"))).getEncuesta());
        } catch (NumberFormatException e) {
            vm.setMensaje("Ingrese un ID de reunión válido");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
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
                throw new Exception("Ingrese la pregunta de la propuesta");
            }
            if (request.getParameter("respuestas").isEmpty()) {
                throw new Exception("Ingrese las respuestas de la pregunta.");
            }

            List<DTRespuesta> respuestas = new ArrayList();
            for (String respuesta : Arrays.asList(request.getParameter("respuestas").split("\n"))) {
                respuestas.add(new DTRespuesta(0, respuesta));
            }

            DTReunion reunion = (DTReunion) sesion.getAttribute("reunion");
            if (reunion.getEncuesta() == null) {
                reunion.setEncuesta(new DTEncuesta());
            }
            DTEncuesta encuesta = ((DTReunion) sesion.getAttribute("reunion")).getEncuesta();
            reunion.getEncuesta().getPropuestas().add(new DTPropuesta(0, request.getParameter("pregunta"), respuestas));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encuesta/agregar.jsp", vm);
    }

    public void agregar_encuesta_post() {
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
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("reunion_id")));
            vm = new VMEncuesta(request.getParameter("reunion_id"), reunion.getEncuesta());
            cliente.iniciarVotacion(reunion);
            vm.setMensaje("Votación iniciada con éxito.");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encuesta/ver.jsp", vm);
    }

    // Buscar mejor solución que utilizar Session
    public void cuestionario_get() {
        ViewModel vm = new ViewModel();
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));
            sesion.setAttribute("encuesta", reunion.getEncuesta());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encuesta/cuestionario.jsp", vm);
    }

    public void buscar_estudiante_post() {
        ViewModel vm = new ViewModel();
        try {
            DTUsuario usuario = cliente.buscarUsuario(Integer.parseInt(request.getParameter("ci")));
            if (usuario.getRol().equals("Estudiante")) {
                sesion.setAttribute("estudiante", usuario);
            }

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encuesta/cuestionario.jsp", vm);
    }

    public void confirmar_voto_post() {
        ViewModel vm = new ViewModel();
        try {
            DTUsuario estudiante = (DTUsuario) sesion.getAttribute("estudiante");
            DTEncuesta encuesta = (DTEncuesta) sesion.getAttribute("encuesta");

            List<DTRespuesta> respuestasEscogidas = new ArrayList();
            for (DTPropuesta propuesta : encuesta.getPropuestas()) {
                String strRespuesta = request.getParameter(String.valueOf(propuesta.getId()));
                respuestasEscogidas.add(new DTRespuesta(Integer.parseInt(strRespuesta), ""));
            }

            cliente.agregarVoto(new DTVoto(estudiante, respuestasEscogidas));

            sesion.setAttribute("estudiante", null);
            sesion.setAttribute("encuesta", null);
            vm.setMensaje("Votación exitosa");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encuesta/cuestionario.jsp", vm);
    }

    public void eliminar_get() {
        mostrarVista("Encuesta/Eliminar.jsp", new VMEncuesta(request.getParameter("id"), null));
    }

    public void eliminar_post() {
        VMEncuesta vm = new VMEncuesta();
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("reunion_id")));
            cliente.eliminarEncuesta(reunion);
            vm.setMensaje("Encuesta eliminada exitosamente.");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encuesta/Eliminar.jsp", vm);
    }

    public void modificar_get() {
        try {
            DTReunion r = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));
            sesion.setAttribute("encuesta", r.getEncuesta());
            mostrarVista("Encuesta/modificar.jsp");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modificar_post() {
        ViewModel vm = new ViewModel();
        try {
            DTEncuesta encuesta = (DTEncuesta) sesion.getAttribute("encuesta");

            encuesta.setTitulo(request.getParameter("titulo"));
            encuesta.setDuracion(Integer.parseInt(request.getParameter("duracion")));

            int cantPropuestas = Integer.parseInt(request.getParameter("cantidad_propuestas"));
            List<DTPropuesta> propuestas = new ArrayList();
            DTPropuesta propuesta;
            for (int i = 0; i < cantPropuestas; i++) {
                propuesta = new DTPropuesta();
                propuesta.setPregunta(request.getParameter("propuesta" + i + "_pregunta"));

                int cantRespuestas = Integer.parseInt(request.getParameter("propuesta" + i + "_cantidad_respuestas"));

                for (int j = 0; j < cantRespuestas; j++) {
                    propuesta.getRespuestas().add(new DTRespuesta(0, request.getParameter("propuesta" + i + "_respuesta" + j)));
                }
                propuestas.add(propuesta);
            }
            encuesta.setPropuestas(propuestas);      
            
            cliente.modificarEncuesta(encuesta);
            vm.setMensaje("Encuesta modificada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }

        mostrarVista("Encuesta/modificar.jsp", vm);
    }
}
