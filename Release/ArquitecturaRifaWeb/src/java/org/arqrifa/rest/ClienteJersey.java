package org.arqrifa.rest;

import java.util.Arrays;
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
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTSolicitud;

public class ClienteJersey {

    private static final String BASE_URI = "http://localhost:8080/ArquitecturaRifa/api";
    private WebTarget target;
    private final Client client;
    private final String RESPONSE_TYPE = MediaType.APPLICATION_JSON + ";charset=utf-8";

    public ClienteJersey() {
        client = ClientBuilder.newClient();
        target = client.target(BASE_URI).path("servicio");

    }

    public DTUsuario login(int ci, String pass) throws ClientErrorException, Exception {
        Response respuesta = target.path("login")
                .queryParam("ci", ci)
                .queryParam("pass", pass)
                .request(RESPONSE_TYPE)
                .get();
        
        if (respuesta.getStatus() == 200) {
            return respuesta.readEntity(DTUsuario.class);
        } else if (respuesta.getStatus() != 204) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        return null;
    }

    public void enviarSolicitud(DTSolicitud solicitud) throws Exception {
        Response respuesta = target.path("solicitud/enviar")
                .request(RESPONSE_TYPE)
                .post(Entity.entity(solicitud, RESPONSE_TYPE));
        
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void verificarSolicitud(int codigo) throws ClientErrorException, Exception {
        Response respuesta = target.queryParam("codigo", codigo)
                .path("solicitud/verificar")
                .request(RESPONSE_TYPE)
                .get();
        
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public List<DTSolicitud> listarSolicitudes(DTUsuario usuario) throws Exception {
        Response respuesta = target.path("solicitud/listar")
                .request(RESPONSE_TYPE)
                .post(Entity.entity(usuario, RESPONSE_TYPE));
        
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        return Arrays.asList(respuesta.readEntity(DTSolicitud[].class));
    }

    public List<DTGeneracion> listarGeneraciones() throws Exception {
        Response respuesta = target.path("generacion/listar")
                .request(MediaType.APPLICATION_JSON)
                .get();
        
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        return Arrays.asList(respuesta.readEntity(DTGeneracion[].class));
    }

    public void agregarEncargado(DTUsuario usuario) throws Exception {
        target = target.path("encargado/agregar");
        Response respuesta = target.request(RESPONSE_TYPE).post(Entity.entity(usuario, RESPONSE_TYPE));
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void agregarGeneracion(DTGeneracion generacion) throws Exception {
        target = target.path("generacion/agregar");
        Response respuesta = target.request(RESPONSE_TYPE).post(Entity.entity(generacion, MediaType.APPLICATION_JSON));
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void close() {
        client.close();
    }

}
