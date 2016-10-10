package org.arqrifa.persistencia;

import org.arqrifa.datatypes.DTUsuario;

public interface IPersistenciaUsuario {

    DTUsuario autenticar(int ci, String contrasena) throws Exception;
    
    DTUsuario buscarEstudiante(int ci) throws Exception;
}
