package org.arqrifa.logica.validation;

import org.arqrifa.datatypes.DTUsuario;

public class UsuarioValidator {

    private static DTUsuario usuario;

    public static void validate(DTUsuario dtu, EncuestaValidatorType type) throws Exception {
        usuario = dtu;
        if (usuario == null) {
            throw new Exception("No se puede agregar un usuario nulo");
        }

        switch (type) {
            case AGREGAR:
                validarAlta();
                break;
            case MODIFICAR:
                validarModificar();
                break;
        }
    }

    private static void validarAlta() throws Exception {
        validarCampos();
    }

    private static void validarCampos() throws Exception {
        if (usuario.getCi() < 1000000 || usuario.getCi() > 9999999) {
            throw new Exception("Ingrese una cédula válida");
        }
        if (usuario.getNombre().length() > 20) {
            throw new Exception("El nombre es demasiado largo");
        }
        if (usuario.getApellido().length() > 20) {
            throw new Exception("El apellido es demasiado largo");
        }
        if (usuario.getContrasena().length() > 20) {
            throw new Exception("La contraseña es demasiado larga");
        }
        if (usuario.getEmail().length() > 50) {
            throw new Exception("El email es demasiado largo");
        }
    }

    private static void validarModificar() throws Exception {
        validarCampos();
    }
}
