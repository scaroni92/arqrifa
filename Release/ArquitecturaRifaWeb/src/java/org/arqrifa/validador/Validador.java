package org.arqrifa.validador;

public class Validador {

    public static int validarCi(String pCi) throws Exception {
        int ci;
        try {
            ci = Integer.parseInt(pCi);
        } catch (Exception e) {
            throw new Exception("Ingrese una cédula válida.");
        }
        return ci;
    }

    public static int validarId(String pId) throws Exception {
        int id;
        try {
            id = Integer.parseInt(pId);
        } catch (Exception e) {
            throw new Exception("Ingrese un ID de reunión válido.");
        }
        return id;
    }

}
