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
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTEstadoAsistencia;
import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTVoto;
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
    @Path("/asistencia/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregarAsistencia(DTAsistencia asistencia) {
        FabricaLogica.getControladorReuniones().agregarAsistencia(asistencia.getUsuario(), asistencia.getReunion());
        return Response.status(Response.Status.OK).build();
    }

    @Path("/reunion/iniciadas")
    @GET
    public List<DTReunion> getReunionesActivas() {
        return FabricaLogica.getControladorReuniones().listarReunionesIniciadas();
    }

    @Path("/reunion/agendar")
    @POST
    public Response agregarReunion(DTReunion reunion) {
        FabricaLogica.getControladorReuniones().agregarReunion(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/reunion/eliminar")
    @POST
    public Response eliminarReunion(DTReunion reunion) {
        FabricaLogica.getControladorReuniones().eliminarReunion(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/reunion/modificar")
    @POST
    public Response modificarReunion(DTReunion reunion) {
        FabricaLogica.getControladorReuniones().modificarReunion(reunion);
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

    @Path("reunion/ultima_finalizada")
    @GET
    public DTReunion buscarUltimaReunion(@QueryParam("id_gen") int id_gen) {
        return FabricaLogica.getControladorReuniones().buscarUltimaReunionFinalizada(id_gen);
    }

    @Path("reunion/listar_por_generacion")
    @GET
    public List<DTReunion> listarReunionesPorGeneracion(@QueryParam("id_gen") int id_gen) {
        return FabricaLogica.getControladorReuniones().listarReunionesPorGeneracion(id_gen);
    }

    @Path("reunion/listar_asistencias")
    @POST
    public List<DTEstadoAsistencia> listarAsistecnias(DTReunion reunion) {
        return FabricaLogica.getControladorReuniones().listarAsistencias(reunion);
    }

    @Path("reunion/siguiente")
    @GET
    public DTReunion buscarSiguienteReunion(@QueryParam("id_gen") int id_gen) {
        return FabricaLogica.getControladorReuniones().buscarProximaReunionPorRealizar(id_gen);
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

    @Path("/encuesta/buscar")
    @GET
    public DTEncuesta buscarEncuesta(@QueryParam("id") int id) {
        return FabricaLogica.getControladorEncuesta().buscarEncuesta(id);
    }

    @Path("/encuesta/agregar")
    @POST
    public Response agregarEncuesta(DTReunion reunion) {
        FabricaLogica.getControladorEncuesta().agregarEncuesta(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/encuesta/eliminar")
    @POST
    public Response eliminarEncuesta(DTEncuesta encuesta) {
        FabricaLogica.getControladorEncuesta().eliminarEncuesta(encuesta);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/encuesta/modificar")
    @POST
    public Response modificarEncuesta(DTEncuesta encuesta) {
        FabricaLogica.getControladorEncuesta().modificarEncuesta(encuesta);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/encuesta/iniciar_votacion")
    @POST
    public Response iniciarVotacionEncuesta(DTReunion reunion) {
        FabricaLogica.getControladorEncuesta().habilitarVotacionEncuesta(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/encuesta/votar")
    @POST
    public Response agregarVoto(DTVoto voto) {
        FabricaLogica.getControladorEncuesta().agregarVoto(voto);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/estudiante/listar_por_generacion")
    @GET
    public List<DTUsuario> listarEstudiantesPorGeneracion(@QueryParam("id_gen") int id_gen) {
        return FabricaLogica.getLogicaUsuario().listarEstudiantes(id_gen);
    }

    @Path("/usuario/buscar")
    @GET
    public DTUsuario buscarUsuario(@QueryParam("ci") int ci) {
        return FabricaLogica.getLogicaUsuario().buscarUsuario(ci);
    }

    @Path("/usuario/listar")
    @GET
    public List<DTUsuario> listarTodos() {
        return FabricaLogica.getLogicaUsuario().listarTodos();
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
    public List<DTSolicitud> getSolicitudes(@QueryParam("generacion") int generacion) {
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
