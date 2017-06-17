package org.arqrifa.logica;

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
    public void agregar(DTReunion reunion) {
        try {
            verificarReunionNula(reunion);
            verificarEncuestaNula(reunion);

            if (reunion.isFinalizada()) {
                throw new Exception("No se puede crear encuestas para reuniones finalizadas");
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

    private void verificarEncuestaNula(DTReunion reunion) throws Exception {
        if (reunion.getEncuesta() == null) {
            throw new Exception("La encuesta no puede ser nula");
        }
    }

    private void verificarReunionNula(DTReunion reunion) throws Exception {
        if (reunion == null) {
            throw new Exception("La reunión no puede ser nula");
        }
    }

    @Override
    public void eliminar(DTReunion reunion) {
        try {
            if (!reunion.isPendiente()) {
                throw new Exception("No se puede eliminar la encuesta si la reunión ya fue iniciada");
            }
            verificarEncuestaNula(reunion);
            FabricaPersistencia.getPersistenciaEncuesta().eliminar(reunion.getEncuesta());
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void modificar(DTReunion reunion) {
        try {
            if (!reunion.isPendiente()) {
                throw new Exception("No se puede modificar la encuesta si la reunión ya fue iniciada");
            }
            verificarEncuestaNula(reunion);
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
    public void agregarVoto(DTVoto voto) {
        boolean esParticipante = false;
        try {
            if (!voto.getReunion().isVotacion()) {
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
    public DTVoto buscarVoto(int ci, int reunionId) {
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
            
            return FabricaPersistencia.getPersistenciaEncuesta().buscarVoto(usuario, reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

}
