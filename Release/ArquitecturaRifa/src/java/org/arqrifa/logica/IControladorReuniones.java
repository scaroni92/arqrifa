package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;

public interface IControladorReuniones {

    void agregarReunion(DTReunion reunion);

    void iniciarReunion(DTReunion reunion);
    
    void finalizarReunion(DTReunion reunion);

    void MarcarAsistencia(DTUsuario usuario, DTReunion reunion);
    
    void agregarEncuesta(DTReunion reunion);

    DTReunion buscarReunion(int id);

    List<DTReunion> listarReunionesIniciadas();

}
