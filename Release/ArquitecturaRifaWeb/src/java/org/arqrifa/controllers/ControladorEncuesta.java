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

    public void index_get() {
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("reunionId")));
            if (reunion.getEncuesta() == null) {
                this.agregar_get();
            } else {
                this.detalles_get();
            }

        } catch (Exception e) {
            mostrarVista("reunion/detalles.jsp");
        }
    }

    public void agregar_get() {
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("reunionId")));

            if (reunion != null) {
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

            cargarPropuestas(reunion.getEncuesta());

            cliente.agregarEncuesta(reunion);
            sesion.removeAttribute("reunion");
            sesion.setAttribute("mensaje", "Encuesta agregada exitosamente");
            response.sendRedirect("usuario?accion=ver-calendario");
        } catch (Exception e) {
            mostrarVista("encuestas/agregar.jsp", new ViewModel(e.getMessage()));
        }
    }

    // Esta operación crea los objetos DTPropuesta con la info que viene en el request
    private void cargarPropuestas(DTEncuesta encuesta) throws Exception {
        encuesta.setPropuestas(new ArrayList<>());

        String[] preguntas = request.getParameterValues("preguntas");

        if (preguntas == null) {
            throw new Exception("Ingrese alguna propuesta");
        }

        for (int i = 0; i < preguntas.length; i++) {

            String pregunta = preguntas[i];
            String[] respuestas = request.getParameterValues("respuestas" + i);
            //TODO: verificar esto en JS 
            if (respuestas == null) {
                throw new Exception("Todas las propuestas deben tener respuestas");
            }

            List<DTRespuesta> res = new ArrayList<>();
            for (String respuesta : respuestas) {
                res.add(new DTRespuesta(respuesta));
            }
            encuesta.getPropuestas().add(new DTPropuesta(0, pregunta, res));
        }
    }

    public void modificar_get() {
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("reunionId")));

            if (reunion != null) {
                sesion.setAttribute("reunion", reunion);
                mostrarVista("encuestas/modificar.jsp");
            }

        } catch (Exception e) {
            mostrarVista("encuestas/modificar.jsp", new ViewModel(e.getMessage()));
        }
    }

    public void modificar_post() {
        try {
            DTReunion reunion = (DTReunion) sesion.getAttribute("reunion");
            reunion.getEncuesta().setTitulo(request.getParameter("titulo"));
            reunion.getEncuesta().setDuracion(Integer.parseInt(request.getParameter("duracion")));

            cargarPropuestas(reunion.getEncuesta());

            cliente.modificarEncuesta(reunion.getEncuesta());
            sesion.removeAttribute("reunion");
            sesion.setAttribute("mensaje", "Encuesta modificada exitosamente");
            response.sendRedirect("usuario?accion=ver-calendario");
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

}
