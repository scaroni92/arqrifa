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
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTSolicitud;

public class ClienteJersey {

    private static final String BASE_URI = "http://localhost:8080/ArquitecturaRifa/api";
    private WebTarget target;
    private final Client client;
    private final String JSON_TYPE = "application/json;charset=utf-8";

    public ClienteJersey() {
        client = ClientBuilder.newClient();
        target = client.target(BASE_URI).path("servicio");

    }

    public DTUsuario login(int ci, String pass) throws ClientErrorException, Exception {
        Response respuesta = target.path("login").queryParam("ci", ci).queryParam("pass", pass).request(JSON_TYPE).get();

        if (respuesta.getStatus() == 200) {
            return respuesta.readEntity(DTUsuario.class);
        } else if (respuesta.getStatus() != 204) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        return null;
    }

    public void enviarSolicitud(DTSolicitud solicitud) throws Exception {
        Response respuesta = target.path("solicitud/enviar").request(JSON_TYPE).post(Entity.entity(solicitud, JSON_TYPE));

        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void verificarSolicitud(int codigo) throws ClientErrorException, Exception {
        Response respuesta = target.path("solicitud/verificar").queryParam("codigo", codigo).request(JSON_TYPE).get();

        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public List<DTSolicitud> listarSolicitudes(DTUsuario usuario) throws Exception {
        Response respuesta = target.path("solicitud/listar").request(JSON_TYPE).post(Entity.entity(usuario, JSON_TYPE));

        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        return Arrays.asList(respuesta.readEntity(DTSolicitud[].class));
    }

    public List<DTGeneracion> listarGeneraciones() throws Exception {
        target = target.path("generacion/listar");
        
        Response respuesta = target.request(JSON_TYPE).get();

        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        return Arrays.asList(respuesta.readEntity(DTGeneracion[].class));
    }

    public void agregarEncargado(DTUsuario usuario) throws Exception {
        target = target.path("encargado/agregar");
        Response respuesta = target.request(JSON_TYPE).post(Entity.entity(usuario, JSON_TYPE));
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void agregarGeneracion(DTGeneracion generacion) throws Exception {
        target = target.path("generacion/agregar");
        Response respuesta = target.request(JSON_TYPE).post(Entity.entity(generacion, JSON_TYPE));
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void confirmarSolicitud(DTSolicitud solicitud) throws Exception {
        target = target.path("solicitud/confirmar");
        Response respuesta = target.request(JSON_TYPE).post(Entity.entity(solicitud, JSON_TYPE));
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void rechazarSolicitud(DTSolicitud solicitud) throws Exception {
        target = target.path("solicitud/rechazar");
        Response respuesta = target.request(JSON_TYPE).post(Entity.entity(solicitud, JSON_TYPE));
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void agendarReunion(DTReunion reunion) throws Exception {
        target = target.path("reunion/agendar");
        Response respuesta = target.request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        if (respuesta.getStatus() != 200 && respuesta.getStatus() != 204) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void close() {
        client.close();
    }

}
