package org.arqrifa.servlets;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.ClienteJersey;
import org.arqrifa.viewmodels.VMSolicitudes;

public class ControladorEncargados extends Controlador {

    public void solicitudes_get() {
        List<DTSolicitud> lista = new ArrayList();
        try {
            lista = new ClienteJersey().listarSolicitudes((DTUsuario) sesion.getAttribute("usuario"));
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(lista, ""));
        } catch (Exception e) {
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(lista, e.getMessage()));
        }
        
    }
}
