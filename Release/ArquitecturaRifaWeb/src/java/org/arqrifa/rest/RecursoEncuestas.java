package org.arqrifa.rest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTVotacion;

public class RecursoEncuestas extends ClienteJersey {

    public RecursoEncuestas() {
        super("encuestas");
    }

    public void agregar(DTReunion reunion) throws Exception {
        Response response = webTarget.request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public void modificar(DTReunion reunion) throws Exception {
        Response response = webTarget.request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public void eliminar(int reunionId) throws Exception {
        Response response = webTarget.path(String.valueOf(reunionId)).request(JSON_TYPE).delete();
        comprobarEstado(response);
    }

    public void agregarVotacion(DTVotacion votacion) throws Exception {
        Response respuesta = webTarget.path("votacion").request(JSON_TYPE).post(Entity.entity(votacion, JSON_TYPE));
        comprobarEstado(respuesta);
    }

    public DTVotacion buscarVotacion(int ci, int reunionId) throws Exception {
        Response respuesta = webTarget.path("votacion").queryParam("ci", ci).queryParam("reunionId", reunionId).request(JSON_TYPE).get();
        comprobarEstado(respuesta);
        return respuesta.readEntity(DTVotacion.class);
    }

}
