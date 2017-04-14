package org.arqrifa.app;

import org.arqrifa.rest.JerseyClient;
import org.arqrifa.datatypes.DTReunion;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.views.Main;

public class DesktopController {

    private static Thread thread;
    private static WaitThread waitThread;
    private static DTReunion reunionActiva;
    private static DTUsuario usuario;

    public static void initiateController() {
        waitThread = new WaitThread();
        thread = new Thread(waitThread);
    }

    public static void ingresar(String ci, String pass) throws Exception {
        try {
            
            int cedula;
            try {
                cedula = Integer.parseInt(ci);
            } catch (NumberFormatException e) {
                throw new Exception("Ingrese una cédula válida");
            }
            usuario = new JerseyClient().login(cedula, pass);

            if (usuario == null) {
                throw new Exception("Usuario y/o contraseña incorrectos.");
            }
            if (!usuario.getRol().equals(DTUsuario.ENCARGADO)) {
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
        return new JerseyClient().buscarReunionPorFecha(usuario.getGeneracion(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public static DTReunion getReunionActiva() {
        return reunionActiva;
    }

    public static DTUsuario getUsuario(){
        return usuario;
    }
        
    public static void iniciarPuenteBluetooth() {
        new MessagePane().displayPane("Éxito", "Adaptador Bluetooth activado exitosamente", MessagePane.OK);
        // TODO comprobar estado de la reunión
        //thread.start();
    }

    public static void cerrarPuenteBluetooth() throws IOException, InterruptedException {
        System.exit(0);
    }
}
