package org.arqrifa.rest.recursos;

//<editor-fold defaultstate="collapsed" desc="imports">
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.logica.FabricaLogica;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTSolicitud;
import org.glassfish.jersey.media.multipart.FormDataParam;
//</editor-fold>

@Path("/servicio")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
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
        return FabricaLogica.getControladorReuniones().getReunionesActivas();
    }

    @Path("/solicitud/enviar")
    @POST
    public Response enviarSolicitud(DTSolicitud solicitud) {
        FabricaLogica.getLogicaUsuario().altaSolicitud(solicitud);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/solicitud/listar")
    @POST
    public List<DTSolicitud> getSolicitudes(DTUsuario usuario) {
        return FabricaLogica.getControladorGeneracion().ListarSolicitudes(usuario);
    }

    @Path("/solicitud/verificar")
    @GET
    public Response verificarSolicitud(@QueryParam("codigo") int codigo) {
        FabricaLogica.getLogicaUsuario().verificarSolicitud(codigo);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/generacion/listar")
    @GET
    public List<DTGeneracion> listarGeneraciones() {
        return FabricaLogica.getControladorGeneracion().listarGeneraciones();
    }
    
    @Path("/generacion/agregar")
    @POST
    public Response agregarGeneracion(DTGeneracion generacion) {
        FabricaLogica.getControladorGeneracion().altaGeneracion(generacion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/encargado/agregar")
    @POST
    public Response agregarEncargado(DTUsuario usuario) {
        FabricaLogica.getLogicaUsuario().altaEncargado(usuario);
        return Response.status(Response.Status.OK).build();
    }
}
