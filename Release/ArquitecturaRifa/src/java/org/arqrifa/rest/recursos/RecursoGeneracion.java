package org.arqrifa.rest.recursos;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.logica.FabricaLogica;

@Path("generaciones")
@Produces("application/json;charset=utf-8")
@Consumes("application/json;charset=utf-8")
public class RecursoGeneracion {
    
    @POST
    public Response agregar(DTGeneracion generacion){
        FabricaLogica.getControladorGeneracion().agregarGeneracion(generacion);
        return Response.status(Response.Status.CREATED).build();
    }
    
    /*
    @DELETE
    public Response eliminar(DTGeneracion generacion){
        return Response.status(Response.Status.OK).build();
    }*/
    
    @GET
    public List<DTGeneracion> listar(){
        return  FabricaLogica.getControladorGeneracion().listarGeneraciones();
    }
}
