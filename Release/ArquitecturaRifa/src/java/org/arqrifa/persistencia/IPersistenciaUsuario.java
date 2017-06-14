package org.arqrifa.persistencia;

import java.util.List;
import org.arqrifa.datatypes.DTUsuario;

public interface IPersistenciaUsuario {

    void agregar(DTUsuario usuario) throws Exception;
        
    void modificar(DTUsuario usuario) throws Exception;

    DTUsuario autenticar(int ci, String contrasena) throws Exception;

    DTUsuario buscar(int ci) throws Exception;

    List<DTUsuario> listarTodos() throws Exception;

    List<DTUsuario> listarEstudiantes(int generacion) throws Exception;

}
