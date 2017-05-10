package org.arqrifa.logica;

import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTVoto;

public interface IControladorEncuesta {

    void agregarEncuesta(DTReunion reunion);

    void eliminarEncuesta(DTReunion encuesta);

    void modificarEncuesta(DTReunion encuesta);

    void habilitarVotacion(DTReunion reunion);
    
    void deshabilitarVotacion(DTReunion reunion);

    void agregarVoto(DTVoto voto);

    DTEncuesta buscarEncuesta(int id);
}
