package org.arqrifa.logica;

import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.persistencia.FabricaPersistencia;
import org.arqrifa.excepciones.ArquitecturaRifaExcepcion;

class LogicaUsuario implements ILogicaUsuario {

    private static LogicaUsuario instancia = null;

    public static ILogicaUsuario getInstancia() {
        if (instancia == null) {
            instancia = new LogicaUsuario();
        }
        return instancia;
    }

    private LogicaUsuario() {
    }

    @Override
    public DTUsuario Autenticar(int ci, String contrasena) {
        DTUsuario resp = null;
        try {
            resp = FabricaPersistencia.getPersistenciaUsuario().Autenticar(ci, contrasena);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
        return resp;
    }
}
