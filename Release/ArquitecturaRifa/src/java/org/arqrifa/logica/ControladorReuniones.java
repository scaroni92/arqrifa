package org.arqrifa.logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.arqrifa.datatypes.DTEstado;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.persistencia.FabricaPersistencia;
import org.arqrifa.excepciones.ArquitecturaRifaExcepcion;

class ControladorReuniones implements IControladorReuniones {

    private List<Reunion> reunionesActivas = new ArrayList();
    private static ControladorReuniones instancia = null;

    public static IControladorReuniones getInstancia() {
        if (instancia == null) {
            instancia = new ControladorReuniones();
        }
        return instancia;
    }

    private ControladorReuniones() {
        Reunion r = new Reunion(1, "titulo1", "desc1", "res", new Date(), true, 2010, "listado", "lugar1");
        r.setEstado(DTEstado.LISTADO);
        reunionesActivas.add(r);
        r = new Reunion(2, "titulo2", "desc2", "res", new Date(), true, 2012, "pendiente", "lugar2");
        reunionesActivas.add(r);
    }

    @Override
    public void MarcarAsistencia(DTUsuario usuario, DTReunion reunion) {
        try {
            if (!usuario.getRol().equals("estudiante")) {
                throw new Exception("El usuario CI: " + usuario.getCi() + " desea marcar asistencia pero no es estudiante.");
            }
            Reunion reunionActiva = null;
            for (Reunion reunionActual : reunionesActivas) {
                if (reunionActual.getId() == reunion.getId()) {
                    reunionActiva = reunionActual;
                }
            }
            if (reunionActiva == null) {
                throw new Exception("La reunión a la que se desea marcar asistencia no está en curso.");
            }
            if (!reunionActiva.getEstado().equals(DTEstado.LISTADO)) {
                throw new Exception("La lista no ha sido habilitada aún.");
            }
            FabricaPersistencia.getPersistenciaReunion().MarcarAsistencia(usuario, reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
    }

    // Filtrar por encargado
    @Override
    public List<DTReunion> getReunionesActivas() {
        List<DTReunion> resp = new ArrayList();
        for (Reunion reunion : reunionesActivas) {
            resp.add(reunion.getDataType());
        }
        return resp;
    }

    @Override
    public void altaReunion(DTReunion reunion) {
        try {
            if (reunion == null) {
                throw new Exception("No se puede agendar una reunión nula.");
            }

//            if (reunion.getFecha().after(new Date())) {
//                throw new Exception("Las reuniones deben ser agendadas con almenos un día de anticipación.");
//            }
            FabricaPersistencia.getPersistenciaReunion().altaReunion(reunion);
            List<DTUsuario> usuarios = FabricaPersistencia.getPersistenciaUsuario().listarEstudiantes(reunion.getGeneracion());

            String asunto = "¡Nueva reunión agendada!";
            String mensaje = "Hola te informamos que se ha agendado una nueva reunión para el día " + reunion.getFecha();

            Mensajeria mensajeria = new Mensajeria(new Mensaje("", asunto, mensaje));

            for (DTUsuario usuario : usuarios) {
                mensajeria.getMensaje().setDestinatario(usuario.getEmail());
                mensajeria.enviar();
            }
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
    }

}
