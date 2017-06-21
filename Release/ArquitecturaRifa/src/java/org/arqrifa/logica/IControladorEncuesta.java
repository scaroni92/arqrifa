package org.arqrifa.logica;

import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTVotacion;

public interface IControladorEncuesta {

    void agregar(DTReunion reunion);

    void eliminar(DTReunion encuesta);

    void modificar(DTReunion encuesta);

    void agregarVotacion(DTVotacion votacion);
    
    DTVotacion buscarVotacion(int ci, int reunionId);
}
