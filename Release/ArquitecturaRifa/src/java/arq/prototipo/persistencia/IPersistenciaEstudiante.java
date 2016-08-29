package arq.prototipo.persistencia;

import arq.prototipo.datatypes.*;

public interface IPersistenciaEstudiante {

    DTEstudiante Autenticar(int ci, String contrasena) throws Exception;
    
    DTEstudiante BuscarEstudiante(int ci) throws Exception;
}
