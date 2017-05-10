package org.arqrifa.logica;

import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTVoto;
import org.arqrifa.exceptions.ArquitecturaRifaException;
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
    public void eliminarEncuesta(DTReunion reunion) {
        try {
            if (reunion.getEstado().equals(DTReunion.FINALIZADA)) {
                throw new Exception("No se puede eliminar la encuesta de una reunión finalizada");
            }
            if (reunion.getEncuesta().isHabilitada()) {
                throw new Exception("No se puede eliminar una encuesta que ya fue habilitada.");
            }
            FabricaPersistencia.getPersistenciaEncuesta().eliminar(reunion.getEncuesta());
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void modificarEncuesta(DTReunion reunion) {
        try {
            if (reunion.getEstado().equals(DTReunion.FINALIZADA)) {
                throw new Exception("No se puede modificar la encuesta de una reunión finalizada");
            }
            if (reunion.getEncuesta().isHabilitada()) {
                throw new Exception("No se puede modificar una encuesta que ya fue habilitada.");
            }
            if (reunion.getEncuesta().getPropuestas().isEmpty()) {
                throw new Exception("Agregue almenos una propuesta a la encuesta.");
            }

            for (DTPropuesta propuesta : reunion.getEncuesta().getPropuestas()) {
                if (propuesta.getRespuestas().size() < 2) {
                    throw new Exception("Todas las propuestas deben tener almenos dos respuestas");
                }
            }
            FabricaPersistencia.getPersistenciaEncuesta().modificar(reunion.getEncuesta());
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void habilitarVotacion(DTReunion reunion) {
        try {
            if (!reunion.getEstado().equals(DTReunion.INICIADA)) {
                throw new Exception("No se puede habilitar la votación si el estado de la reunión es " + reunion.getEstado().toLowerCase());
            }
            if (!reunion.getEncuesta().isHabilitada()) {
                FabricaPersistencia.getPersistenciaEncuesta().habilitar(reunion.getEncuesta());
            }
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void deshabilitarVotacion(DTReunion reunion) {
        try {
            if (reunion.getEncuesta().isHabilitada()) {
                FabricaPersistencia.getPersistenciaEncuesta().deshabilitar(reunion.getEncuesta());
            }
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void agregarVoto(DTVoto voto) {
        boolean esParticipante = false;
        try {
            for (DTUsuario participante : voto.getReunion().getParticipantes()) {
                if (participante.getCi() == voto.getUsuario().getCi()) {
                    esParticipante = true;
                }
            }
            if (!esParticipante) {
                throw new Exception("Solo los participantes pueden votar");
            }
            FabricaPersistencia.getPersistenciaEncuesta().votar(voto);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTEncuesta buscarEncuesta(int encuestaId) {
        DTEncuesta encuesta = null;
        try {
            encuesta = FabricaPersistencia.getPersistenciaEncuesta().buscar(encuestaId);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
        return encuesta;
    }

}
