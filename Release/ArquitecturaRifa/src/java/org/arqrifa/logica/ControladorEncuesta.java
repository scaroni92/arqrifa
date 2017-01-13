package org.arqrifa.logica;

import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTVoto;
import org.arqrifa.excepciones.ArquitecturaRifaException;
import org.arqrifa.persistencia.FabricaPersistencia;

public class ControladorEncuesta implements IControladorEncuesta {

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    private static ControladorEncuesta instancia = null;

    public static ControladorEncuesta getInstancia() {
        if (instancia == null) {
            instancia = new ControladorEncuesta();
        }
        return instancia;
    }

    private ControladorEncuesta() {
    }
    //</editor-fold>

    @Override
    public void agregarEncuesta(DTReunion reunion) {
        try {
            if (reunion == null) {
                throw new Exception("La reunión no puede ser nula.");
            }
            if (reunion.getEstado().equals(DTReunion.FINALIZADA)) {
                throw new Exception("No se puede crear encuestas para reuniones finalizadas.");
            }
            if (reunion.getEncuesta() == null) {
                throw new Exception("La encuesta no puede ser nula.");
            }
            if (reunion.getEncuesta().getPropuestas().isEmpty()) {
                throw new Exception("No se puede crear una encuesta sin propuestas.");
            }
            for (DTPropuesta propuesta : reunion.getEncuesta().getPropuestas()) {
                if (propuesta.getRespuestas().size() < 2) {
                    throw new Exception("Todas las propuestas deben tener almenos dos respuestas.");
                }
            }

            FabricaPersistencia.getPersistenciaEncuesta().agregar(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void eliminarEncuesta(DTEncuesta encuesta) {
        try {

            if (encuesta.isHabilitada()) {
                throw new Exception("No se puede eliminar una encuesta que ya fue habilitada.");
            }
            FabricaPersistencia.getPersistenciaEncuesta().eliminar(encuesta);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void modificarEncuesta(DTEncuesta encuesta) {
        try {
            if (encuesta.isHabilitada()) {
                throw new Exception("No se puede modificar una encuesta que ya fue habilitada.");
            }
            if (encuesta.getPropuestas().isEmpty()) {
                throw new Exception("Agregue almenos una propuesta a la encuesta.");
            }
            for (int i = 0; i < encuesta.getPropuestas().size(); i++) {
                if (encuesta.getPropuestas().get(i).getRespuestas().size() < 2) {
                    throw new Exception("Ingrese almenos dos respuestas para la propuesta " + i);
                }
            }
            FabricaPersistencia.getPersistenciaEncuesta().modificar(encuesta);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void habilitarVotacion(DTReunion reunion) {
        try {
            if (!reunion.getEstado().equals(DTReunion.INICIADA)) {
                throw new Exception("No es posible habilitar la votación de una encuesta si la reunión no está en progreso.");
            }
            if (reunion.getEncuesta().isHabilitada()) {
                throw new Exception("No es posible habilitar la votación de la encuesta si ésta ya fue habilitada.");
            }
            FabricaPersistencia.getPersistenciaEncuesta().habilitar(reunion.getEncuesta());
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void agregarVoto(DTVoto voto) {
        try {
            FabricaPersistencia.getPersistenciaEncuesta().votar(voto);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTEncuesta buscarEncuesta(int encuestaId) {
        try {
            return FabricaPersistencia.getPersistenciaEncuesta().buscar(encuestaId);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

}
