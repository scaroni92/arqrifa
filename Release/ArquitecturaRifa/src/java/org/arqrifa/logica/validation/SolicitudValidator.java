package org.arqrifa.logica.validation;

import org.arqrifa.datatypes.DTSolicitud;

public class SolicitudValidator {

    private static DTSolicitud solicitud;

    public static void validate(DTSolicitud dts, SolicitudValidatorType type) throws Exception {
        solicitud = dts;
        if (solicitud == null) {
            throw new Exception("La solicitud no puede ser nula");
        }

        switch (type) {
            case AGREGAR:
                validarAgregar();
                break;
            case CONFIRMAR:
                validarConfirmar();
                break;
            case RECHAZAR:
                validarRechazar();
                break;
        }
    }

    private static void validarAgregar() throws Exception {
        UsuarioValidator.validate(solicitud.getUsuario(),UsuarioValidatorType.ALTA);
    }

    private static void validarConfirmar() throws Exception {
        if (!solicitud.isVerificada()) {
            throw new Exception("No se puede confirmar una solicitud sin verificar");
        }
    }

    private static void validarRechazar() {

    }
}
