package org.arqrifa.persistencia;

import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;


public interface IPersistenciaSolicitud {
    void agregar(DTSolicitud solicitud) throws Exception;
    
    void verificar(int codigo) throws Exception;
    
    void confirmar(DTSolicitud solicitud) throws Exception;
    
    void rechazar(DTSolicitud solicitud) throws Exception;
    
    DTSolicitud buscar(int ci) throws Exception;
    
     List<DTSolicitud> listar(int generacion) throws Exception;
}
