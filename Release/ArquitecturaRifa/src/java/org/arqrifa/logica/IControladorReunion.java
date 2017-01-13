package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTEstadoAsistencia;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
public interface IControladorReunion {

    void agregarReunion(DTReunion reunion);
    
    void eliminarReunion(DTReunion reunion);
    
    void modificarReunion(DTReunion reunion);

    void iniciarReunion(DTReunion reunion);
    
    void finalizarReunion(DTReunion reunion);

    void agregarAsistencia(DTUsuario usuario, DTReunion reunion);
    
    DTReunion buscarReunion(int id);
    
    DTReunion buscarUltimaReunionFinalizada(int id_gen);
    
    DTReunion buscarProximaReunionPorRealizar(int id_gen);
    
    List<DTReunion> listarReunionesIniciadas();
    
    List<DTReunion> listarReunionesPorGeneracion(int id_gen);
    
    List<DTEstadoAsistencia> listarAsistencias(DTReunion r);
    
}
