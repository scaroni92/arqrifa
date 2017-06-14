package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;

public interface IControladorReunion {

    void agregar(DTReunion reunion);

    void eliminar(DTReunion reunion);

    void modificar(DTReunion reunion);

    void iniciar(DTReunion reunion);

    void habilitarLista(DTReunion reunion);

    void deshabilitarLista(DTReunion reunion);
    
     void habilitarVotacion(DTReunion reunion);
    
    void deshabilitarVotacion(DTReunion reunion);

    void finalizar(DTReunion reunion);

    void agregarAsistencia(DTUsuario usuario, DTReunion reunion);

    DTReunion buscar(int id);

    DTReunion buscarUltimaReunionFinalizada(int genId);

    DTReunion buscarProximaReunionPorRealizar(int genId);

    DTReunion BuscarReunionActual(int genId);

    List<DTReunion> listarTodas();

    List<DTReunion> listarPorGeneracion(int genId);

    List<DTAsistencia> listarAsistencias(DTReunion reunion);

}
