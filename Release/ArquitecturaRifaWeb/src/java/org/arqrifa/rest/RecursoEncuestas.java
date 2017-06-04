package org.arqrifa.rest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTVoto;

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

    public void iniciarVotacion(DTReunion reunion) throws Exception {
        Response respuesta = webTarget.path("iniciar").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(respuesta);
    }

    public void finalizarVotacion(DTReunion reunion) throws Exception {
        Response respuesta = webTarget.path("finalizar").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(respuesta);
    }

    public void agregarVoto(DTVoto voto) throws Exception {
        Response respuesta = webTarget.path("voto").request(JSON_TYPE).post(Entity.entity(voto, JSON_TYPE));
        comprobarEstado(respuesta);
    }

}
