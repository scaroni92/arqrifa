package org.arqrifa.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTMensajeError;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;

public class JerseyClient {

    private static final String BASE_URI = "http://localhost:8080/ArquitecturaRifa/api";
    private final WebTarget TARGET;
    private final Client CLIENT;
    private final String JSON_TYPE = "application/json;charset=utf-8";

    public JerseyClient() {
        CLIENT = ClientBuilder.newClient();
        TARGET = CLIENT.target(BASE_URI).path("servicio");
    }

    private void comprobarError(Response respuesta) throws Exception {
        switch (respuesta.getStatus()) {
            case 400:
                throw new Exception("Solicitud incorrecta");
            case 404:
                throw new Exception("Recurso no encontrado");
            case 409:
                throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public DTUsuario login(int ci, String pass) throws Exception {
        Response respuesta = TARGET.path("login").queryParam("ci", ci).queryParam("pass", pass).request(JSON_TYPE).get();
        comprobarError(respuesta);
        return respuesta.readEntity(DTUsuario.class);
    }

    public DTReunion buscarReunionDelDia(int generacion) throws Exception {
        Response respuesta = TARGET.path("reunion/activa").queryParam("id_gen", generacion).request(JSON_TYPE).get();
        comprobarError(respuesta);
        return respuesta.readEntity(DTReunion.class);
    }

    public void agregarAsistencia(DTReunion reunion, DTUsuario usuario) throws Exception {
        Response respuesta = TARGET.path("asistencia/agregar").request(JSON_TYPE).post(Entity.entity(new DTAsistencia(usuario, reunion), JSON_TYPE));
        comprobarError(respuesta);
    }

}
