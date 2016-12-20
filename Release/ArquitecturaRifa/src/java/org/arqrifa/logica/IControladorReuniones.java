package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTVoto;

public interface IControladorReuniones {

    void agregarReunion(DTReunion reunion);

    void iniciarReunion(DTReunion reunion);
    
    void finalizarReunion(DTReunion reunion);

    void MarcarAsistencia(DTUsuario usuario, DTReunion reunion);
    
    void agregarEncuesta(DTReunion reunion);
    
    void habilitarVotacion(DTReunion reunion);
    
    void agregarVoto(DTVoto voto);

    DTReunion buscarReunion(int id);
    
    List<DTReunion> listarReunionesIniciadas();

}
