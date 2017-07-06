package org.arqrifa.rest;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTAsistenciaWrapper;
import org.arqrifa.datatypes.DTMensajeError;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;

public class JerseyClient {

    private final WebTarget webTarget;
    private static final String BASE_URI = "http://192.168.0.104:8080/ArquitecturaRifa/api";
    private static final String JSON_TYPE = "application/json;charset=utf-8";

    public JerseyClient() {
        webTarget = ClientBuilder.newClient().target(BASE_URI);
    }

    private void comprobarEstado(Response response) throws Exception {
        switch (response.getStatus()) {
            case 400:
                throw new Exception("Solicitud incorrecta");
            case 404:
                throw new Exception("Recurso no encontrado");
            case 409:
                throw new Exception(response.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public DTUsuario login(String ci, String pass) throws Exception {
        Response response = webTarget.path("usuarios/login").queryParam("ci", ci).queryParam("pass", pass).request(JSON_TYPE).get();
        return response.readEntity(DTUsuario.class);
    }

    public DTUsuario buscarUsuario(String ci) throws Exception {
        Response response = webTarget.path("usuarios/" + ci).request(JSON_TYPE).get();
        return response.readEntity(DTUsuario.class);
    }

    public DTReunion buscarReunionActual(int gen) throws Exception {
        Response response = webTarget.path("reuniones/actual").queryParam("gen", gen).request(JSON_TYPE).get();
        comprobarEstado(response);
        return response.readEntity(DTReunion.class);
    }

    public void agregarAsistencia(DTUsuario usuario, DTReunion reunion) throws Exception {
        Response response = webTarget.path("reuniones/asistencias").request(JSON_TYPE).post(Entity.entity(new DTAsistenciaWrapper(usuario, reunion), JSON_TYPE));
        comprobarEstado(response);
    }

}
