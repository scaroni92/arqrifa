package org.arqrifa.logica;

import org.arqrifa.datatypes.DTUsuario;

public interface ILogicaUsuario {

    DTUsuario Autenticar(int ci, String contrasena);

}
