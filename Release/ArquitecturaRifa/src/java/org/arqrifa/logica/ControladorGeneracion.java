package org.arqrifa.logica;

import java.util.Calendar;
import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.exceptions.ArquitecturaRifaException;
import org.arqrifa.persistencia.FabricaPersistencia;

class ControladorGeneracion implements IControladorGeneracion {

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    private static ControladorGeneracion instancia = null;

    public static ControladorGeneracion getInstancia() {
        if (instancia == null) {
            instancia = new ControladorGeneracion();
        }
        return instancia;
    }

    private ControladorGeneracion() {
    }
    //</editor-fold>

    @Override
    public void agregarGeneracion(DTGeneracion generacion) {
        try {

            if (generacion.getId() < 2009) {
                throw new Exception("El año ingresado es inferior al permitido");
            }
            if (generacion.getId() > Calendar.getInstance().get(Calendar.YEAR)) {
                throw new Exception("No se puede agregar una generación cuyo año supere al actual");
            }

            FabricaPersistencia.getPersistenciaGeneracion().agregar(generacion);

        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public List<DTGeneracion> listarGeneraciones() {
        try {
            
            return FabricaPersistencia.getPersistenciaGeneracion().listar();
            
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

}
