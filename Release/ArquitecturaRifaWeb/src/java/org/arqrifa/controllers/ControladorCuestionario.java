package org.arqrifa.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTRespuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTVotacion;
import org.arqrifa.rest.RecursoEncuestas;
import org.arqrifa.rest.RecursoUsuarios;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorCuestionario", urlPatterns = {"/cuestionario"})
public class ControladorCuestionario extends Controlador {

    DTReunion reunionActiva;

    public void index_get() {
        try {
            reunionActiva = (DTReunion) sesion.getAttribute("reunionActiva");
            if (!reunionActiva.isVotacion()) {
                throw new Exception("La encuesta no está habilitada para votaciones");
            }
            mostrarVista("encuestas/cuestionario.jsp");
        } catch (Exception e) {
            mostrarVista("reuniones/panel.jsp", new ViewModel(e.getMessage()));
        }

    }

    public void buscar_get() {
        ViewModel vm = new ViewModel();
        sesion.setAttribute("estudiante", null);
        try {

            DTUsuario dtUsuario = new RecursoUsuarios().buscar(Integer.parseInt(request.getParameter("ci")));

            if (dtUsuario == null) {
                throw new Exception("Estudiante no encontrado");
            }

            if (dtUsuario.getGeneracion() != usuario.getGeneracion()) {
                throw new Exception("Estudiante no encontrado");

            }

            DTVotacion votacion = new RecursoEncuestas().buscarVotacion(dtUsuario.getCi(), reunionActiva.getId());
            if (votacion != null) {
                throw new Exception("El estudiante ya voto en la encuesta");
            }

            sesion.setAttribute("estudiante", dtUsuario);
        }
        catch (NumberFormatException e) {
            vm.setMensaje("La cédula debe ser numérica");
        }
        catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("encuestas/cuestionario.jsp", vm);
    }

    public void confirmar_post() {
        ViewModel vm = new ViewModel();
        try {
            DTVotacion votacion = new DTVotacion();
            votacion.setUsuario((DTUsuario) sesion.getAttribute("estudiante"));
            votacion.setReunion(reunionActiva);
            votacion.setRespuestasEscogidas(getRespuestasEscogidas());

            new RecursoEncuestas().agregarVotacion(votacion);
            sesion.removeAttribute("estudiante");
            vm.setMensaje("Votación exitosa");
        } catch (NumberFormatException e) {
            vm.setMensaje("Seleccione una opción de cada respuesta");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("encuestas/cuestionario.jsp", vm);
    }

    private List<DTRespuesta> getRespuestasEscogidas() throws NumberFormatException {
        List<DTRespuesta> respuestasEscogidas = new ArrayList();

        for (DTPropuesta propuesta : reunionActiva.getEncuesta().getPropuestas()) {
            int id = Integer.parseInt(request.getParameter(String.valueOf(propuesta.getId())));

            respuestasEscogidas.add(new DTRespuesta(id, "", 0));
        }

        return respuestasEscogidas;
    }
}
