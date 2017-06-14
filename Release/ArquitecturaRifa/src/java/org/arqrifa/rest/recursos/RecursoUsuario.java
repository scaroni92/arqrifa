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
import org.arqrifa.datatypes.DTAsistenciaWrapper;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.logica.FabricaLogica;
import org.arqrifa.logica.IControladorUsuario;

@Path("usuarios")
@Produces("application/json;charset=utf-8")
@Consumes("application/json;charset=utf-8")
public class RecursoUsuario {
    
    private static final IControladorUsuario CONTROLADOR = FabricaLogica.getLogicaUsuario();

    @POST
    public Response agregar(DTUsuario usuario) {
        CONTROLADOR.agregar(usuario);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    public Response modificar(DTUsuario usuario) {
        CONTROLADOR.modificar(usuario);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    public List<DTUsuario> listar(@QueryParam("gen") int generacion) {
        if (generacion != 0) {
            return CONTROLADOR.listarEstudiantes(generacion);
        } else {
            return CONTROLADOR.listarTodos();
        }
    }

    @Path("{ci}")
    @GET
    public DTUsuario buscar(@PathParam("ci") int ci) {
        return CONTROLADOR.buscar(ci);
    }

    @Path("login")
    @GET
    public DTUsuario login(@QueryParam("ci") int ci, @QueryParam("pass") String pass) {
        return CONTROLADOR.autenticar(ci, pass);
    }

    @Path("asistencias")
    @POST
    public Response agregarAsistencia(DTAsistenciaWrapper asistenciaWrapper) {
        FabricaLogica.getControladorReuniones().agregarAsistencia(asistenciaWrapper.getUsuario(), asistenciaWrapper.getReunion());
        return Response.status(Response.Status.CREATED).build();
    }

}
