package org.arqrifa.app;

import org.arqrifa.datatypes.DTReunion;
import java.io.IOException;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.JerseyClient;
import org.arqrifa.views.Main;

public class DesktopController {

    private static Thread thread;
    private static WaitThread waitThread;
    private static DTUsuario usuario;

    public static void initiateController() {
        waitThread = new WaitThread();
        thread = new Thread(waitThread);
    }

    public static void ingresar(String ci, String pass) throws Exception {
        try {

            usuario = new JerseyClient().login(ci, pass);

            if (usuario == null) {
                throw new Exception("Usuario y/o contraseña incorrectos.");
            }
            if (!usuario.getRol().equals(DTUsuario.ENCARGADO)) {
                throw new Exception("Solo los encargados tienen acceso a la aplicación.");
            }

            Main ventanaPrincipal = new Main();
            ventanaPrincipal.setVisible(true);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static DTReunion getReunion() {
        DTReunion reunion = null;
        try {
            reunion = new JerseyClient().buscarReunionActual(usuario.getGeneracion());
        } catch (Exception ex) {
            new MessagePane().displayPane("Error", ex.getMessage(), MessagePane.CONFLICT);
        }
        return reunion;
    }

    public static void iniciarPuenteBluetooth() throws Exception {

        if (!getReunion().getEstado().equals(DTReunion.LISTADO)) {
            throw new Exception("El estado de la reunión debe ser 'Listado'");
        }
        thread.start();
    }

    public static void cerrarPuenteBluetooth() throws IOException, InterruptedException {
        System.exit(0);
    }
}
