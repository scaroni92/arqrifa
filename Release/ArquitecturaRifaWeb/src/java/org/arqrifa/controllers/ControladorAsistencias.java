package org.arqrifa.controllers;

import java.util.List;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.RecursoReuniones;
import org.arqrifa.rest.RecursoUsuarios;
import org.arqrifa.viewmodels.VMListaAsistencias;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorAsistencias", urlPatterns = {"/asistencias"})
public class ControladorAsistencias extends Controlador {

    public void index_get() {
        VMListaAsistencias vm = new VMListaAsistencias();
        try {
            DTReunion reunionActiva = (DTReunion) sesion.getAttribute("reunionActiva");
            if (!reunionActiva.isListado()) {
                throw new Exception("La lista no ha sido habilitada");
            }
            vm.setAsistencias(new RecursoReuniones().listarAsistencias(reunionActiva.getId()));
            mostrarVista("reuniones/asistencias.jsp", vm);
        } catch (Exception e) {
            mostrarVista("reuniones/panel.jsp", new ViewModel(e.getMessage()));
        }
    }

    public void marcar_asistencia_get() {
        try {
            DTUsuario estudiante = new RecursoUsuarios().buscar(Integer.parseInt(request.getParameter("ci")));
            DTReunion reunionActiva = (DTReunion) sesion.getAttribute("reunionActiva");
            new RecursoReuniones().agregarAsistencia(estudiante, reunionActiva);
            sesion.setAttribute("mensaje", "Asistenia marcada exitosamente.");
        }
         catch (NumberFormatException e) {
            sesion.setAttribute("mensaje", "La cédula debe ser numérica");
        }
        catch (Exception e) {
            sesion.setAttribute("mensaje", e.getMessage());
        }
        index_get();
    }

    public void buscar_get() {
        VMListaAsistencias vm = new VMListaAsistencias();
        try {
            DTReunion reunionActiva = (DTReunion) sesion.getAttribute("reunionActiva");
            List<DTAsistencia> asistencias = new RecursoReuniones().listarAsistencias(reunionActiva.getId());
            String ci = request.getParameter("ci");
            if (!ci.isEmpty()) {
                DTAsistencia asistencia = null;
                for (DTAsistencia a : asistencias) {
                    if (String.valueOf(a.getEstudiante().getCi()).equals(ci)) {
                        asistencia = a;
                    }
                }
                if (asistencia != null) {
                    vm.getAsistencias().add(asistencia);
                }
            } else {
                vm.setAsistencias(asistencias);
            }
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/asistencias.jsp", vm);
    }
}
