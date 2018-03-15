package org.arqrifa.logica.validation;

import java.util.Date;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.logica.Utilities;

public class ReunionValidator {

    private static DTReunion reunion;

    public static void validate(DTReunion dtr, ReunionValidatorType type) throws Exception {
        reunion = dtr;

        if (reunion == null) {
            throw new Exception("La reunión no puede ser nula");
        }

        switch (type) {
            case ALTA:
                validarAlta();
                break;
            case BAJA:
                validarBaja();
                break;
            case MODIFICAR:
                validarModificar();
                break;
            case INICIO:
                validarInicio();
                break;
            case HABILITAR_LISTA:
                validarHabilitarLista();
                break;
            case DESHABILITAR_LISTA:
                validarDeshabilitarLista();
                break;
            case HABILITAR_VOTACION:
                validarHabilitarVotacion();
                break;
            case DESHABILITAR_VOTACION:
                validarDeshabilitarVotacion();
                break;
            case FIN:
                validarFin();
                break;
        }
    }

    private static void validarAlta() throws Exception {
        if (Utilities.compararFechas(reunion.getFecha(), new Date()) <= 0) {
            throw new Exception("Las reuniones deben agendarse con almenos un día de anticipación");
        }
        validarCampos();
    }

    private static void validarCampos() throws Exception {
        if (reunion.getTitulo().length() > 100) {
            throw new Exception("El título ingresado es demasiado largo");
        }
        if (reunion.getLugar().length() > 100) {
            throw new Exception("El texto ingresado en el campo lugar es demasiado largo");
        }
        if (reunion.getDuracion() > 240) {
            throw new Exception("La duración de la reunión no puede ser tan extensa");
        }
        if (reunion.getDuracion() < 15) {
            throw new Exception("La duración de la reunión no puede ser tan breve");
        }
        for (String tema : reunion.getTemas()) {
            if (tema.length() > 50) {
                throw new Exception("Uno de los temas ingresado es demasiado largo");
            }
        }
    }

    private static void validarBaja() throws Exception {
        if (!reunion.isPendiente()) {
            throw new Exception("No se puede eliminar una reunión en estado \"" + reunion.getEstado().toLowerCase() + "\"");
        }
    }

    private static void validarModificar() throws Exception {
        if (!reunion.isPendiente()) {
            throw new Exception("No se puede modificar una reunión en estado \"" + reunion.getEstado().toLowerCase() + "\"");
        }
        validarCampos();
    }

    private static void validarInicio() throws Exception {
        if (!reunion.isPendiente()) {
            throw new Exception("La reunión que desea iniciar ya fue efectuada");
        }

        Date fechaActual = new Date();

        if (Utilities.compararFechas(reunion.getFecha(), fechaActual) != 0) {
            throw new Exception("No se puede iniciar una reunión fuera de fecha");
        }

        if (reunion.getFecha().after(fechaActual)) {
            throw new Exception("No se puede iniciar una reunión antes de la hora prevista");
        }
    }

    private static void validarHabilitarLista() throws Exception {
        if (!reunion.isIniciada()) {
            throw new Exception("La reunión debe estar en estado iniciada");
        }
    }

    private static void validarDeshabilitarLista() throws Exception {
        if (!reunion.isListado()) {
            throw new Exception("La lista ya se encuentra deshabilitada");
        }
    }

    private static void validarHabilitarVotacion() throws Exception {
        if (!reunion.isIniciada()) {
            throw new Exception("El estado de la reunión debe ser iniciada");
        }
    }

    private static void validarDeshabilitarVotacion() throws Exception {
        if (!reunion.isIniciada()) {
            throw new Exception("El estado de la reunión debe ser iniciada");
        }
    }

    private static void validarFin() throws Exception {
        if (!reunion.isIniciada()) {
            throw new Exception("El estado de la reunión debe ser iniciada");
        }
        if (reunion.getResoluciones().isEmpty()) {
            throw new Exception("Debe ingresar alguna resolución");
        }
    }
}
