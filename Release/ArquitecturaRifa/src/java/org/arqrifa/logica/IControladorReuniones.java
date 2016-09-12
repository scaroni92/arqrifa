package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;

public interface IControladorReuniones {

    void MarcarAsistencia(DTUsuario usuario, DTReunion reunion);

    List<DTReunion> getReunionesActivas();
}
