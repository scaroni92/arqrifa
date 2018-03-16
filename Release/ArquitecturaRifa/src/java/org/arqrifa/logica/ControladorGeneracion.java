package org.arqrifa.logica;

import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.exceptions.ArquitecturaRifaException;
import org.arqrifa.logica.validation.GeneracionValidator;
import org.arqrifa.logica.validation.GeneracionValidatorType;
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
    public void agregar(DTGeneracion generacion) {
        try {
            GeneracionValidator.validate(generacion, GeneracionValidatorType.AGREGAR);
            FabricaPersistencia.getPersistenciaGeneracion().agregar(generacion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public List<DTGeneracion> listar() {
        try {
            return FabricaPersistencia.getPersistenciaGeneracion().listar();
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

}
