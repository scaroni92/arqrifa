package org.arqrifa.servlets;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTRespuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTVoto;
import org.arqrifa.viewmodels.VMEncuesta;
import org.arqrifa.viewmodels.VMReunion;
import org.arqrifa.viewmodels.ViewModel;

public class ControladorEncuesta extends Controlador {

    public void ver_get() {
        try {
            String reunionId = request.getParameter("reunion_id");
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(reunionId));

            if (reunion.getGeneracion() == getUsuario().getGeneracion() && reunion.getEncuesta() != null) {
                mostrarVista("Encuesta/ver.jsp", new VMReunion(reunion, ""));
            } else {
                mostrarVista("Error/404.jsp");
            }
        } catch (Exception e) {
            mostrarVista("Error/404.jsp");
        }

    }

    public void agregar_get() {
        DTReunion reunion = null;
        try {
            reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));

            if (reunion == null) {
                mostrarVista("Error/404.jsp");
            } else {
                sesion.setAttribute("reunion", reunion);
                mostrarVista("Encuesta/agregar.jsp");
            }

        } catch (Exception e) {
            mostrarVista("Reunion/ver.jsp", new VMReunion(reunion, "#error#" + e.getMessage()));
        }

    }

    public void agregar_post() {
        try {

            DTEncuesta encuesta = new DTEncuesta();
            encuesta.setTitulo(request.getParameter("titulo"));
            encuesta.setDuracion(Integer.parseInt(request.getParameter("duracion")));

            String[] preguntas = request.getParameterValues("preguntas");
            DTPropuesta propuesta;
            if (preguntas != null) {
                for (int i = 0; i < preguntas.length; i++) {
                    propuesta = new DTPropuesta();
                    propuesta.setPregunta(preguntas[i]);

                    String[] respuestas = request.getParameterValues("respuestas" + i);
                    if (respuestas != null) {
                        for (String respuesta : respuestas) {
                            if (!respuesta.isEmpty()) {
                                propuesta.getRespuestas().add(new DTRespuesta(0, respuesta, 0));
                            }
                        }
                    }
                    encuesta.getPropuestas().add(propuesta);
                }
            }

            DTReunion reunion = (DTReunion) sesion.getAttribute("reunion");
            reunion.setEncuesta(encuesta);
            cliente.agregarEncuesta(reunion);

            sesion.removeAttribute("reunion");
            mostrarVista("Reunion/ver.jsp", new VMReunion(reunion, "Encuesta agregada exitosamente"));

        } catch (Exception e) {
            mostrarVista("Encuesta/agregar.jsp", new ViewModel("#error#" + e.getMessage()));
        }
    }

    public void iniciar_votacion_post() {
        VMReunion vm = new VMReunion();
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("reunion_id")));
            cliente.iniciarVotacion(reunion);

            reunion.getEncuesta().setHabilitada(true);
            vm = new VMReunion(reunion, "Votación iniciada con éxito.");
        } catch (Exception e) {
            vm.setMensaje("#error#" + e.getMessage());
        }
        mostrarVista("Encuesta/ver.jsp", vm);
    }

    public void cuestionario_get() {
        ViewModel vm = new ViewModel();
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("reunion_id")));
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
                respuestasEscogidas.add(new DTRespuesta(Integer.parseInt(strRespuesta), "", 0));
            }

            cliente.agregarVoto(new DTVoto(estudiante, respuestasEscogidas));

            sesion.removeAttribute("estudiante");
            sesion.removeAttribute("encuesta");
            sesion.setAttribute("Votación exitosa", vm);
            response.sendRedirect("Reuniones");

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("Encuesta/cuestionario.jsp", vm);
        }
    }

    public void eliminar_get() {
        VMEncuesta vm = new VMEncuesta();
        try {
            vm.setEncuesta(cliente.buscarEncuesta(Integer.parseInt(request.getParameter("id"))));
        } catch (Exception e) {
            vm.setMensaje("#error#" + e.getMessage());
        }
        mostrarVista("Encuesta/Eliminar.jsp", vm);
    }

    public void eliminar_post() {
        VMEncuesta vm = new VMEncuesta();
        try {
            DTEncuesta encuesta = cliente.buscarEncuesta(Integer.parseInt(request.getParameter("id")));
            cliente.eliminarEncuesta(encuesta);

            sesion.setAttribute("mensaje", "Encuesta eliminada exitosamente.");
            response.sendRedirect("Reuniones");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("Encuesta/Eliminar.jsp", vm);
        }

    }

    public void modificar_get() {
        VMEncuesta vm = new VMEncuesta();
        try {
            sesion.setAttribute("encuesta", cliente.buscarEncuesta(Integer.parseInt(request.getParameter("id"))));
        } catch (Exception ex) {
            vm.setMensaje(ex.getMessage());
        }
        mostrarVista("Encuesta/modificar.jsp", vm);
    }

    public void modificar_post() {
        ViewModel vm = new ViewModel();
        try {
            DTEncuesta encuesta = (DTEncuesta) sesion.getAttribute("encuesta");

            encuesta.setTitulo(request.getParameter("titulo"));
            encuesta.setDuracion(Integer.parseInt(request.getParameter("duracion")));
            encuesta.setPropuestas(new ArrayList());

            DTPropuesta propuesta;
            String[] preguntas = request.getParameterValues("preguntas");
            if (preguntas != null) {
                for (int i = 0; i < preguntas.length; i++) {
                    propuesta = new DTPropuesta();
                    propuesta.setPregunta(preguntas[i]);

                    String[] respuestas = request.getParameterValues("respuestas" + i);
                    if (respuestas != null) {
                        for (String respuesta : respuestas) {
                            if (!respuesta.isEmpty()) {
                                propuesta.getRespuestas().add(new DTRespuesta(0, respuesta, 0));
                            }
                        }
                    }
                    encuesta.getPropuestas().add(propuesta);
                }
            }

            cliente.modificarEncuesta(encuesta);

            sesion.setAttribute("mensaje", "Encuesta modificada exitosamente");
            sesion.removeAttribute("encuesta");
            response.sendRedirect("Reuniones");

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("Encuesta/modificar.jsp", vm);
        }
    }
}
