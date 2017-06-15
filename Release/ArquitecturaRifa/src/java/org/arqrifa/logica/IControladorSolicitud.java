package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;

public interface IControladorSolicitud {

    void agregar(DTSolicitud solicitud);

    void verificar(int ci);

    void confirmar(DTSolicitud solicitud);

    void rechazar(DTSolicitud solicitud);

    DTSolicitud buscar(int ci);
    
    List<DTSolicitud> listar(int generacion);
}
