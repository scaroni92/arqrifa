package org.arqrifa.servlets;

import java.util.Date;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.ClienteJersey;
import org.arqrifa.viewmodels.ViewModel;

public class ControladorUsuarios extends Controlador {

    public void registrar_get() {
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

            if (pass.isEmpty()) {
                throw new Exception("Debe completar todos los campos");
            }

            DTUsuario usuario = new ClienteJersey().login(ci, pass);
            if (usuario == null) {
                throw new Exception("Usuario o contraseña incorrectos.");
            }
            sesion.setAttribute("usuario", usuario);
            if (usuario.getRol().equals("estudiante")) {
                mostrarVista("Vistas/Estudiante/index.jsp");
            }
            if (usuario.getRol().equals("encargado")) {
                mostrarVista("Vistas/Encargado/index.jsp");
            }

        } catch (NumberFormatException ex) {
            mostrarVista("index.jsp", new ViewModel("La cédula debe ser numérica."));
        } catch (Exception ex) {
            mostrarVista("index.jsp", new ViewModel(ex.getMessage()));
        }
    }
}
