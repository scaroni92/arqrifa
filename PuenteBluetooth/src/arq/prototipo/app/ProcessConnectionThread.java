/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arq.prototipo.app;

import arq.prototipo.rest.ClienteRest;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.io.StreamConnection;

public class ProcessConnectionThread implements Runnable {

    private StreamConnection mConnection;

    //Comandos recibidos
    private static final int EXIT_CMD = -1;
     private static final int END_MSG = 35;
    
    private String message;
    private byte [] bytes;
    private ByteArrayOutputStream baos;

    public ProcessConnectionThread(StreamConnection connection)
    {
        mConnection = connection;
    }

    @Override
    public void run() {
        try {
            //Prepara la recepcion de datos
            InputStream inputStream = mConnection.openInputStream();
            message = "";
            baos = new ByteArrayOutputStream();
            System.out.println("esperando datos...");
            
            OUTER:
            while (true) {
                int command = inputStream.read();
                switch (command) {
                    case END_MSG:
                        System.out.println("datos recibidos...");
                        processCommand(baos.toByteArray());
                        break;
                    case EXIT_CMD:
                        System.out.println("terminando conexion...");
                        break OUTER;
                    default:
                        baos.write(command);
                        System.out.println("datos recibidos -  "+command);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Process the command from client
     * @param command the command code
     */
    private void processCommand(byte[] command) {
        try {
            String strCommand = new String(command);
            System.out.println("Marcar asistencia para el estudiante: "+strCommand);
            String resultado = new ClienteRest().marcarAsistencia("strCommand", "1");
            if (resultado.contains("true")) {
                System.out.println("Asistencia marcada");
            } else {
                System.out.println("Error al marcar asistencia");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}