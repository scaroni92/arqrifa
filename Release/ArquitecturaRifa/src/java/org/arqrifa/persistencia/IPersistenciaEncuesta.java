package org.arqrifa.persistencia;

import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTReunion;

public interface IPersistenciaEncuesta {
    public void alta(DTReunion reunion) throws Exception;
    public void habilitarVotacion(DTEncuesta encuesta) throws Exception;
}
