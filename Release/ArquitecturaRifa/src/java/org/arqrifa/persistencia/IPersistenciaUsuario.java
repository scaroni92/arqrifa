package org.arqrifa.persistencia;

import org.arqrifa.datatypes.DTUsuario;

public interface IPersistenciaUsuario {

    DTUsuario Autenticar(int ci, String contrasena) throws Exception;
    
    DTUsuario BuscarEstudiante(int ci) throws Exception;
}
