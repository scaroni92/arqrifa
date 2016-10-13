package org.arqrifa.servlets;

import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.ClienteJersey;
import org.arqrifa.viewmodels.ViewModel;

public class ControladorAdministrador extends Controlador {

    public void agregar_encargado_get() {
        
        try {            
            List<DTGeneracion> generaciones = new ClienteJersey().listarGeneraciones();
            sesion.setAttribute("generaciones", generaciones);
            
        } catch (Exception e) {
            mostrarVista("/Vistas/Admin/agregarEncargado.jsp", new ViewModel(e.getMessage()));
        }
        mostrarVista("/Vistas/Admin/agregarEncargado.jsp");
    }

    public void agregar_encargado_post() {
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

            mostrarVista("/Vistas/Admin/agregarEncargado.jsp", new ViewModel("Encargado agregado exitosamente."));

        } catch (Exception ex) {
            sesion.setAttribute("encargado", encargado);
            mostrarVista("/Vistas/Admin/agregarEncargado.jsp", new ViewModel(ex.getMessage()));
        }
    }
}
