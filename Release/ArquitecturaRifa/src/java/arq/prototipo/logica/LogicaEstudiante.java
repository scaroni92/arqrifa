package arq.prototipo.logica;

import arq.prototipo.datatypes.*;
import arq.prototipo.excepciones.ArquitecturaRifaExcepcion;
import arq.prototipo.persistencia.*;

class LogicaEstudiante implements ILogicaEstudiante {

    private static LogicaEstudiante instancia = null;
   

    public static ILogicaEstudiante getInstancia() {
        if (instancia == null) {
            instancia = new LogicaEstudiante();
        }
        return instancia;
    }

    private LogicaEstudiante() {
    }

    @Override
    public DTEstudiante Autenticar(int ci, String contrasena) {
        DTEstudiante resp = null;
        try {
            resp = FabricaPersistencia.getPersistenciaEstudiante().Autenticar(ci, contrasena);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
        return resp;
    }
}
