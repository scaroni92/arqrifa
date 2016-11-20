package org.arqrifa.logica;

import org.arqrifa.datatypes.DTUsuario;

public interface IControladorUsuario {

    void agregarEncargado(DTUsuario usuario);

    DTUsuario autenticar(int ci, String contrasena);

}
