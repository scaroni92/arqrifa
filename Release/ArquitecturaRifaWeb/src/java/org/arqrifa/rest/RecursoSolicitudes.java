package org.arqrifa.rest;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTSolicitud;

public class RecursoSolicitudes extends ClienteJersey {

    public RecursoSolicitudes() {
        super("solicitudes");
    }

    public void confirmar(DTSolicitud solicitud) throws Exception {
        Response response = webTarget.path("confirmar").request(JSON_TYPE).put(Entity.entity(solicitud, JSON_TYPE));
        comprobarEstado(response);
    }

    public void eliminar(String ci) throws Exception {
        Response response = webTarget.path(ci).request(JSON_TYPE).delete();
        comprobarEstado(response);
    }

    public DTSolicitud buscar(String ci) throws Exception {
        Response response = webTarget.path(ci).request(JSON_TYPE).get();
        comprobarEstado(response);
        return response.readEntity(DTSolicitud.class);
    }

    public List<DTSolicitud> listar(int gen) throws Exception {
        Response response = webTarget.queryParam("gen", gen).request(JSON_TYPE).get();
        comprobarEstado(response);
        return Arrays.asList(response.readEntity(DTSolicitud[].class));
    }

    public void verificar(String codigo) throws Exception {
        Response response = webTarget.path("verificar").queryParam("codigo", codigo).request(JSON_TYPE).get();
        comprobarEstado(response);
    }

    public void agregar(DTSolicitud solicitud) throws Exception {
        Response response = webTarget.request(JSON_TYPE).post(Entity.entity(solicitud, JSON_TYPE));
        comprobarEstado(response);
    }

}
