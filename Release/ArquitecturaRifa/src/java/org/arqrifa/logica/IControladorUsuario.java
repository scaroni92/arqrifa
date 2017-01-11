package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTUsuario;

public interface IControladorUsuario {

    void agregarEncargado(DTUsuario usuario);

    DTUsuario autenticar(int ci, String contrasena);
    
    List<DTUsuario> listarEstudiantes(int id_gen);
   
    DTUsuario buscarUsuario(int ci);

}
