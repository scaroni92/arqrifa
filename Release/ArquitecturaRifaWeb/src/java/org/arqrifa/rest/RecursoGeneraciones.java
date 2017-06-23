package org.arqrifa.rest;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTGeneracion;

public class RecursoGeneraciones extends ClienteJersey{

    public RecursoGeneraciones() {
        super("generaciones");
    }
    
    public List<DTGeneracion> listar() throws Exception {
        Response response = webTarget.request(JSON_TYPE).get();
        comprobarEstado(response);
        return Arrays.asList(response.readEntity(DTGeneracion[].class));
    }

    public void agregar(DTGeneracion generacion) throws Exception {
        Response response = webTarget.request(JSON_TYPE).post(Entity.entity(generacion, JSON_TYPE));
        comprobarEstado(response);
    }
}
