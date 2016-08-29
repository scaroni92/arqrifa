package arq.prototipo.logica;

import arq.prototipo.datatypes.*;
import arq.prototipo.excepciones.ArquitecturaRifaExcepcion;
import arq.prototipo.persistencia.*;

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
            DTEstudiante estudiante = FabricaPersistencia.getPersistenciaEstudiante().BuscarEstudiante(ci);
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
