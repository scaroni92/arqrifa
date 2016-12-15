package org.arqrifa.persistencia;

public class FabricaPersistencia {

    public static IPersistenciaReunion getPersistenciaReunion() {
        return PersistenciaReunion.getInstancia();
    }

    public static IPersistenciaUsuario getPersistenciaUsuario() {
        return PersistenciaUsuario.getInstancia();
    }

    public static IPersistenciaGeneracion getPersistenciaGeneracion() {
        return PersistenciaGeneracion.getInstancia();
    }

    public static IPersistenciaSolicitud getPersistenciaSolicitud() {
        return PersistenciaSolicitud.getInstancia();
    }
    
    public static IPersistenciaEncuesta getPersistenciaEncuesta() {
        return PersistenciaEncuesta.getInstancia();
    }
}
