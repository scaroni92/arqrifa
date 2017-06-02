package org.arqrifa.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTRespuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTVoto;
import org.arqrifa.rest.RecursoEncuestas;
import org.arqrifa.rest.RecursoUsuarios;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorCuestionario", urlPatterns = {"/cuestionario"})
public class ControladorCuestionario extends Controlador {

    public void index_get() {
        ViewModel vm = new ViewModel();
        try {
            DTReunion reunion = (DTReunion)sesion.getAttribute("reunionActiva");
            if (!reunion.getEncuesta().isHabilitada()) {
                throw new Exception("La encuesta no está habilitada para votaciones");
            }
            mostrarVista("encuestas/cuestionario.jsp");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);

    }

    public void buscar_estudiante_get() {
        ViewModel vm = new ViewModel();
        try {
            sesion.setAttribute("estudiante", null);
            DTUsuario dtUsuario = new RecursoUsuarios().buscar(request.getParameter("ci"));
            if (dtUsuario != null) {
                if (dtUsuario.getRol().equals(DTUsuario.ESTUDIANTE)) {
                    sesion.setAttribute("estudiante", dtUsuario);
                    vm.setMensaje("Estudiante encontrado");
                }
                else {
                    vm.setMensaje("Estudiante no encontrado");
                }
            }

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("encuestas/cuestionario.jsp", vm);
    }

    public void confirmar_voto_post() {
        //TODO comprobar estado de reunión en lógica

        ViewModel vm = new ViewModel();
        try {
            DTUsuario estudiante = (DTUsuario) sesion.getAttribute("estudiante");
            DTReunion reunionActiva = (DTReunion) sesion.getAttribute("reunionActiva");
            DTEncuesta encuesta = reunionActiva.getEncuesta();

            List<DTRespuesta> respuestasEscogidas = new ArrayList();
            for (DTPropuesta propuesta : encuesta.getPropuestas()) {
                int respuestaId = Integer.parseInt(request.getParameter(String.valueOf(propuesta.getId())));
                respuestasEscogidas.add(new DTRespuesta(respuestaId, "", 0));
            }
            //TODO comprobar voto repetido
            new RecursoEncuestas().agregarVoto(new DTVoto(estudiante, reunionActiva, respuestasEscogidas));
            sesion.removeAttribute("estudiante");
            vm.setMensaje("Votación exitosa");

        } catch (NumberFormatException e) {
            vm.setMensaje("Seleccione una opción de cada respuesta");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("encuestas/cuestionario.jsp", vm);
    }
}
