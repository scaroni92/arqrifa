/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arq.prototipo.app;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class WaitThread implements Runnable {

    /** Constructor */
    public WaitThread() {
    }

    @Override
    public void run() {
        waitForConnection();
    }

    /** Permite que el server sea detectado por otros equipos 
        y se queda a la espera de una conexion*/
    private void waitForConnection() {
        
        LocalDevice local = null;
        StreamConnectionNotifier notifier;
        StreamConnection connection = null;

        // Configura el servidor para escuchar conexiones
        try {
            //Obtiene el dispositivo bluetooth local y lo configura para que sea visible
            local = LocalDevice.getLocalDevice();
            local.setDiscoverable(DiscoveryAgent.GIAC);

            //Crea la conexion
            UUID uuid = new UUID(80087355);
            String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
            notifier = (StreamConnectionNotifier)Connector.open(url);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Esperando conexion
        while(true) {
            try {
                System.out.println("esperando conexion");
                //Conexion encontrada y aceptada
                connection = notifier.acceptAndOpen();
                
                //Crea el hilo de procesamiento de la conexion
                Thread processThread = new Thread(new ProcessConnectionThread(connection));
                processThread.start();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
