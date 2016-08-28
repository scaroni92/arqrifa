package arq.prototipo.logica;

import arq.prototipo.datatypes.*;

public interface ISistema {

    DTEstudiante Autenticar(int ci, String contrasena) throws Exception;

    void MarcarAsistencia(int ci, int reunionId) throws Exception;
}
