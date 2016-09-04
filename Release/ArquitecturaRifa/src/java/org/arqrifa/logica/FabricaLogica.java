package org.arqrifa.logica;

public class FabricaLogica {
    
    public static  ILogicaUsuario getLogicaUsuario() {
        return LogicaUsuario.getInstancia();
    }
    
    public static  ILogicaReunion getLogicaReunion() {
        return LogicaReunion.getInstancia();
    }
}
