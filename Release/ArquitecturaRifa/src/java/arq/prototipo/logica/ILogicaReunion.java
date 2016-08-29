package arq.prototipo.logica;

import arq.prototipo.datatypes.*;

public interface ILogicaReunion {

    void MarcarAsistencia(int ci);
    
    DTReunion BuscarReunion(int id) throws Exception;
}
