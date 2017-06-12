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
            if (reunion.getEstado().equals(DTReunion.VOTACION)) {
                throw new Exception("No se puede eliminar una encuesta habilitada.");
            }
            FabricaPersistencia.getPersistenciaEncuesta().eliminar(reunion.getEncuesta());
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void modificarEncuesta(DTReunion reunion) {
        try {
            if (reunion.getEstado().equals(DTReunion.INICIADA)) {
                throw new Exception("No se puede modificar la encuesta si la reunión ya fue iniciada");
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
                throw new Exception("El estado de la reunión debe ser iniciada");
            }
            reunion.setEstado(DTReunion.VOTACION);
            FabricaPersistencia.getPersistenciaReunion().modificar(reunion);

        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void deshabilitarVotacion(DTReunion reunion) {
        try {
            if (!reunion.getEstado().equals(DTReunion.VOTACION)) {
                throw new Exception("La encuesta no ha sido habilitada aún");
            }
            reunion.setEstado(DTReunion.INICIADA);
            FabricaPersistencia.getPersistenciaReunion().modificar(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void agregarVoto(DTVoto voto) {
        boolean esParticipante = false;
        try {
            if (!voto.getReunion().getEstado().equals(DTReunion.VOTACION)) {
                throw new Exception("La encuesta no está habilitada para votaciones");
            }
            //TODO: verificar funcionamiento
            for (DTUsuario participante : voto.getReunion().getParticipantes()) {
                if (participante.getCi() == voto.getUsuario().getCi()) {
                    esParticipante = true;
                }
            }
            if (!esParticipante) {
                throw new Exception("Solo los participantes pueden votar");
            }
            FabricaPersistencia.getPersistenciaEncuesta().agregarVoto(voto);
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
