package org.arqrifa.rest.recursos;

import java.util.List;
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
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.logica.FabricaLogica;
import org.arqrifa.logica.IControladorSolicitud;

@Path("solicitudes")
@Produces("application/json;charset=utf-8")
@Consumes("application/json;charset=utf-8")
public class RecursoSolicitud {

    private static final IControladorSolicitud CONTROLADOR = FabricaLogica.getControladorSolicitud();

    @POST
    public Response agregar(DTSolicitud solicitud) {
        CONTROLADOR.agregarSolicitud(solicitud);
        return Response.status(Response.Status.CREATED).build();
    }

    @Path("{ci}")
    @DELETE
    public Response eliminar(@PathParam("ci") int ci) {
        CONTROLADOR.rechazarSolicitud(CONTROLADOR.buscarSolicitud(ci));
        return Response.status(Response.Status.OK).build();
    }

    @GET
    public List<DTSolicitud> listar(@QueryParam("gen") int generacion) {
        return CONTROLADOR.listarSolicitudes(generacion);
    }

    @Path("{ci}")
    @GET
    public DTSolicitud buscar(@PathParam("ci") int ci) {
        return CONTROLADOR.buscarSolicitud(ci);
    }

    @Path("verificar")
    @GET
    public Response verificar(@QueryParam("codigo") int codigo) {
        CONTROLADOR.verificarSolicitud(codigo);
        return Response.status(Response.Status.OK).build();
    }

    @Path("confirmar")
    @PUT
    public Response confirmar(DTSolicitud solicitud) {
        CONTROLADOR.confirmarSolicitud(solicitud);
        return Response.status(Response.Status.OK).build();
    }

}
