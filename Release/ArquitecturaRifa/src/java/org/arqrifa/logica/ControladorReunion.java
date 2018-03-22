package org.arqrifa.logica;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.persistencia.FabricaPersistencia;
import org.arqrifa.exceptions.ArquitecturaRifaException;
import org.arqrifa.logica.validation.ReunionValidator;
import org.arqrifa.logica.validation.ReunionValidatorType;
import org.arqrifa.notificaciones.Notificaciones;

class ControladorReunion implements IControladorReunion {

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    private static ControladorReunion instancia = null;

    public static IControladorReunion getInstancia() {
        if (instancia == null) {
            instancia = new ControladorReunion();
        }
        return instancia;
    }

    private ControladorReunion() {
    }
    //</editor-fold>

    @Override
    public void agregarAsistencia(DTUsuario usuario, DTReunion reunion) {
        try {
            if (!usuario.getRol().equals("Estudiante")) {
                throw new Exception("Solo los estudiantes pueden marcar asistencia");
            }

            reunion = buscar(reunion.getId());

            if (!reunion.isListado()) {
                throw new Exception("La lista no ha sido habilitada aún");
            }

            FabricaPersistencia.getPersistenciaReunion().agregarAsistencia(usuario, reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void agregar(DTReunion reunion) {
        try {
            ReunionValidator.validate(reunion, ReunionValidatorType.ALTA);
            FabricaPersistencia.getPersistenciaReunion().agregar(reunion);
            Notificaciones.notificarNuevaReunion(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTReunion buscar(int id) {
        try {
            return FabricaPersistencia.getPersistenciaReunion().buscar(id);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void iniciar(DTReunion reunion) {
        try {
            ReunionValidator.validate(reunion, ReunionValidatorType.INICIO);

            reunion.setEstado(DTReunion.ESTADO_INICIADA);
            FabricaPersistencia.getPersistenciaReunion().cambiarEstado(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void finalizar(DTReunion reunion) {
        try {
            ReunionValidator.validate(reunion, ReunionValidatorType.FIN);
            reunion.setEstado(DTReunion.ESTADO_FINALIZADA);
            FabricaPersistencia.getPersistenciaReunion().modificar(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTReunion buscarUltimaFinalizada(int genId) {
        try {
            return FabricaPersistencia.getPersistenciaReunion().buscarUltimaFinalizada(genId);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTReunion BuscarActual(int genId) {
        try {
            return FabricaPersistencia.getPersistenciaReunion().buscarActual(genId);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public List<DTReunion> listarPorGeneracion(int genId) {
        try {
            return FabricaPersistencia.getPersistenciaReunion().listarPorGeneracion(genId);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public List<DTAsistencia> listarAsistencias(DTReunion reunion) {
        List<DTAsistencia> asistencias = new ArrayList();
        try {
            List<DTUsuario> estudiantes = ControladorUsuario.getInstancia().listarEstudiantes(reunion.getGeneracion());
            for (DTUsuario estudiante : estudiantes) {
                asistencias.add(new DTAsistencia(estudiante, obtenerEstadoAsistencia(reunion, estudiante)));
            }
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
        return asistencias;
    }

    private String obtenerEstadoAsistencia(DTReunion reunion, DTUsuario estudiante) {
        for (DTUsuario participante : reunion.getParticipantes()) {
            if (participante.getCi() == estudiante.getCi()) {
                return DTAsistencia.PRESENTE;
            }
        }
        return DTAsistencia.AUSENTE;
    }

    @Override
    public DTReunion buscarProximaPorRealizar(int genId) {
        try {
            return FabricaPersistencia.getPersistenciaReunion().buscarProxima(genId);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void eliminar(DTReunion reunion) {
        try {
            ReunionValidator.validate(reunion, ReunionValidatorType.BAJA);
            FabricaPersistencia.getPersistenciaReunion().eliminar(reunion);

        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void modificar(DTReunion reunion) {
        try {
            ReunionValidator.validate(reunion, ReunionValidatorType.MODIFICAR);
            FabricaPersistencia.getPersistenciaReunion().modificar(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public List<DTReunion> listarTodas() {
        try {
            return FabricaPersistencia.getPersistenciaReunion().listarTodas();
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void habilitarLista(DTReunion reunion) {
        try {
            ReunionValidator.validate(reunion, ReunionValidatorType.HABILITAR_LISTA);
            reunion.setEstado(DTReunion.ESTADO_LISTADO);
            FabricaPersistencia.getPersistenciaReunion().cambiarEstado(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void deshabilitarLista(DTReunion reunion) {
        try {
            ReunionValidator.validate(reunion, ReunionValidatorType.DESHABILITAR_LISTA);
            reunion.setEstado(DTReunion.ESTADO_INICIADA);
            FabricaPersistencia.getPersistenciaReunion().cambiarEstado(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void habilitarVotacion(DTReunion reunion) {
        try {
            ReunionValidator.validate(reunion, ReunionValidatorType.HABILITAR_VOTACION);
            reunion.setEstado(DTReunion.ESTADO_VOTACION);
            FabricaPersistencia.getPersistenciaReunion().cambiarEstado(reunion);

        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void deshabilitarVotacion(DTReunion reunion) {
        try {
            ReunionValidator.validate(reunion, ReunionValidatorType.DESHABILITAR_VOTACION);
            reunion.setEstado(DTReunion.ESTADO_INICIADA);
            FabricaPersistencia.getPersistenciaReunion().cambiarEstado(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

}
