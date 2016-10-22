package org.arqrifa.servlets;

import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.ClienteJersey;
import org.arqrifa.viewmodels.VMGeneraciones;
import org.arqrifa.viewmodels.ViewModel;

public class ControladorAdministrador extends Controlador {

    public void agregar_encargado_get() {
        ViewModel vm = new ViewModel();
        try {
            List<DTGeneracion> generaciones = new ClienteJersey().listarGeneraciones();
            sesion.setAttribute("generaciones", generaciones);
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("/Vistas/Admin/agregarEncargado.jsp", vm);
    }

    public void agregar_encargado_post() {
        ViewModel vm = new ViewModel();
        DTUsuario encargado = null;

        try {
            int ci;
            int generacion = Integer.parseInt(request.getParameter("generacion"));
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String pass = request.getParameter("pass");
            String email = request.getParameter("email");

            try {
                ci = Integer.parseInt(request.getParameter("ci"));
            } catch (NumberFormatException e) {
                throw new Exception("La cédula debe ser numérica.");
            }

            encargado = new DTUsuario(ci, nombre, apellido, pass, email, "encargado", generacion);
            new ClienteJersey().agregarEncargado(encargado);

            vm.setMensaje("Encargado agregado exitosamente.");

        } catch (Exception ex) {
            sesion.setAttribute("encargado", encargado);
            vm.setMensaje(ex.getMessage());
        }
        mostrarVista("/Vistas/Admin/agregarEncargado.jsp", vm);

    }

    public void agregar_generacion_get() {
        VMGeneraciones vm = new VMGeneraciones();
        try {
            vm.setGeneraciones(new ClienteJersey().listarGeneraciones());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("/Vistas/Admin/agregarGeneracion.jsp", vm);
    }

    public void agregar_generacion_post() {
        VMGeneraciones vm = (VMGeneraciones) request.getAttribute("modelo");

        try {

            int anio = Integer.parseInt(request.getParameter("anio"));
            DTGeneracion generacion = new DTGeneracion(anio);

            new ClienteJersey().agregarGeneracion(generacion);
            
            vm.setMensaje("Generación agregada con éxito.");

        } catch (NumberFormatException e) {
            vm.setMensaje("Ingrese un año válido.");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("/Vistas/Admin/agregarGeneracion.jsp", vm);
    }
}
