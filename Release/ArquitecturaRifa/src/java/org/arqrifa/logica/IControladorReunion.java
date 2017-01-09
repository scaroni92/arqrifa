package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTVoto;

public interface IControladorReunion {

    void agregarReunion(DTReunion reunion);

    void iniciarReunion(DTReunion reunion);
    
    void finalizarReunion(DTReunion reunion);

    void agregarAsistencia(DTUsuario usuario, DTReunion reunion);
    
    void agregarEncuesta(DTReunion reunion);
    
    void habilitarVotacionEncuesta(DTReunion reunion);
    
    void agregarVoto(DTVoto voto);

    DTReunion buscarReunion(int id);
    
    DTReunion buscarUltimaReunionFinalizada(int id_gen);
    
    DTReunion buscarProximaReunionPorRealizar(int id_gen);
    
    List<DTReunion> listarReunionesIniciadas();
    
    List<DTReunion> listarReunionesPorGeneracion(int id_gen);
    
    List<DTAsistencia> listarAsistencias(DTReunion r);

}
