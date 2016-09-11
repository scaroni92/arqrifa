/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arq.prototipo.app;

import arq.prototipo.datatypes.DTReunion;
import arq.prototipo.rest.ClienteRest;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author usuario
 */
public class DesktopController {
        
    private static Thread Thread;
    private static WaitThread waitThread;
    private static DTReunion reunion;
    
    public static void initiateController(){      
        waitThread = new WaitThread();
        Thread = new Thread(waitThread);
    }
    
    public static List<DTReunion> getReunionesIniciadas() throws Exception{
        return new ClienteRest().getReunionesIniciadas();
    }
    
    public static void iniciarPuenteBluetooth(DTReunion parReunion){
        reunion = parReunion;
        Thread.start();
    }
    
    public static void cerrarPuenteBluetooth() throws IOException, InterruptedException{
        System.exit(0);
    }
}

