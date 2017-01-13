package org.arqrifa.exceptions;

//@author Ale
public class ArquitecturaRifaException extends Exception {

    public ArquitecturaRifaException() {
    }

    public ArquitecturaRifaException(String mensaje) {
        super("mensaje-error" + mensaje);
    }

}
