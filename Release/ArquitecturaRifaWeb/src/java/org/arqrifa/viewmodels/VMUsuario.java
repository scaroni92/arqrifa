package org.arqrifa.viewmodels;

import org.arqrifa.datatypes.DTUsuario;

public class VMUsuario extends ViewModel {

    private DTUsuario usuario;

    public VMUsuario() {
    }

    public VMUsuario(DTUsuario usuario, String mensaje) {
        super(mensaje);
        this.usuario = usuario;
    }

    public DTUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(DTUsuario usuario) {
        this.usuario = usuario;
    }

}
