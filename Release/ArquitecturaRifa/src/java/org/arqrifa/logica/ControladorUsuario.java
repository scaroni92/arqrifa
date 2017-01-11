package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.persistencia.FabricaPersistencia;
import org.arqrifa.excepciones.ArquitecturaRifaException;

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
        try {
            return FabricaPersistencia.getPersistenciaUsuario().autenticar(ci, contrasena);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
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
            
            FabricaPersistencia.getPersistenciaUsuario().agregar(usuario);
            
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public List<DTUsuario> listarEstudiantes(int id_gen) {
        try {
            return FabricaPersistencia.getPersistenciaUsuario().listarEstudiantes(id_gen);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTUsuario buscarUsuario(int ci) {
        try {
            return FabricaPersistencia.getPersistenciaUsuario().buscar(ci);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }


}
