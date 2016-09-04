package org.arqrifa.logica;

import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.persistencia.FabricaPersistencia;
import org.arqrifa.excepciones.ArquitecturaRifaExcepcion;

class LogicaReunion implements ILogicaReunion {

    private static LogicaReunion instancia = null;

    public static ILogicaReunion getInstancia() {
        if (instancia == null) {
            instancia = new LogicaReunion();
        }
        return instancia;
    }

    private LogicaReunion() {
    }

    @Override
    public void MarcarAsistencia(int ci) {
        try {
            DTUsuario estudiante = FabricaPersistencia.getPersistenciaUsuario().BuscarEstudiante(ci);
            DTReunion reunion = FabricaPersistencia.getPersistenciaReunion().BuscarReunion(1);
            if (estudiante == null) {
                throw new Exception("El estudiante no puede ser nulo.");
            }
            if (reunion == null) {
                throw new Exception("La reunion no puede ser nula.");
            }
            FabricaPersistencia.getPersistenciaReunion().MarcarAsistencia(estudiante, reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
    }
    
    @Override
    public DTReunion BuscarReunion(int id) throws Exception{
        return FabricaPersistencia.getPersistenciaReunion().BuscarReunion(1);
    }

}
