package org.arqrifa.rest.recursos;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTVotacion;
import org.arqrifa.logica.FabricaLogica;

@Path("encuestas")
@Produces("application/json;charset=utf-8")
@Consumes("application/json;charset=utf-8")
public class RecursoEncuesta {

    @POST
    public Response agregar(DTReunion reunion) {
        FabricaLogica.getControladorEncuesta().agregar(reunion);
        return Response.status(Response.Status.CREATED).build();
    }

    @Path("{reunionId}")
    @DELETE
    public Response eliminar(@PathParam("reunionId") int id) {
        FabricaLogica.getControladorEncuesta().eliminar(FabricaLogica.getControladorReuniones().buscar(id));
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    public Response modificar(DTReunion reunion) {
        FabricaLogica.getControladorEncuesta().modificar(reunion);
        return Response.status(Response.Status.OK).build();
    }
    
    @Path("votacion")
    @POST
    public Response agregarVotacion(DTVotacion votacion) {
        FabricaLogica.getControladorEncuesta().agregarVotacion(votacion);
        return Response.status(Response.Status.CREATED).build();
    }
    
    //todo: reubicar
    @Path("votacion")
    @GET
    public DTVotacion buscarVotacion(@QueryParam("ci") int ci, @QueryParam("reunionId") int reunionId) {
        return FabricaLogica.getControladorEncuesta().buscarVotacion(ci, reunionId);
    }
}
