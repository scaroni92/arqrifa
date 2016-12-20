package org.arqrifa.logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.arqrifa.datatypes.DTEstado;
import org.arqrifa.datatypes.DTMensaje;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTVoto;
import org.arqrifa.persistencia.FabricaPersistencia;
import org.arqrifa.excepciones.ArquitecturaRifaException;

class ControladorReuniones implements IControladorReuniones {

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    private static ControladorReuniones instancia = null;

    public static IControladorReuniones getInstancia() {
        if (instancia == null) {
            instancia = new ControladorReuniones();
        }
        return instancia;
    }

    private ControladorReuniones() {
    }
    //</editor-fold>

    @Override
    public void MarcarAsistencia(DTUsuario usuario, DTReunion reunion) {
        try {
            if (!usuario.getRol().equals("Estudiante")) {
                throw new Exception("El usuario CI: " + usuario.getCi() + " desea marcar asistencia pero no es estudiante.");
            }

            if (!reunion.getEstado().equals(DTEstado.LISTADO)) {
                throw new Exception("La lista no se ha sido habilitada aún.");
            }

            FabricaPersistencia.getPersistenciaReunion().marcarAsistencia(usuario, reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    // Filtrar por GENERACION
    @Override
    public List<DTReunion> listarReunionesIniciadas() {
        List<DTReunion> reuniones = new ArrayList();
        try {
            reuniones = FabricaPersistencia.getPersistenciaReunion().listarIniciadas();
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
        return reuniones;
    }

    @Override
    public void agregarReunion(DTReunion reunion) {
        try {
            if (reunion == null) {
                throw new Exception("No se puede agendar una reunión nula.");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date fechaReunion = sdf.parse(sdf.format(reunion.getFecha()));
            Date fechaActual = sdf.parse(sdf.format(new Date()));

            if (fechaReunion.compareTo(fechaActual) <= 0) {
                throw new Exception("Las reunion deben agendarse con almenos un día de anticipación.");
            }

            FabricaPersistencia.getPersistenciaReunion().agregar(reunion);

            String asunto = "¡Nueva reunión agendada!";
            String mensaje = "Hola te informamos que se ha agendado una nueva reunión para el día "
                    + new SimpleDateFormat("dd 'de' MMMMM 'a las' HH:mm 'hrs.'").format(reunion.getFecha());

            Mensajeria mensajeria = new Mensajeria(new DTMensaje("", asunto, mensaje));

            for (DTUsuario usuario : FabricaPersistencia.getPersistenciaUsuario().listarEstudiantes(reunion.getGeneracion())) {
                mensajeria.getMensaje().setDestinatario(usuario.getEmail());
                mensajeria.enviar();
            }
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTReunion buscarReunion(int id) {
        DTReunion reunion = null;
        try {
            reunion = FabricaPersistencia.getPersistenciaReunion().buscar(id);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
        return reunion;
    }

    @Override
    public void iniciarReunion(DTReunion reunion) {
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
    public void finalizarReunion(DTReunion reunion) {
        try {
            if (reunion == null) {
                throw new Exception("No se puede finalizar una reunión nula");
            }

            FabricaPersistencia.getPersistenciaReunion().finalizar(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void agregarEncuesta(DTReunion reunion) {
        try {
            System.out.println(reunion.getEncuesta().getPropuestas().size());
            if (reunion == null) {
                throw new Exception("La reunión no puede ser nula.");
            }
            if (reunion.getEstado().equals(DTEstado.FINALIZADA)) {
                throw new Exception("No se puede crear encuestas para reuniones finalizadas.");
            }
            if (reunion.getEncuesta() == null) {
                throw new Exception("La encuesta no puede ser nula.");
            }
            if (reunion.getEncuesta().getPropuestas().isEmpty()) {
                throw new Exception("No se puede crear una encuesta sin propuestas.");
            }

            FabricaPersistencia.getPersistenciaEncuesta().alta(reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void habilitarVotacion(DTReunion reunion) {
        try {
            if (!reunion.getEstado().equals(DTEstado.INICIADA)) {
                throw new Exception("No se puede habilitar la votación de una reunión que no está en progreso.");
            }
            if (reunion.getEncuesta().isHabilitada()) {
                throw new Exception("No se puede habilitar la votación de una encuesta que ya fue habilitada.");
            }
            FabricaPersistencia.getPersistenciaEncuesta().habilitarVotacion(reunion.getEncuesta());
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void agregarVoto(DTVoto voto) {
        try {
            FabricaPersistencia.getPersistenciaEncuesta().altaVoto(voto);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

}
