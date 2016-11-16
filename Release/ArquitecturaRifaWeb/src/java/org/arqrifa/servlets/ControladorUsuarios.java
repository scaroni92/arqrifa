package org.arqrifa.servlets;

import java.util.Date;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMUsuario;
import org.arqrifa.viewmodels.ViewModel;

public class ControladorUsuarios extends Controlador {

    public void registrar_get() {
        VMUsuario vm = new VMUsuario();
        try {

            vm.setGeneraciones(cliente.listarGeneraciones());

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("registro.jsp", vm);
    }

    public void registrar_post() {
        VMUsuario vm = (VMUsuario) cargarModelo(new VMUsuario());

        try {

            int ci;
            try {
                ci = Integer.parseInt(vm.getCi());

            } catch (Exception e) {
                throw new Exception("Ingrese una cédula válida.");
            }

            int generacion = Integer.parseInt(vm.getGeneracion());

            cliente.enviarSolicitud(new DTSolicitud(ci, generacion, new Date(), vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), 0, false));
            mostrarVista("index.jsp");

        } catch (Exception ex) {
            vm.setMensaje(ex.getMessage());
            mostrarVista("registro.jsp", vm);
        }
    }

    public void login_post() {
        try {
            int ci = Integer.parseInt(request.getParameter("user"));
            String pass = request.getParameter("pass");

            if (pass.isEmpty()) {
                throw new Exception("Ingrese la contraseña");
            }

            DTUsuario usuario = cliente.login(ci, pass);
            if (usuario == null) {
                throw new Exception("Usuario o contraseña incorrectos.");
            }
            sesion.setAttribute("usuario", usuario);
            if (usuario.getRol().equals("estudiante")) {
                mostrarVista("Vistas/Estudiante/index.jsp");
            } else if (usuario.getRol().equals("encargado")) {
                mostrarVista("Vistas/Encargado/index.jsp");
            } else {
                mostrarVista("Vistas/Admin/index.jsp");
            }

        } catch (NumberFormatException ex) {
            mostrarVista("index.jsp", new ViewModel("La cédula debe ser numérica."));
        } catch (Exception ex) {
            mostrarVista("index.jsp", new ViewModel(ex.getMessage()));
        }
    }
}
