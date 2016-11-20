package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;

public interface IControladorGeneracion {

    void agregarGeneracion(DTGeneracion generacion);

    List<DTGeneracion> listarGeneraciones();

}
