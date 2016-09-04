package org.arqrifa.logica;

import org.arqrifa.datatypes.DTReunion;

public interface ILogicaReunion {

    void MarcarAsistencia(int ci);
    
    DTReunion BuscarReunion(int id) throws Exception;
}
