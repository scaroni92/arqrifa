package org.arqrifa.persistencia;

import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTVoto;

public interface IPersistenciaEncuesta {
    public void agregar(DTReunion reunion) throws Exception;
    public void eliminar(DTEncuesta encuesta) throws Exception;
    public void modificar(DTEncuesta encuesta) throws Exception;
    public void habilitar(DTEncuesta encuesta) throws Exception;
    public void deshabilitar(DTEncuesta encuesta) throws Exception;
    public void votar(DTVoto voto) throws Exception;
    public DTEncuesta buscar(int id) throws Exception;
    public DTEncuesta buscarPorReunion(int reunionId) throws Exception;
    
}
