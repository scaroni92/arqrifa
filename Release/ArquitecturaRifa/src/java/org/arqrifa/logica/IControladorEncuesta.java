package org.arqrifa.logica;

import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTVoto;

public interface IControladorEncuesta {
    void agregarEncuesta(DTReunion reunion);
    void modificarEncuesta(DTEncuesta encuesta);
    void eliminarEncuesta(DTReunion reunion);
    void agregarVoto(DTVoto voto);
    DTEncuesta buscarEncuesta(int id);
    void habilitarVotacionEncuesta(DTReunion reunion);
}
