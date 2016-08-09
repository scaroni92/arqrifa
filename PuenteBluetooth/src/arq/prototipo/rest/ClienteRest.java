/*
 * Clase generada por NetBeans para el acceso a la API REST
 */
package arq.prototipo.rest;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

public class ClienteRest {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/ProyectoArquitecturaRIfa/app";

    public ClienteRest() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("servicio");
    }

    public String marcarAsistencia(String ci, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (ci != null) {
            resource = resource.queryParam("ci", ci);
        }
        if (id != null) {
            resource = resource.queryParam("id", id);
        }
        resource = resource.path("marcarAsistencia");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }

    public String autenticar(String pass, String user) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (pass != null) {
            resource = resource.queryParam("pass", pass);
        }
        if (user != null) {
            resource = resource.queryParam("user", user);
        }
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }

    public void close() {
        client.close();
    }
    
}
