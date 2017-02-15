package org.arqrifa.app;

import org.arqrifa.rest.JerseyClient;
import org.arqrifa.datatypes.DTReunion;
import java.io.IOException;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.views.Main;

public class DesktopController {

    private static Thread thread;
    private static WaitThread waitThread;
    private static DTReunion reunionActiva;
    private static DTUsuario encargado;

    public static void initiateController() {
        waitThread = new WaitThread();
        thread = new Thread(waitThread);
    }

    public static void ingresar(int ci, String pass) throws Exception {
        try {
            encargado = new JerseyClient().login(ci, pass);

            if (encargado == null) {
                throw new Exception("Usuario y/o contraseña incorrectos.");
            }
            if (!encargado.getRol().equals(DTUsuario.ENCARGADO)) {
                throw new Exception("Solo los encargados tienen acceso a la aplicación.");
            }
            reunionActiva = buscarReunionDelDia();
            Main ventanaPrincipal = new Main();
            ventanaPrincipal.setVisible(true);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static DTReunion buscarReunionDelDia() throws Exception {
        return new JerseyClient().buscarReunionDelDia(encargado.getGeneracion());
    }

    public static DTReunion getReunionActiva() {
        return reunionActiva;
    }

    public static void iniciarPuenteBluetooth() {
        // TODO comprobar estado de la reunión
        thread.start();
    }

    public static void cerrarPuenteBluetooth() throws IOException, InterruptedException {
        System.exit(0);
    }
}
