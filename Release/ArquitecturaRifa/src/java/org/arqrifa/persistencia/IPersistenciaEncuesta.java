package org.arqrifa.persistencia;

import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTVotacion;

public interface IPersistenciaEncuesta {
    public void agregar(DTReunion reunion) throws Exception;
    public void eliminar(DTEncuesta encuesta) throws Exception;
    public void modificar(DTEncuesta encuesta) throws Exception;
    public void agregarVotacion(DTVotacion votacion) throws Exception;
    public DTEncuesta buscarPorReunion(int reunionId) throws Exception;
    public DTVotacion buscarVotacion(DTUsuario usuario, DTReunion reunion) throws Exception;
}
