package org.arqrifa.persistencia;

import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTVoto;

public interface IPersistenciaEncuesta {
    public void agregarEncuesta(DTReunion reunion) throws Exception;
    public void eliminarEncuesta(DTEncuesta encuesta) throws Exception;
    public void modificarEncuesta(DTEncuesta encuesta) throws Exception;
    public void habilitarVotacion(DTEncuesta encuesta) throws Exception;
    public void agregarVoto(DTVoto voto) throws Exception;
    public DTEncuesta buscar(int id) throws Exception;
}
