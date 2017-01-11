package org.arqrifa.logica;

public class FabricaLogica {

    public static IControladorUsuario getLogicaUsuario() {
        return ControladorUsuario.getInstancia();
    }

    public static IControladorReunion getControladorReuniones() {
        return ControladorReunion.getInstancia();
    }

    public static IControladorGeneracion getControladorGeneracion() {
        return ControladorGeneracion.getInstancia();
    }
    
    public static IControladorSolicitud getControladorSolicitud() {
        return ControladorSolicitud.getInstancia();
    }
    
    public static IControladorEncuesta getControladorEncuesta() {
        return ControladorEncuesta.getInstancia();
    }
}
