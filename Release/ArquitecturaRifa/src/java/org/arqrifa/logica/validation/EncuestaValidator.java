package org.arqrifa.logica.validation;

import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTRespuesta;
import org.arqrifa.datatypes.DTReunion;

public class EncuestaValidator {

    private static DTReunion reunion;

    public static void validate(DTReunion dtr, EncuestaValidatorType type) throws Exception {
        reunion = dtr;
        if (reunion == null) {
            throw new Exception("La reunión no puede ser nula");
        }
        if (reunion.getEncuesta() == null) {
            throw new Exception("La encuesta no puede ser nula");
        }

        switch (type) {
            case AGREGAR:
                validarAlta();
                break;
            case BAJA:
                validarBaja();
                break;
            case MODIFICAR:
                validarModificar();
                break;
        }
    }

    private static void validarAlta() throws Exception {
        if (reunion.isFinalizada()) {
            throw new Exception("No se puede crear encuestas para reuniones finalizadas");
        }
        validarCampos();
    }

    private static void validarCampos() throws Exception {
        if (reunion.getEncuesta().getTitulo().length() > 30) {
            throw new Exception("El título de la encuesta es demasiado largo");
        }
        if (reunion.getEncuesta().getDuracion() > reunion.getDuracion()) {
            throw new Exception("La encuesta no puede durar más que la reunión");
        }
        if (reunion.getEncuesta().getDuracion() < 5) {
            throw new Exception("La duración de la encuesta no puede ser tan breve");
        }

        if (reunion.getEncuesta().getPropuestas().isEmpty()) {
            throw new Exception("No se puede crear una encuesta sin propuestas");
        }
        for (DTPropuesta propuesta : reunion.getEncuesta().getPropuestas()) {
            if (propuesta.getPregunta().length() > 100) {
                throw new Exception("El título de la propuesta no puede ser tan largo");
            }
            if (propuesta.getRespuestas().size() < 2) {
                throw new Exception("Todas las propuestas deben tener almenos dos respuestas");
            }
            for (DTRespuesta respuesta : propuesta.getRespuestas()) {
                if (respuesta.getRespuesta().length() > 100) {
                    throw new Exception("Las respuestas no pueden ser tan extensas");
                }
            }
        }
    }

    private static void validarBaja() throws Exception {
        if (!reunion.isPendiente()) {
            throw new Exception("No se puede eliminar la encuesta de una reunión en estado \"" + reunion.getEstado().toLowerCase() + "\"");
        }
    }

    private static void validarModificar() throws Exception {
        if (!reunion.isPendiente()) {
            throw new Exception("No se puede modificar la encuesta de una reunión en estado \"" + reunion.getEstado().toLowerCase() + "\"");
        }
        validarCampos();
    }

}
