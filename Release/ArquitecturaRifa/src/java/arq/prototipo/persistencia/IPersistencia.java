package arq.prototipo.persistencia;

import arq.prototipo.datatypes.*;

public interface IPersistencia {

    DTEstudiante Autenticar(int ci, String contrasena) throws Exception;

    void MarcarAsistencia(DTEstudiante estudiante, DTReunion reunion) throws Exception;
    
    DTEstudiante BuscarEstudiante(int ci) throws Exception;
    
    DTReunion BuscarReunion(int id) throws Exception;
}
