package arqrifa.org.arquitecturarifamobile.app;

import android.app.Application;

import arqrifa.org.arquitecturarifamobile.datatypes.DTUsuario;

/**
 * Esta clase almacena instancias globales
 */

public class ArquitecturaRifaApplication extends Application {

    private DTUsuario usuario;

    public DTUsuario getUsuario(){
        return usuario;
    }

    public void setUsuario(DTUsuario usuario) {
        this.usuario = usuario;
    }
}
