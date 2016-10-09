package org.arqrifa.rest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTMensajeError;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTSolicitud;

public class ClienteJersey {

    private WebTarget webTarget;
    private final Client client;
    private static final String BASE_URI = "http://localhost:8080/ArquitecturaRifa/api";
    private final String responseType = MediaType.APPLICATION_JSON + ";charset=utf-8";

    public ClienteJersey() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("servicio");

    }

    public DTUsuario login(String ci, String pass) throws ClientErrorException, Exception {
        webTarget = webTarget.queryParam("ci", ci);
        webTarget = webTarget.queryParam("pass", pass);
        webTarget = webTarget.path("login");
        Response respuesta = webTarget.request(responseType).get();
        if (respuesta.getStatus() == 204) {
            return null;
        }
        if (respuesta.getStatus() != 200) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        return respuesta.readEntity(DTUsuario.class);
    }

    public void enviarSolicitud(DTSolicitud solicitud) throws Exception {
        webTarget = webTarget.path("usuario/solicitud");
        Response respuesta = webTarget.request(responseType).post(Entity.entity(solicitud, responseType));
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public List<DTSolicitud> listarSolicitudes(DTUsuario usuario) throws Exception {
        webTarget = webTarget.path("generacion/solicitudes");
        Response respuesta = webTarget.request(responseType).post(Entity.entity(usuario, responseType));
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        List<DTSolicitud> solicitudes = Arrays.asList(respuesta.readEntity(DTSolicitud[].class));
        return solicitudes;
    }

    public void close() {
        client.close();
    }

}
