package org.arqrifa.logica;

import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTVotacion;
import org.arqrifa.exceptions.ArquitecturaRifaException;
import org.arqrifa.logica.validation.EncuestaValidator;
import org.arqrifa.logica.validation.EncuestaValidatorType;
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
    public void agregar(DTReunion reunion) {
        try {
            EncuestaValidator.validate(reunion, EncuestaValidatorType.ALTA);
            FabricaPersistencia.getPersistenciaEncuesta().agregar(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void eliminar(DTReunion reunion) {
        try {
            EncuestaValidator.validate(reunion, EncuestaValidatorType.BAJA);
            FabricaPersistencia.getPersistenciaEncuesta().eliminar(reunion.getEncuesta());
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void modificar(DTReunion reunion) {
        try {
            EncuestaValidator.validate(reunion, EncuestaValidatorType.MODIFICAR);
            FabricaPersistencia.getPersistenciaEncuesta().modificar(reunion.getEncuesta());
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void agregarVotacion(DTVotacion votacion) {
        boolean esParticipante = false;
        try {
            if (!votacion.getReunion().isVotacion()) {
                throw new Exception("La encuesta no está habilitada para votaciones");
            }

            for (DTUsuario participante : votacion.getReunion().getParticipantes()) {
                if (participante.getCi() == votacion.getUsuario().getCi()) {
                    esParticipante = true;
                }
            }
            if (!esParticipante) {
                throw new Exception("Solo los participantes pueden votar");
            }
            FabricaPersistencia.getPersistenciaEncuesta().agregarVotacion(votacion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTVotacion buscarVotacion(int ci, int reunionId) {
        try {
            DTUsuario usuario = FabricaLogica.getLogicaUsuario().buscar(ci);
            DTReunion reunion = FabricaLogica.getControladorReuniones().buscar(reunionId);

            if (!reunion.isVotacion()) {
                throw new Exception("La votación no ha sido habilitada");
            }

            if (usuario == null) {
                throw new Exception("Estudiante no encontrado");
            }

            if (!usuario.getRol().equals(DTUsuario.ESTUDIANTE)) {
                throw new Exception("Solo estudiantes pueden votar en la encuesta");
            }

            return FabricaPersistencia.getPersistenciaEncuesta().buscarVotacion(usuario, reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

}
