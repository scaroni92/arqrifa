package org.arqrifa.logica.validation;

import java.util.Calendar;
import org.arqrifa.datatypes.DTGeneracion;

public class GeneracionValidator {

    private static DTGeneracion generacion;

    public static void validate(DTGeneracion dtg, GeneracionValidatorType type) throws Exception {
        generacion = dtg;
        if (generacion == null) {
            throw new Exception("No se puede agregar una generación nula");
        }

        switch (type) {
            case AGREGAR:
                validarAlta();
        }
    }

    private static void validarAlta() throws Exception {
        if (generacion.getId() < 2009) {
            throw new Exception("El año ingresado es inferior al permitido");
        }
        if (generacion.getId() > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new Exception("No se puede agregar una generación posterior al año actual");
        }
    }
}
