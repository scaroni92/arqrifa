package org.arqrifa.rest;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTAsistenciaWrapper;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;

public class RecursoReuniones extends ClienteJersey {

    public RecursoReuniones() {
        super("reuniones");
    }

    public DTReunion buscar(String id) throws Exception {
        Response response = webTarget.path(id).request(JSON_TYPE).get();
        comprobarEstado(response);
        return response.readEntity(DTReunion.class);
    }

    public DTReunion siguiente(int gen) throws Exception {
        Response response = webTarget.path("siguiente").queryParam("gen", gen).request(JSON_TYPE).get();
        comprobarEstado(response);
        return response.readEntity(DTReunion.class);
    }

    public DTReunion ultimaFinalizada(int gen) throws Exception {
        Response response = webTarget.path("ultima").queryParam("gen", gen).request(JSON_TYPE).get();
        comprobarEstado(response);
        return response.readEntity(DTReunion.class);
    }

    public DTReunion buscarActual(int gen) throws Exception {
        Response response = webTarget.path("actual").queryParam("gen", gen).request(JSON_TYPE).get();
        comprobarEstado(response);
        return response.readEntity(DTReunion.class);
    }

    public void agregar(DTReunion reunion) throws Exception {
        Response response = webTarget.request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public void modificar(DTReunion reunion) throws Exception {
        Response response = webTarget.request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public void eliminar(int id) throws Exception {
        Response response = webTarget.path(String.valueOf(id)).request(JSON_TYPE).delete();
        comprobarEstado(response);
    }

    public List<DTReunion> listar(int gen) throws Exception {
        Response response = webTarget.queryParam("gen", gen).request(JSON_TYPE).get();
        comprobarEstado(response);
        return Arrays.asList(response.readEntity(DTReunion[].class));
    }

    public void iniciar(DTReunion reunion) throws Exception {
        Response response = webTarget.path("iniciar").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public void finalizar(DTReunion reunion) throws Exception {
        Response response = webTarget.path("finalizar").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public void hablitarLista(DTReunion reunion) throws Exception {
        Response response = webTarget.path("habilitarLista").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public void deshablitarLista(DTReunion reunion) throws Exception {
        Response response = webTarget.path("deshabilitarLista").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public void habilitarVotacion(DTReunion reunion) throws Exception {
        Response respuesta = webTarget.path("votacion/habilitar").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(respuesta);
    }

    public void deshabilitarVotacion(DTReunion reunion) throws Exception {
        Response respuesta = webTarget.path("votacion/deshabilitar").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(respuesta);
    }

    public List<DTAsistencia> listarAsistencias(int reunionId) throws Exception {
        Response response = webTarget.path(reunionId + "/asistencias").request(JSON_TYPE).get();
        comprobarEstado(response);
        return Arrays.asList(response.readEntity(DTAsistencia[].class));
    }

    public void agregarAsistencia(DTUsuario usuario, DTReunion reunion) throws Exception {
        Response response = webTarget.path("asistencias").request(JSON_TYPE).post(Entity.entity(new DTAsistenciaWrapper(usuario, reunion), JSON_TYPE));
        comprobarEstado(response);
    }
}
