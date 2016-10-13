package org.arqrifa.logica;

import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;

public interface IControladorUsuario {

    DTUsuario Autenticar(int ci, String contrasena);

    void altaSolicitud(DTSolicitud solicitud);

    void verificarSolicitud(int ci);
    
    void altaEncargado(DTUsuario usuario);
}
