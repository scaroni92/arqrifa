/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arqrifa.rest;

import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTMensajeError;
import com.google.gson.Gson;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClienteJersey {

    private final WebTarget webTarget;
    private final Client client;
    private static final String BASE_URI = "http://localhost:8080/ArquitecturaRifa/api";

    public ClienteJersey() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("servicio");
    }

    public DTUsuario login(String ci, String pass) throws ClientErrorException, Exception {
        WebTarget resource = webTarget;
        resource = resource.queryParam("ci", ci);
        resource = resource.queryParam("pass", pass);
        resource = resource.path("login");
        Response respuesta = resource.request(MediaType.APPLICATION_JSON).get();
        respuesta.getStatus();
        String resultado = respuesta.readEntity(String.class);
        if (respuesta.getStatus() != 200) {
            throw new Exception(new Gson().fromJson(resultado, DTMensajeError.class).getMensaje());
        }
        return new Gson().fromJson(resultado, DTUsuario.class);
    }

    public void close() {
        client.close();
    }

}
