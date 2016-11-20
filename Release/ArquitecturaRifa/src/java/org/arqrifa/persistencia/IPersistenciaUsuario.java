package org.arqrifa.persistencia;

import java.util.List;
import org.arqrifa.datatypes.DTUsuario;

public interface IPersistenciaUsuario {

    void agregar(DTUsuario usuario) throws Exception;

    DTUsuario autenticar(int ci, String contrasena) throws Exception;

    DTUsuario buscarEstudiante(int ci) throws Exception;

    List<DTUsuario> listarEstudiantes(int generacion) throws Exception;
}
