package org.arqrifa.persistencia;

import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;

public interface IPersistenciaGeneracion {
    List<DTSolicitud> listarSolicitudes(int generacion) throws Exception;
}
