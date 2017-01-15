package org.arqrifa.exceptions;


public class ArquitecturaRifaException extends Exception {

    public ArquitecturaRifaException() {
    }

    public ArquitecturaRifaException(String mensaje) {
        super("#error#" + mensaje);
    }

}
