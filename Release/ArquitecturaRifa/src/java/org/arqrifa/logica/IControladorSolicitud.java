package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;

public interface IControladorSolicitud {

    void agregarSolicitud(DTSolicitud solicitud);

    void verificarSolicitud(int ci);

    void confirmarSolicitud(DTSolicitud solicitud);

    void rechazarSolicitud(DTSolicitud solicitud);

    DTSolicitud buscarSolicitud(int ci);
    
    //cambiar por param gen
    List<DTSolicitud> listarSolicitudes(DTUsuario usuario);
}
