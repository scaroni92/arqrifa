package org.arqrifa.controllers;
//Acceso: ENCARGADO

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTRespuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTVoto;
import org.arqrifa.viewmodels.VMReunion;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorCuestionario", urlPatterns = {"/cuestionario"})
public class ControladorCuestionario extends Controlador {

    public void index_get() {
        DTReunion reunion = null;
        try {
            reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("reunionId")));
            if (!reunion.getEncuesta().isHabilitada()) {
                throw new Exception("La encuesta no está habilitada para votaciones");
            }
            sesion.setAttribute("encuesta", reunion.getEncuesta());
            mostrarVista("encuestas/cuestionario.jsp");
        } catch (Exception e) {
            mostrarVista("encuestas/detalles.jsp", new VMReunion(reunion, e.getMessage()));
        }

    }

    public void buscar_estudiante_get() {
        ViewModel vm = new ViewModel();
        try {
            sesion.setAttribute("estudiante", null);
            DTUsuario dtUsuario = cliente.buscarUsuario(Integer.parseInt(request.getParameter("ci")));
            if (dtUsuario != null) {
                if (dtUsuario.getRol().equals(DTUsuario.ESTUDIANTE)) {
                    sesion.setAttribute("estudiante", dtUsuario);
                    vm.setMensaje("Estudiante encontrado");
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
            DTEncuesta encuesta = (DTEncuesta) sesion.getAttribute("encuesta");

            List<DTRespuesta> respuestasEscogidas = new ArrayList();
            for (DTPropuesta propuesta : encuesta.getPropuestas()) {
                int respuestaId = Integer.parseInt(request.getParameter(String.valueOf(propuesta.getId())));
                respuestasEscogidas.add(new DTRespuesta(respuestaId, "", 0));
            }
            //TODO comprobar voto repetido
            cliente.agregarVoto(new DTVoto(estudiante, respuestasEscogidas));
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