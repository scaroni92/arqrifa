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
    
    //private final RecursoReuniones recurso = new RecursoReuniones();
    DTReunion reunionActiva;

    public void index_get() {
        ViewModel vm = new ViewModel();
        try {
            reunionActiva = (DTReunion)sesion.getAttribute("reunionActiva");
            if (!reunionActiva.getEncuesta().isHabilitada()) {
                throw new Exception("La encuesta no está habilitada para votaciones");
            }
            mostrarVista("encuestas/cuestionario.jsp");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void buscar_get() {
        ViewModel vm = new ViewModel();
        sesion.setAttribute("estudiante", null);
        try {
            
            DTUsuario dtUsuario = new RecursoUsuarios().buscar(request.getParameter("ci"));
            if (dtUsuario != null) {
                //Verificar ROL en lógica
                if (dtUsuario.getGeneracion() == usuario.getGeneracion()) {
                    sesion.setAttribute("estudiante", dtUsuario);
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

    public void confirmar_post() {
        //TODO comprobar estado de reunión en lógica

        ViewModel vm = new ViewModel();
        try {
            DTVoto votacion = new DTVoto();
            votacion.setUsuario((DTUsuario) sesion.getAttribute("estudiante"));
            votacion.setReunion(reunionActiva);            
            votacion.setRespuestasEscogidas(getRespuestasEscogidas());
            
            //TODO comprobar voto repetido
            new RecursoEncuestas().agregarVoto(votacion);
            sesion.removeAttribute("estudiante");
            vm.setMensaje("Votación exitosa");
        } catch (NumberFormatException e) {
            //validar en JS
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
