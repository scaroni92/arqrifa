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
        return FabricaLogica.getLogicaUsuario().autenticar(ci, pass);
    }

    @POST
    @Path("/asistencias/marcar")
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
    public Response marcarAsistencia(@FormDataParam("usuario") DTUsuario usuario, @FormDataParam("reunion") DTReunion reunion) {
        FabricaLogica.getControladorReuniones().MarcarAsistencia(usuario, reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/reuniones/getActivas")
    @GET
    public List<DTReunion> getReunionesActivas() {
        return FabricaLogica.getControladorReuniones().listarReunionesIniciadas();
    }

    @Path("/reunion/agendar")
    @POST
    public Response agendarReunion(DTReunion reunion) {
        FabricaLogica.getControladorReuniones().agregarReunion(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/reunion/buscar")
    @GET
    public DTReunion buscarReunion(@QueryParam("id") int id) {
        return FabricaLogica.getControladorReuniones().buscarReunion(id);
    }

    @Path("/reunion/iniciar")
    @POST
    public Response iniciarReunion(DTReunion reunion) {
        FabricaLogica.getControladorReuniones().iniciarReunion(reunion);
        return Response.status(Response.Status.OK).build();
    }

    
    @Path("/reunion/finalizar")
    @POST
    public Response finalizarReunion(DTReunion reunion) {
        FabricaLogica.getControladorReuniones().finalizarReunion(reunion);
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
        FabricaLogica.getControladorGeneracion().agregarGeneracion(generacion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/encargado/agregar")
    @POST
    public Response agregarEncargado(DTUsuario usuario) {
        FabricaLogica.getLogicaUsuario().agregarEncargado(usuario);
        return Response.status(Response.Status.OK).build();
    }

    //<editor-fold defaultstate="collapsed" desc="Solicitudes">
    @Path("/solicitud/enviar")
    @POST
    public Response enviarSolicitud(DTSolicitud solicitud) {
        FabricaLogica.getControladorSolicitud().agregarSolicitud(solicitud);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/solicitud/listar")
    @GET
    public List<DTSolicitud> getSolicitudes(@QueryParam("generacion")int generacion) {
        return FabricaLogica.getControladorSolicitud().listarSolicitudes(generacion);
    }

    @Path("/solicitud/verificar")
    @GET
    public Response verificarSolicitud(@QueryParam("codigo") int codigo) {
        FabricaLogica.getControladorSolicitud().verificarSolicitud(codigo);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/solicitud/confirmar")
    @POST
    public Response confirmarSolicitud(DTSolicitud solicitud) {
        FabricaLogica.getControladorSolicitud().confirmarSolicitud(solicitud);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/solicitud/rechazar")
    @POST
    public Response rechazarSolicitud(DTSolicitud solicitud) {
        FabricaLogica.getControladorSolicitud().rechazarSolicitud(solicitud);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/solicitud/buscar")
    @GET
    public DTSolicitud buscarSolicitud(@QueryParam("ci") int ci) {
        return FabricaLogica.getControladorSolicitud().buscarSolicitud(ci);
    }
    //</editor-fold>
}
