package org.arqrifa.persistencia;

import org.arqrifa.datatypes.DTSolicitud;


public interface IPersistenciaSolicitud {
    void alta(DTSolicitud solicitud) throws Exception;
    
    void verificar(int ci) throws Exception;
    
    void confirmar(DTSolicitud solicitud) throws Exception;
    
    void rechazar(DTSolicitud solicitud) throws Exception;
}
