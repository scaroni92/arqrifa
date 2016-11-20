package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;

public interface IControladorSolicitud {

    void agregarSolicitud(DTSolicitud solicitud);

    void verificarSolicitud(int ci);

    void confirmarSolicitud(DTSolicitud solicitud);

    void rechazarSolicitud(DTSolicitud solicitud);

    DTSolicitud buscarSolicitud(int ci);
    
    List<DTSolicitud> listarSolicitudes(int generacion);
}
