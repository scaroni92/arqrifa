package org.arqrifa.rest;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTMensajeError;

public abstract class ClienteJersey {

    public final WebTarget webTarget;
    private static final String BASE_URI = "http://localhost:8080/ArquitecturaRifa/api/";
    public static final String JSON_TYPE = "application/json;charset=utf-8";

    public ClienteJersey(String resource) {
        webTarget = ClientBuilder.newClient().target(BASE_URI + resource);
    }

    public void comprobarEstado(Response response) throws Exception {
        switch (response.getStatus()) {
            case 400:
                throw new Exception("Solicitud incorrecta");
            case 404:
                throw new Exception("Recurso no encontrado");
            case 409:
                throw new Exception(response.readEntity(DTMensajeError.class).getMensaje());
        }
    }
}
