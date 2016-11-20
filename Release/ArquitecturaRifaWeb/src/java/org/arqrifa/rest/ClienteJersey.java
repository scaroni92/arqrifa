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
    private final WebTarget TARGET;
    private final Client CLIENT;
    private final String JSON_TYPE = "application/json;charset=utf-8";

    public ClienteJersey() {
        CLIENT = ClientBuilder.newClient();
        TARGET = CLIENT.target(BASE_URI).path("servicio");

    }

    public DTUsuario login(int ci, String pass) throws ClientErrorException, Exception {
        Response respuesta = TARGET.path("login").queryParam("ci", ci).queryParam("pass", pass).request(JSON_TYPE).get();

        if (respuesta.getStatus() == 200) {
            return respuesta.readEntity(DTUsuario.class);
        } else if (respuesta.getStatus() != 204) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        return null;
    }

    public void enviarSolicitud(DTSolicitud solicitud) throws Exception {
        Response respuesta = TARGET.path("solicitud/enviar").request(JSON_TYPE).post(Entity.entity(solicitud, JSON_TYPE));

        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void verificarSolicitud(int codigo) throws ClientErrorException, Exception {
        Response respuesta = TARGET.path("solicitud/verificar").queryParam("codigo", codigo).request(JSON_TYPE).get();

        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public List<DTSolicitud> listarSolicitudes(int generacion) throws Exception {
        Response respuesta = TARGET.path("solicitud/listar").queryParam("generacion", generacion).request(JSON_TYPE).get();

        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        return Arrays.asList(respuesta.readEntity(DTSolicitud[].class));
    }

    public List<DTGeneracion> listarGeneraciones() throws Exception {

        Response respuesta = TARGET.path("generacion/listar").request(JSON_TYPE).get();

        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        return Arrays.asList(respuesta.readEntity(DTGeneracion[].class));
    }

    public void agregarEncargado(DTUsuario usuario) throws Exception {
        Response respuesta = TARGET.path("encargado/agregar").request(JSON_TYPE).post(Entity.entity(usuario, JSON_TYPE));
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void agregarGeneracion(DTGeneracion generacion) throws Exception {
        Response respuesta = TARGET.path("generacion/agregar").request(JSON_TYPE).post(Entity.entity(generacion, JSON_TYPE));
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void confirmarSolicitud(DTSolicitud solicitud) throws Exception {
        Response respuesta = TARGET.path("solicitud/confirmar").request(JSON_TYPE).post(Entity.entity(solicitud, JSON_TYPE));
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }
    
    public DTSolicitud buscarSolicitud(int ci) throws Exception {
        Response respuesta = TARGET.path("solicitud/buscar").queryParam("ci", ci).request(JSON_TYPE).get();
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        return respuesta.readEntity(DTSolicitud.class);
    }

    public void rechazarSolicitud(DTSolicitud solicitud) throws Exception {
        Response respuesta = TARGET.path("solicitud/rechazar").request(JSON_TYPE).post(Entity.entity(solicitud, JSON_TYPE));
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void agendarReunion(DTReunion reunion) throws Exception {
        Response respuesta = TARGET.path("reunion/agendar").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        if (respuesta.getStatus() != 200 && respuesta.getStatus() != 204) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public DTReunion buscarReunion(int id) throws Exception {
        Response respuesta = TARGET.path("reunion/buscar").queryParam("id", id).request(JSON_TYPE).get();
        
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        return respuesta.readEntity(DTReunion.class);
    }
    
    public void iniciarReunion(DTReunion reunion) throws Exception {
        Response respuesta = TARGET.path("reunion/iniciar").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    public void close() {
        CLIENT.close();
    }

}
