package org.arqrifa.persistencia;

import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;

public interface IPersistenciaReunion {

    DTReunion BuscarReunion(int id) throws Exception;
    
    void MarcarAsistencia(DTUsuario estudiante, DTReunion reunion) throws Exception;
    
    void altaReunion(DTReunion reunion) throws Exception;
            
}
