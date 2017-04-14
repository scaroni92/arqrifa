package org.arqrifa.app;

import org.arqrifa.rest.JerseyClient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.StreamConnection;
import org.arqrifa.datatypes.DTUsuario;

public class ProcessConnectionThread implements Runnable {

    private StreamConnection mConnection;

    //Comandos recibidos
    private static final int EXIT_CMD = -1;
    private static final int END_MSG = 35;

    private String message;
    private byte[] bytes;
    private ByteArrayOutputStream baos;

    public ProcessConnectionThread(StreamConnection connection) {
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
                        System.out.println("datos recibidos -  " + command);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void processCommand(byte[] command) {
        try {
            String strCommand = new String(command);
            System.out.println("Marcar asistencia para el estudiante: " + strCommand);

            DTUsuario estudiante = new JerseyClient().buscarUsuario(Integer.parseInt(strCommand));

            new JerseyClient().agregarAsistencia(DesktopController.getReunion(), estudiante);
            System.out.println("Asistencia marcada");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
