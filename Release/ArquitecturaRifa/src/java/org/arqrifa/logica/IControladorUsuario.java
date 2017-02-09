package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTUsuario;

public interface IControladorUsuario {

    void agregar(DTUsuario usuario);

    void eliminar(DTUsuario usuario);

    void modificar(DTUsuario usuario);

    DTUsuario autenticar(int ci, String contrasena);

    DTUsuario buscar(int ci);

    List<DTUsuario> listarTodos();

    List<DTUsuario> listarEstudiantes(int id_gen);

}
