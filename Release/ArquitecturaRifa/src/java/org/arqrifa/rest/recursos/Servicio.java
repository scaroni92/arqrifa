package org.arqrifa.rest.recursos;

import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.logica.FabricaLogica;
import org.arqrifa.repository.Repository;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/servicio")
@Produces(MediaType.APPLICATION_JSON)
public class Servicio {

    @Path("/login")
    @GET
    public DTUsuario login(@QueryParam("ci") int ci, @QueryParam("pass") String pass) {
        return FabricaLogica.getLogicaUsuario().Autenticar(ci, pass);
    }

    @POST
    @Path("/asistencias/marcar")
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
    public Response marcarAsistencia(@FormDataParam("usuario") DTUsuario usuario, @FormDataParam("reunion") DTReunion reunion) {
        if (usuario != null && reunion != null) {
            System.out.println("servidor rest: " + usuario.getNombre() + " " + reunion.getId());
        }
        FabricaLogica.getControladorReuniones().MarcarAsistencia(usuario, reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/reuniones/getActivas")
    @GET
    public List<DTReunion> getReunionesActivas() {
        //return Repository.reunionesActivas;
        return FabricaLogica.getControladorReuniones().getReunionesActivas();
    }

}
