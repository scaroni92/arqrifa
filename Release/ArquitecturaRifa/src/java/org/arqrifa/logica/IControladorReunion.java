package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTEstadoAsistencia;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
public interface IControladorReunion {

    void agregar(DTReunion reunion);
    
    void eliminar(DTReunion reunion);
    
    void modificar(DTReunion reunion);

    void iniciar(DTReunion reunion);
    
    void finalizar(DTReunion reunion);

    void agregarAsistencia(DTUsuario usuario, DTReunion reunion);
    
    DTReunion buscar(int id);
    
    DTReunion buscarUltimaReunionFinalizada(int genId);
    
    DTReunion buscarProximaReunionPorRealizar(int genId);
    
    DTReunion BuscarReunionDelDia(int genId);
    
    List<DTReunion> listarTodas();
    
    List<DTReunion> listarIniciadas();
    
    List<DTReunion> listarPorGeneracion(int genId);
    
    List<DTEstadoAsistencia> listarAsistencias(DTReunion reunion);
    
}
