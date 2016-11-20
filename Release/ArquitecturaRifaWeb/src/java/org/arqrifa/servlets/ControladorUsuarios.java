package org.arqrifa.servlets;

import java.util.Date;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMUsuario;
import org.arqrifa.viewmodels.VMVerificacion;
import org.arqrifa.viewmodels.ViewModel;

public class ControladorUsuarios extends Controlador {

    public void registrar_get() {
        VMUsuario vm = new VMUsuario();
        try {

            vm.setGeneraciones(cliente.listarGeneraciones());

        } catch (Exception e) {
            vm.setMensaje("Error al listar las generaciones.");
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

            if (vm.getNombre().isEmpty()) {
                throw new Exception("Ingrese su nombre");
            }

            if (vm.getApellido().isEmpty()) {
                throw new Exception("Ingrese la contraseña");
            }

            if (vm.getEmail().isEmpty()) {
                throw new Exception("Ingrese el mail");
            }

            cliente.enviarSolicitud(new DTSolicitud(ci, generacion, new Date(), vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), 0, false));
            mostrarVista("index.jsp");

        } catch (Exception ex) {
            vm.setMensaje(ex.getMessage());
            try {
                vm.setGeneraciones(cliente.listarGeneraciones());
            } catch (Exception e) {
                vm.setMensaje("<br>Error al listar las generaciones.");
            }
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

            mostrarVista("Vistas/" + usuario.getRol() + "/index.jsp");

        } catch (NumberFormatException ex) {
            mostrarVista("index.jsp", new ViewModel("La cédula debe ser numérica."));
        } catch (Exception ex) {
            mostrarVista("index.jsp", new ViewModel(ex.getMessage()));
        }
    }

    public void verificar_get() {
        VMVerificacion vm = new VMVerificacion();
        try {
            int codigo = Integer.parseInt(request.getParameter("codigo"));

            vm.setVerificada(false);
            cliente.verificarSolicitud(codigo);

            vm.setVerificada(true);
        } catch (NumberFormatException ex) {
            vm.setMensaje("El código de verificación no es válido.");
        } catch (Exception ex) {
            vm.setMensaje("No se pudo verificar la solicitud, quizas haya sido rechazada por el encargado");
        }
        mostrarVista("verificar.jsp", vm);
    }
}
