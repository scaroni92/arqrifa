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
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTAsistenciaWrapper;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.logica.FabricaLogica;
import org.arqrifa.logica.IControladorReunion;

@Path("reuniones")
@Produces("application/json;charset=utf-8")
@Consumes("application/json;charset=utf-8")
public class RecursoReunion {

    private static final IControladorReunion CONTROLADOR = FabricaLogica.getControladorReuniones();

    @Path("{id}")
    @GET
    public DTReunion buscar(@PathParam("id") int id) {
        return CONTROLADOR.buscar(id);
    }

    @Path("siguiente")
    @GET
    public DTReunion buscarSiguiente(@QueryParam("gen") int generacion) {
        return CONTROLADOR.buscarProximaPorRealizar(generacion);
    }

    @Path("actual")
    @GET
    public DTReunion buscarActiva(@QueryParam("gen") int generacion) {
        return FabricaLogica.getControladorReuniones().BuscarActual(generacion);
    }

    @Path("ultima")
    @GET
    public DTReunion buscarUltimaFinalizada(@QueryParam("gen") int generacion) {
        return CONTROLADOR.buscarUltimaFinalizada(generacion);
    }

    @Path("{id}/asistencias")
    @GET
    public List<DTAsistencia> getLista(@PathParam("id") int id) {
        return CONTROLADOR.listarAsistencias(CONTROLADOR.buscar(id));
    }

    @GET
    public List<DTReunion> listar(@QueryParam("gen") int generacion) {
        if (generacion > 0) {
            return CONTROLADOR.listarPorGeneracion(generacion);
        }
        return CONTROLADOR.listarTodas();
    }

    @POST
    public Response agregar(DTReunion reunion) {
        CONTROLADOR.agregar(reunion);
        return Response.status(Response.Status.CREATED).build();
    }

    @Path("{id}")
    @DELETE
    public Response eliminar(@PathParam("id") int id) {
        //Se recibe el id porque que el método DELETE no permite el uso de Entity
        CONTROLADOR.eliminar(CONTROLADOR.buscar(id));
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    public Response modificar(DTReunion reunion) {
        CONTROLADOR.modificar(reunion);
        return Response.status(Response.Status.CREATED).build();
    }

    @Path("iniciar")
    @PUT
    public Response iniciar(DTReunion reunion) {
        CONTROLADOR.iniciar(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("finalizar")
    @PUT
    public Response finalizar(DTReunion reunion) {
        CONTROLADOR.finalizar(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("habilitarLista")
    @PUT
    public Response habilitarLista(DTReunion reunion) {
        CONTROLADOR.habilitarLista(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("deshabilitarLista")
    @PUT
    public Response deshabilitarLista(DTReunion reunion) {
        CONTROLADOR.deshabilitarLista(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("asistencias")
    @POST
    public void agregarAsistencia(DTAsistenciaWrapper asistencia) {
        CONTROLADOR.agregarAsistencia(asistencia.getUsuario(), asistencia.getReunion());
    }

    @Path("votacion/habilitar")
    @PUT
    public Response habilitarVotacion(DTReunion reunion) {
        CONTROLADOR.habilitarVotacion(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("votacion/deshabilitar")
    @PUT
    public Response deshabilitarVotacion(DTReunion reunion) {
        CONTROLADOR.deshabilitarVotacion(reunion);
        return Response.status(Response.Status.OK).build();
    }

}
