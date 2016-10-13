package org.arqrifa.servlets;

import java.util.Date;
import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.ClienteJersey;
import org.arqrifa.viewmodels.ViewModel;

public class ControladorUsuarios extends Controlador {

    public void registrar_get() {
        try {
            List<DTGeneracion> generaciones = new ClienteJersey().listarGeneraciones();
            sesion.setAttribute("generaciones", generaciones);
        } catch (Exception e) {
            mostrarVista("registro.jsp", new ViewModel(e.getMessage()));
        }
        mostrarVista("registro.jsp");
    }

    public void registrar_post() {
        DTSolicitud solicitud = null;

        try {
            int ci = Integer.parseInt(request.getParameter("ci"));
            int generacion = Integer.parseInt(request.getParameter("generacion"));
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String pass = request.getParameter("pass");
            String email = request.getParameter("email");

            solicitud = new DTSolicitud(ci, generacion, new Date(), nombre, apellido, pass, email, 0, false);
            new ClienteJersey().enviarSolicitud(solicitud);
            mostrarVista("index.jsp");
        } catch (Exception ex) {
            sesion.setAttribute("solicitud", solicitud);
            mostrarVista("registro.jsp", new ViewModel(ex.getMessage()));
        }
    }

    public void login_post() {
        try {
            int ci = Integer.parseInt(request.getParameter("user"));
            String pass = request.getParameter("pass");

            if (ci < 4000000) {
                throw new Exception("Ingrese una cédula válida.");
            }
            if (pass.isEmpty()) {
                throw new Exception("Ingrese la contraseña");
            }

            DTUsuario usuario = new ClienteJersey().login(ci, pass);
            if (usuario == null) {
                throw new Exception("Usuario o contraseña incorrectos.");
            }
            sesion.setAttribute("usuario", usuario);
            if (usuario.getRol().equals("estudiante")) {
                mostrarVista("Vistas/Estudiante/index.jsp");
            }
            else if (usuario.getRol().equals("encargado")) {
                mostrarVista("Vistas/Encargado/index.jsp");
            }
            else {
                mostrarVista("Vistas/Admin/index.jsp");
            }

        } catch (NumberFormatException ex) {
            mostrarVista("index.jsp", new ViewModel("La cédula debe ser numérica."));
        } catch (Exception ex) {
            mostrarVista("index.jsp", new ViewModel(ex.getMessage()));
        }
    }
}
