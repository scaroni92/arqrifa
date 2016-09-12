package org.arqrifa.logica;

public class FabricaLogica {
    
    public static  IControladorUsuario getLogicaUsuario() {
        return ControladorUsuario.getInstancia();
    }
    
    public static  IControladorReuniones getControladorReuniones() {
        return ControladorReuniones.getInstancia();
    }
}
