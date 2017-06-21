package org.arqrifa.logica;

import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTVotacion;

public interface IControladorEncuesta {

    void agregar(DTReunion reunion);

    void eliminar(DTReunion encuesta);

    void modificar(DTReunion encuesta);

    void agregarVoto(DTVotacion voto);
    
    DTVotacion buscarVoto(int ci, int reunionId);
}
