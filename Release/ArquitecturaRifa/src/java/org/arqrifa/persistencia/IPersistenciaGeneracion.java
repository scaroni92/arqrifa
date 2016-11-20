package org.arqrifa.persistencia;

import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;

public interface IPersistenciaGeneracion {
 
    void agregar(DTGeneracion generacion) throws Exception;
    
    List<DTGeneracion> listar() throws Exception;
    
}
