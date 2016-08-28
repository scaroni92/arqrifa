package arq.prototipo.logica;

import arq.prototipo.datatypes.*;

public interface ISistema {

    DTEstudiante Autenticar(int ci, String contrasena);

    void MarcarAsistencia(int ci);
}
