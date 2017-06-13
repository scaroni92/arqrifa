package org.arqrifa.logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTMensaje;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.persistencia.FabricaPersistencia;
import org.arqrifa.exceptions.ArquitecturaRifaException;

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
            if (!reunion.isListado()) {
                throw new Exception("La lista no ha sido habilitada aún");
            }

            FabricaPersistencia.getPersistenciaReunion().agregarAsistencia(usuario, reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public List<DTReunion> listarIniciadas() {
        try {
            return FabricaPersistencia.getPersistenciaReunion().listarIniciadas();
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void agregar(DTReunion reunion) {
        try {
            verificarReunionNula(reunion);

            if (Util.compararFechas(reunion.getFecha(), new Date()) <= 0) {
                throw new Exception("Las reuniones deben agendarse con almenos un día de anticipación.");
            }
            FabricaPersistencia.getPersistenciaReunion().agregar(reunion);

            notificarMailEstudiantes(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    private void notificarMailEstudiantes(DTReunion reunion) throws MessagingException, Exception {
        List<DTMensaje> mensajes = new ArrayList();
        String asunto = "Nueva reunión agendada";
        String mensaje = " te informamos que se ha agendado una nueva reunión para el día "
                + new SimpleDateFormat("dd 'de' MMMMM 'a las' HH:mm 'hrs.'").format(reunion.getFecha());

        for (DTUsuario usuario : FabricaPersistencia.getPersistenciaUsuario().listarEstudiantes(reunion.getGeneracion())) {
            mensajes.add(new DTMensaje(usuario.getEmail(), asunto, "Hola " + usuario.getNombre() + mensaje));
        }

        Util.notificarMail(mensajes);
    }

    private static void verificarReunionNula(DTReunion reunion) throws Exception {
        if (reunion == null) {
            throw new Exception("No se puede modificar una reunión nula");
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
            verificarReunionNula(reunion);

            if (!reunion.isPendiente()) {
                throw new Exception("No se puede volver a iniciar una reunión");
            }

            Date fechaActual = new Date();

            if (Util.compararFechas(reunion.getFecha(), fechaActual) != 0) {
                throw new Exception("No se puede iniciar una reunión fuera de fecha");
            }

            if (reunion.getFecha().after(fechaActual)) {
                throw new Exception("No se puede iniciar una reunión antes de la hora prevista");
            }

            reunion.setEstado(DTReunion.INICIADA);
            FabricaPersistencia.getPersistenciaReunion().modificar(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void finalizar(DTReunion reunion) {
        try {
            verificarReunionNula(reunion);
            if (!reunion.isIniciada()) {
                throw new Exception("El estado de la reunión debe ser iniciada");
            }
            if (reunion.getResoluciones().isEmpty()) {
                throw new Exception("Ingrese alguna resolución de la reunión");
            }

            reunion.setEstado(DTReunion.FINALIZADA);
            FabricaPersistencia.getPersistenciaReunion().modificar(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTReunion buscarUltimaReunionFinalizada(int genId) {
        try {
            return FabricaPersistencia.getPersistenciaReunion().buscarUltimaReunionFinalizada(genId);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTReunion BuscarReunionActual(int genId) {
        try {
            return FabricaPersistencia.getPersistenciaReunion().buscarReunionActual(genId);
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
            List<DTUsuario> estudiantes = FabricaPersistencia.getPersistenciaUsuario().listarEstudiantes(reunion.getGeneracion());

            for (DTUsuario estudiante : estudiantes) {
                boolean encontrado = false;
                for (DTUsuario participante : reunion.getParticipantes()) {
                    if (estudiante.getCi() == participante.getCi()) {
                        encontrado = true;
                        break;
                    }
                }
                asistencias.add(new DTAsistencia(estudiante, encontrado ? DTAsistencia.PRESENTE : DTAsistencia.AUSENTE));
            }
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
        return asistencias;
    }

    @Override
    public DTReunion buscarProximaReunionPorRealizar(int genId) {
        try {
            return FabricaPersistencia.getPersistenciaReunion().buscarProximaReunion(genId);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void eliminar(DTReunion reunion) {
        try {
            if (!reunion.isPendiente()) {
                throw new Exception("No se puede eliminar la reunión porque ya fue iniciada");
            }
            FabricaPersistencia.getPersistenciaReunion().eliminar(reunion);

        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void modificar(DTReunion reunion) {
        try {
            verificarReunionNula(reunion);
            if (!reunion.isPendiente()) {
                throw new Exception("No se puede modificar la reunión porque ya fue iniciada");
            }

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
            verificarReunionNula(reunion);
            if (!reunion.isIniciada()) {
                throw new Exception("El estado de la reunión deber ser iniciada");
            }
            reunion.setEstado(DTReunion.LISTADO);
            FabricaPersistencia.getPersistenciaReunion().modificar(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void deshabilitarLista(DTReunion reunion) {
        try {
            verificarReunionNula(reunion);
            if (!reunion.isListado()) {
                throw new Exception("No se puede deshabilitar la lista porque ya está deshabilitada");
            }
            reunion.setEstado(DTReunion.INICIADA);
            FabricaPersistencia.getPersistenciaReunion().modificar(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

}
