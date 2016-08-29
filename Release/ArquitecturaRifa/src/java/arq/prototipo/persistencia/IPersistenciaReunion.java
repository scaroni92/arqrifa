package arq.prototipo.persistencia;

import arq.prototipo.datatypes.*;

public interface IPersistenciaReunion {

    DTReunion BuscarReunion(int id) throws Exception;
    
    void MarcarAsistencia(DTEstudiante estudiante, DTReunion reunion) throws Exception;
}
