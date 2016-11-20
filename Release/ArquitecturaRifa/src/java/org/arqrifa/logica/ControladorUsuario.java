package org.arqrifa.logica;

import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.persistencia.FabricaPersistencia;
import org.arqrifa.excepciones.ArquitecturaRifaExcepcion;

class ControladorUsuario implements IControladorUsuario {

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    private static ControladorUsuario instancia = null;

    public static IControladorUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ControladorUsuario();
        }
        return instancia;
    }

    private ControladorUsuario() {
    }
    //</editor-fold>

    @Override
    public DTUsuario autenticar(int ci, String contrasena) {
        DTUsuario resp = null;
        try {
            resp = FabricaPersistencia.getPersistenciaUsuario().autenticar(ci, contrasena);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
        return resp;
    }

    @Override
    public void agregarEncargado(DTUsuario usuario) {
        try {
            if (usuario == null) {
                throw new Exception("No se puede dar de alta una solicitud nula.");
            }
            if (usuario.getCi() < 4000000) {
                throw new Exception("Cédula inválida.");
            }
            usuario.setRol("Encargado");
            FabricaPersistencia.getPersistenciaUsuario().agregar(usuario);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
    }

}
