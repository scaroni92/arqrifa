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
}
