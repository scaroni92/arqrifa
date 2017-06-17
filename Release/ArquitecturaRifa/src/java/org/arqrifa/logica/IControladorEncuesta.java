package org.arqrifa.logica;

import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTVoto;

public interface IControladorEncuesta {

    void agregar(DTReunion reunion);

    void eliminar(DTReunion encuesta);

    void modificar(DTReunion encuesta);

    void agregarVoto(DTVoto voto);
    
    DTVoto buscarVoto(int ci, int reunionId);
}
