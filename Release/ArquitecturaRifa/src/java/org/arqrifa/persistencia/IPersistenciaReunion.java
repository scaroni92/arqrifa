package org.arqrifa.persistencia;

import java.util.List;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;

public interface IPersistenciaReunion {
    
    void agregar(DTReunion reunion) throws Exception;
    
    void eliminar(DTReunion reunion) throws Exception;
    
    void modificar(DTReunion reunion) throws Exception;
    
    void cambiarEstado(DTReunion reunion) throws Exception;
    
    void agregarAsistencia(DTUsuario estudiante, DTReunion reunion) throws Exception;
    
    DTReunion buscar(int id) throws Exception;
    
    DTReunion buscarUltimaFinalizada(int id_gen) throws Exception;
    
    DTReunion buscarProxima(int id_gen) throws Exception;
    
    DTReunion buscarActual(int id_gen) throws Exception;
    
    List<DTReunion> listarTodas() throws Exception;
    
    
    List<DTReunion> listarPorGeneracion(int id_gen) throws Exception;
}
