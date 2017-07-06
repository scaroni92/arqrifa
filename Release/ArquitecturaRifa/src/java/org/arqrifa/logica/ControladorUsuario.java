package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.persistencia.FabricaPersistencia;
import org.arqrifa.exceptions.ArquitecturaRifaException;

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
    public void agregar(DTUsuario usuario) {
        try {
            if (usuario == null) {
                throw new Exception("No se puede agregar un usuario nulo");
            }
            if (usuario.getCi() < 5000000) {
                throw new Exception("Ingrese una cédula válida.");
            }

            FabricaPersistencia.getPersistenciaUsuario().agregar(usuario);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTUsuario autenticar(int ci, String contrasena) {
        try {
            return FabricaPersistencia.getPersistenciaUsuario().autenticar(ci, contrasena);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTUsuario buscar(int ci) {
        DTUsuario usuario = null;
        try {
            usuario = FabricaPersistencia.getPersistenciaUsuario().buscar(ci);

            if (DTUsuario.ESTUDIANTE.equals(usuario.getRol())) {
                cargarInasistencias(usuario);
            }

        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
        return usuario;
    }

    private void cargarInasistencias(DTUsuario estudiante) {
        int inasistencias = 0;
        boolean esParticipante;

        List<DTReunion> reuniones = ControladorReunion.getInstancia().listarPorGeneracion(estudiante.getGeneracion());

        for (DTReunion reunion : reuniones) {
            esParticipante = false;
            if (reunion.isFinalizada()) {
                for (DTUsuario participante : reunion.getParticipantes()) {
                    if (participante.getCi() == estudiante.getCi()) {
                        esParticipante = true;
                        break;
                    }
                }
                if (!esParticipante) {
                    inasistencias++;
                }
            }
        }
        estudiante.setInasistencias(inasistencias);
    }

    @Override
    public List<DTUsuario> listarTodos() {
        try {
            return FabricaPersistencia.getPersistenciaUsuario().listarTodos();
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
    public void modificar(DTUsuario usuario) {
        try {
            FabricaPersistencia.getPersistenciaUsuario().modificar(usuario);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

}
