package org.arqrifa.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTRespuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.rest.RecursoEncuestas;
import org.arqrifa.rest.RecursoReuniones;
import org.arqrifa.viewmodels.VMReunion;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorEncuesta", urlPatterns = {"/encuesta"})
public class ControladorEncuesta extends Controlador {

    private final RecursoEncuestas recurso = new RecursoEncuestas();

    public void agregar_get() {
        try {
            DTReunion reunion = new RecursoReuniones().buscar(request.getParameter("id"));

            if (reunion == null) {
                response.sendError(404);
                return;
            }

            if (reunion.getGeneracion() != usuario.getGeneracion()) {
                response.sendError(403);
                return;
            }

            if (reunion.getEncuesta() != null) {
                response.sendRedirect("reuniones?accion=detalles&&id=" + reunion.getId());
                return;
            }
            sesion.setAttribute("reunion", reunion);
            mostrarVista("encuestas/agregar.jsp");

        } catch (Exception e) {
            mostrarVista("encuestas/agregar.jsp", new ViewModel(e.getMessage()));
        }

    }

    public void agregar_post() {
        DTReunion reunion = (DTReunion) sesion.getAttribute("reunion");
        DTEncuesta encuesta = new DTEncuesta();

        try {

            encuesta.setTitulo(request.getParameter("titulo"));
            encuesta.setDuracion(Integer.parseInt(request.getParameter("duracion")));
            encuesta.setPropuestas(getPropuestas());

            reunion.setEncuesta(encuesta);

            recurso.agregar(reunion);
            sesion.removeAttribute("reunion");
            sesion.setAttribute("mensaje", "Encuesta agregada exitosamente");
            response.sendRedirect("reuniones?accion=detalles&&id=" + reunion.getId());
        } catch (Exception e) {
            mostrarVista("encuestas/agregar.jsp", new ViewModel(e.getMessage()));
        }
    }

    private List<DTPropuesta> getPropuestas() throws Exception {
        List<DTPropuesta> propuestas = new ArrayList<>();

        String[] preguntas = request.getParameterValues("preguntas");

        if (preguntas == null) {
            throw new Exception("No se permiten encuestas sin propuestas");
        }

        for (int i = 0; i < preguntas.length; i++) {
            propuestas.add(getPropuesta(preguntas[i], request.getParameterValues("respuestas" + i)));
        }

        return propuestas;
    }

    private DTPropuesta getPropuesta(String pregunta, String[] respuestas) throws Exception {

        if (respuestas == null) {
            throw new Exception("Todas las propuestas deben tener respuestas");
        }

        List<DTRespuesta> respuestasArray = new ArrayList<>();
        for (String respuesta : respuestas) {
            respuestasArray.add(new DTRespuesta(respuesta));
        }

        return new DTPropuesta(0, pregunta, respuestasArray);
    }

    public void modificar_get() {
        ViewModel vm = new ViewModel();
        try {
            DTReunion reunion = new RecursoReuniones().buscar(request.getParameter("id"));

            if (reunion == null) {
                response.sendError(404);
                return;
            }

            sesion.setAttribute("reunion", reunion);
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("encuestas/modificar.jsp", vm);
    }

    public void modificar_post() {
        DTReunion reunion = (DTReunion) sesion.getAttribute("reunion");
        try {
            reunion.getEncuesta().setTitulo(request.getParameter("titulo"));
            reunion.getEncuesta().setDuracion(Integer.parseInt(request.getParameter("duracion")));
            reunion.getEncuesta().setPropuestas(getPropuestas());

            recurso.modificar(reunion);

            sesion.removeAttribute("reunion");
            sesion.setAttribute("mensaje", "Encuesta modificada exitosamente");
            response.sendRedirect("reuniones?accion=detalles&&id=" + reunion.getId());
        } catch (Exception e) {
            mostrarVista("encuestas/modificar.jsp", new ViewModel(e.getMessage()));
        }
    }

    public void eliminar_post() {
        DTReunion reunion = null;
        try {
            reunion = new RecursoReuniones().buscar(request.getParameter("id"));
            recurso.eliminar(reunion.getId());
            sesion.setAttribute("mensaje", "Encuesta eliminada exitosamente");
            response.sendRedirect("reuniones?accion=detalles&&id=" + reunion.getId());
        } catch (Exception e) {
            mostrarVista("encuestas/detalles.jsp", new VMReunion(reunion, e.getMessage()));
        }
    }

}
