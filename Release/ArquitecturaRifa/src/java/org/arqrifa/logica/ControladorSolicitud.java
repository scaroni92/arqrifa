package org.arqrifa.logica;

import java.util.List;
import java.util.Random;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.exceptions.ArquitecturaRifaException;
import org.arqrifa.logica.validation.SolicitudValidator;
import org.arqrifa.logica.validation.SolicitudValidatorType;
import org.arqrifa.notificaciones.Notificaciones;
import org.arqrifa.persistencia.FabricaPersistencia;

class ControladorSolicitud implements IControladorSolicitud {

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    private static ControladorSolicitud instancia = null;

    public static ControladorSolicitud getInstancia() {
        if (instancia == null) {
            instancia = new ControladorSolicitud();
        }
        return instancia;
    }

    private ControladorSolicitud() {
    }
    //</editor-fold>

    @Override
    public void agregar(DTSolicitud solicitud) {
        try {
            SolicitudValidator.validate(solicitud, SolicitudValidatorType.AGREGAR);
            generarCodigo(solicitud);
            FabricaPersistencia.getPersistenciaSolicitud().agregar(solicitud);
            Notificaciones.notificarSolicitudAgregada(solicitud);

        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    private void generarCodigo(DTSolicitud solicitud) {
        solicitud.setCodigo((int) (new Random().nextDouble() * 99999999));
    }

    @Override
    public void verificar(int codigo) {
        try {
            FabricaPersistencia.getPersistenciaSolicitud().verificar(codigo);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void confirmar(DTSolicitud solicitud) {
        try {
            SolicitudValidator.validate(solicitud, SolicitudValidatorType.CONFIRMAR);
            FabricaPersistencia.getPersistenciaSolicitud().confirmar(solicitud);
            Notificaciones.notificarSolicitudAceptada(solicitud);

        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void rechazar(DTSolicitud solicitud) {
        try {
            SolicitudValidator.validate(solicitud, SolicitudValidatorType.RECHAZAR);
            FabricaPersistencia.getPersistenciaSolicitud().rechazar(solicitud);
            Notificaciones.notificarSolicitudRechazada(solicitud);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTSolicitud buscar(int ci) {
        try {
            return FabricaPersistencia.getPersistenciaSolicitud().buscar(ci);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public List<DTSolicitud> listar(int generacion) {
        try {
            return FabricaPersistencia.getPersistenciaSolicitud().listar(generacion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

}
