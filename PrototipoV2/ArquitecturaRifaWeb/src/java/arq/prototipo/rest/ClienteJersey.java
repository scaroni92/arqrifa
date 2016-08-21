/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arq.prototipo.rest;

import arq.prototipo.datatypes.DTEstudiante;
import arq.prototipo.datatypes.MensajeError;
import com.google.gson.Gson;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class ClienteJersey {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/ArquitecturaRifa/api";

    public ClienteJersey() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("servicio");
    }



    public DTEstudiante login(String ci, String pass) throws ClientErrorException, Exception {
        WebTarget resource = webTarget;
        resource = resource.queryParam("ci", ci);
        resource = resource.queryParam("pass", pass);
        resource = resource.path("login");
        String resultado = resource.request(MediaType.APPLICATION_JSON).get(String.class);
        
        if (resultado.isEmpty()) {
            return null;
        } else if (resultado.contains("ci")) {
            return new Gson().fromJson(resultado, DTEstudiante.class);
        } else {
            MensajeError me = new Gson().fromJson(resultado, MensajeError.class);
            throw new Exception(me.getMensaje());
        }
    }

    public void close() {
        client.close();
    }
    
}
