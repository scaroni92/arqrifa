/*
 * Clase generada por NetBeans para el acceso a la API REST
 */
package arq.prototipo.rest;

import arq.prototipo.datatypes.DTReunion;
import arq.prototipo.datatypes.MensajeError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public class ClienteRest {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/ArquitecturaRifa/api";

    public ClienteRest() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("servicio");
    }

    public void marcarAsistencia(String ci) throws Exception {
        WebTarget resource = webTarget;
        resource = resource.queryParam("ci", ci);
        resource = resource.path("asistencia");
        String resultado = resource.request(APPLICATION_JSON).get(String.class);
        if (resultado.contains("mensaje")) {
            MensajeError me = new Gson().fromJson(resultado, MensajeError.class);
            throw new Exception(me.getMensaje());
        }
    }
    
    public List<DTReunion> getReunionesIniciadas() throws Exception{
        WebTarget resource = webTarget;
        resource = resource.path("reuniones");
        resource = resource.path("getActivas");
        String resultado = resource.request(MediaType.APPLICATION_JSON).get(String.class);
        
        if (resultado.isEmpty()) {
            return null;
        } else if (resultado.contains("id")) {
            //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.zzz").create();
            DTReunion[] reunionArray = new Gson().fromJson(resultado, DTReunion[].class);
            return new ArrayList<>(Arrays.asList(reunionArray));
        } else {
            MensajeError me = new Gson().fromJson(resultado, MensajeError.class);
            throw new Exception(me.getMensaje());
        }
    }
    

    public void close() {
        client.close();
    }
    
}
