package org.arqrifa.persistencia;

import java.util.List;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;

public interface IPersistenciaReunion {
    
    void agregar(DTReunion reunion) throws Exception;
    
    void eliminar(DTReunion reunion) throws Exception;
    
    void modificar(DTReunion reunion) throws Exception;
    
    void iniciar(DTReunion reunion) throws Exception;
    
    void habilitarLista(DTReunion reunion) throws Exception;
    
    void deshabilitarLista(DTReunion reunion) throws Exception;
    
    void finalizar(DTReunion reunion) throws Exception;
    
    void agregarAsistencia(DTUsuario estudiante, DTReunion reunion) throws Exception;
    
    DTReunion buscar(int id) throws Exception;
    
    DTReunion buscarUltimaReunionFinalizada(int id_gen) throws Exception;
    
    DTReunion buscarProximaReunion(int id_gen) throws Exception;
    
    DTReunion buscarReunionPorFecha(int id_gen, String fecha) throws Exception;
    
    List<DTReunion> listarTodas() throws Exception;
    
    List<DTReunion> listarIniciadas() throws Exception;
    
    List<DTReunion> listarPorGeneracion(int id_gen) throws Exception;
}
