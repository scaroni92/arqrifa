package org.arqrifa.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTRespuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMEncuesta;
import org.arqrifa.viewmodels.VMReunion;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorEncuesta", urlPatterns = {"/encuesta"})
public class ControladorEncuesta extends Controlador {

    public void agregar_get() {
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("reunionId")));

            if (reunion != null) {
                //Es necesario el uso de session ya que se manejan colecciones de colecciones
                sesion.setAttribute("reunion", reunion);
                mostrarVista("encuestas/agregar.jsp");
            }

        } catch (Exception e) {
            mostrarVista("encuestas/agregar.jsp", new ViewModel(e.getMessage()));
        }

    }

    public void agregar_post() {
        try {
            DTReunion reunion = (DTReunion) sesion.getAttribute("reunion");
            reunion.setEncuesta(new DTEncuesta());
            reunion.getEncuesta().setTitulo(request.getParameter("titulo"));
            reunion.getEncuesta().setDuracion(Integer.parseInt(request.getParameter("duracion")));
            reunion.getEncuesta().setPropuestas(getPropuestas());
            
            cliente.agregarEncuesta(reunion);
            sesion.removeAttribute("reunion");
            sesion.setAttribute("mensaje", "Encuesta agregada exitosamente");
            response.sendRedirect("usuario?accion=ver-calendario");
        } catch (Exception e) {
            mostrarVista("encuestas/agregar.jsp", new ViewModel(e.getMessage()));
        }
    }

    private List<DTPropuesta> getPropuestas() throws Exception {
        List<DTPropuesta> propuestas = new ArrayList<>();
        String[] preguntas = request.getParameterValues("preguntas");
        DTPropuesta propuesta;
        
        if (preguntas == null) {
            throw new Exception("Ingrese alguna propuesta");
        }
        
        for (int i = 0; i < preguntas.length; i++) {
            propuesta = new DTPropuesta();
            propuesta.setPregunta(preguntas[i]);
            String[] respuestas = request.getParameterValues("respuestas" + i);

            if (respuestas == null) {
                throw new Exception("Agregue las respuestas de la propuesta " + (i + 1));
            }
            for (String respuesta : respuestas) {
                if (!respuesta.isEmpty()) {
                    propuesta.getRespuestas().add(new DTRespuesta(0, respuesta, 0));
                }
            }
            propuestas.add(propuesta);
        }

        return propuestas;
    }

    public void modificar_get() {
        VMEncuesta vm = new VMEncuesta();
        try {
            sesion.setAttribute("encuesta", cliente.buscarEncuesta(Integer.parseInt(request.getParameter("id"))));
        } catch (Exception ex) {
            vm.setMensaje(ex.getMessage());
        }
        mostrarVista("encuestas/modificar.jsp", vm);
    }

    public void modificar_post() {
        try {
            DTEncuesta encuesta = (DTEncuesta) sesion.getAttribute("encuesta");
            encuesta.setId(Integer.parseInt(request.getParameter("id")));
            encuesta.setTitulo(request.getParameter("titulo"));
            encuesta.setDuracion(Integer.parseInt(request.getParameter("duracion")));
            encuesta.setPropuestas(getPropuestas());

            cliente.modificarEncuesta(encuesta);
            sesion.removeAttribute("encuesta");
            sesion.setAttribute("mensaje", "Encuesta modificada exitosamente");
            response.sendRedirect("usuario");
        } catch (Exception e) {
            mostrarVista("encuestas/modificar.jsp", new ViewModel(e.getMessage()));
        }
    }

    public void eliminar_post() {
        DTReunion reunion = null;
        try {
            //TODO enviar reunión para comprobar estado en lógica
            reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("reunionId")));

            cliente.eliminarEncuesta(reunion.getEncuesta());
            sesion.setAttribute("mensaje", "Encuesta eliminada exitosamente.");
            response.sendRedirect("usuario?accion=ver-calendario");
        } catch (Exception e) {
            mostrarVista("encuestas/detalles.jsp", new VMReunion(reunion, e.getMessage()));
        }

    }

    public void detalles_get() {
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("reunionId")));

            if (reunion.getEncuesta() == null) {
                mostrarVista("Error/404.jsp");
            } else if (usuario.getGeneracion() != reunion.getGeneracion() && !usuario.getRol().equals(DTUsuario.ADMIN)) {
                mostrarVista("Error/403.jsp");
            }

            mostrarVista("encuestas/detalles.jsp", new VMReunion(reunion, ""));
        } catch (Exception e) {
            mostrarVista("Error/500.jsp");
        }
    }

    public void iniciar_get() {
        try {
            cliente.iniciarVotacion(cliente.buscarReunion(Integer.parseInt(request.getParameter("reunionId"))));
            sesion.setAttribute("mensaje", "Votación iniciada exitosamente");
        } catch (Exception e) {
            sesion.setAttribute("mensaje", e.getMessage());
        }
        this.detalles_get();
    }

    public void finalizar_get() {
        try {
            cliente.finalizarVotacion(cliente.buscarReunion(Integer.parseInt(request.getParameter("reunionId"))));
            sesion.setAttribute("mensaje", "Votación finalizada exitosamente");
        } catch (Exception e) {
            sesion.setAttribute("mensaje", e.getMessage());
        }
        this.detalles_get();
    }
}
