package org.arqrifa.persistencia;

import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;

public interface IPersistenciaReunion {
    
    void agregar(DTReunion reunion) throws Exception;
    
    void iniciar(DTReunion reunion) throws Exception;
    
    void marcarAsistencia(DTUsuario estudiante, DTReunion reunion) throws Exception;
    
    DTReunion buscar(int id) throws Exception;
}
