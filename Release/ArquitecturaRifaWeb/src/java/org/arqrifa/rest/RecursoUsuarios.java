package org.arqrifa.rest;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTUsuario;

public class RecursoUsuarios extends ClienteJersey{
    
    public RecursoUsuarios() {
        super("usuarios");
    }
    
    public DTUsuario buscar(String ci) throws Exception {
        Response response = webTarget.path(ci).request(JSON_TYPE).get();
        return response.readEntity(DTUsuario.class);
    }

    public void modificar(DTUsuario usuario) throws Exception {
        Response response = webTarget.request(JSON_TYPE).put(Entity.entity(usuario, JSON_TYPE));
        comprobarEstado(response);
    }

    public void agregar(DTUsuario usuario) throws Exception {
        Response response = webTarget.request(JSON_TYPE).post(Entity.entity(usuario, JSON_TYPE));
        comprobarEstado(response);
    }

    public List<DTUsuario> listar(int generacion) throws Exception {
        Response response = webTarget.queryParam("gen", generacion).request(JSON_TYPE).get();
        comprobarEstado(response);
        return Arrays.asList(response.readEntity(DTUsuario[].class));
    }

    public void agregarAsistencia(DTAsistencia asistencia) throws Exception {
        Response response = webTarget.path("asistencias").request(JSON_TYPE).post(Entity.entity(asistencia, JSON_TYPE));
        comprobarEstado(response);
    }

    public DTUsuario login(String ci, String pass) throws Exception {
        Response response = webTarget.path("login").queryParam("ci", ci).queryParam("pass", pass).request(JSON_TYPE).get();
        comprobarEstado(response);
        return response.readEntity(DTUsuario.class);
    }
    
}
