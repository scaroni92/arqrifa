package org.arqrifa.logica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import org.arqrifa.datatypes.DTEstadoAsistencia;
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
                throw new Exception("El usuario CI: " + usuario.getCi() + " desea marcar asistencia pero no es estudiante.");
            }

            /* TODO: SE COMENTA ESTA VALIDACIÓN PARA HACER PRUEBAS
            if (!reunion.getEstado().equals(DTReunion.LISTADO)) {
                throw new Exception("La lista no se ha sido habilitada aún.");
            }*/
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
            if (reunion == null) {
                throw new Exception("No se puede agendar una reunión nula.");
            }
            if (formatearFecha(reunion.getFecha()).compareTo(formatearFecha(new Date())) <= 0) {
                throw new Exception("Las reuniones deben agendarse con almenos un día de anticipación.");
            }
            FabricaPersistencia.getPersistenciaReunion().agregar(reunion);
            notificarEstudiantesMail(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    private Date formatearFecha(Date fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(sdf.format(fecha));
    }

    private void notificarEstudiantesMail(DTReunion reunion) throws MessagingException {
        String asunto = "¡Nueva reunión agendada!";
        String mensaje = "Hola te informamos que se ha agendado una nueva reunión para el día "
                + new SimpleDateFormat("dd 'de' MMMMM 'a las' HH:mm 'hrs.'").format(reunion.getFecha());

        Mensajeria mensajeria = new Mensajeria();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    for (DTUsuario usuario : FabricaPersistencia.getPersistenciaUsuario().listarEstudiantes(reunion.getGeneracion())) {
                        mensajeria.enviar(new DTMensaje(usuario.getEmail(), asunto, mensaje));
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };

        Thread hilo = new Thread(runnable);
        hilo.start();
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
            if (reunion == null) {
                throw new Exception("No se puede iniciar una reunión nula.");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date fechaReunion = sdf.parse(sdf.format(reunion.getFecha()));
            Date fechaActual = new Date();

            if (fechaReunion.compareTo(sdf.parse(sdf.format(fechaActual))) != 0) {
                throw new Exception("No se puede iniciar una reunión fuera de fecha.");
            }

            if (reunion.getFecha().before(fechaActual)) {
                throw new Exception("No se puede iniciar la reunión antes de la fecha y hora prevista.");
            }

            FabricaPersistencia.getPersistenciaReunion().iniciar(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void finalizar(DTReunion reunion) {
        try {
            if (reunion == null) {
                throw new Exception("No se puede finalizar una reunión nula");
            }
            if (reunion.getResoluciones().isEmpty()) {
                throw new Exception("Ingrese alguna resolución de la reunión");
            }

            FabricaPersistencia.getPersistenciaReunion().finalizar(reunion);
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
    public List<DTReunion> listarPorGeneracion(int genId) {
        try {
            return FabricaPersistencia.getPersistenciaReunion().listarPorGeneracion(genId);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public List<DTEstadoAsistencia> listarAsistencias(DTReunion reunion) {
        List<DTEstadoAsistencia> asistencias = new ArrayList();
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
                asistencias.add(new DTEstadoAsistencia(estudiante, encontrado ? DTEstadoAsistencia.PRESENTE : DTEstadoAsistencia.AUSENTE));
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
            if (reunion.getEstado().equals(DTReunion.INICIADA)) {
                throw new Exception("No se puede eliminar una reunión en progreso.");
            }
            FabricaPersistencia.getPersistenciaReunion().eliminar(reunion);

        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void modificar(DTReunion reunion) {
        try {
            if (reunion == null) {
                throw new Exception("No se puede modificar uan reunión nula.");
            }
            if (!reunion.getEstado().equals(DTReunion.PENDIENTE)) {
                throw new Exception("No se puede modificar una reunión que ya fue iniciada.");
            }
             if (formatearFecha(reunion.getFecha()).compareTo(formatearFecha(new Date())) < 0) {
                throw new Exception("No se puede asignar una fehca menor a la actual.");
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

}
