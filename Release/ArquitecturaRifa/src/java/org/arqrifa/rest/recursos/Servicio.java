package org.arqrifa.rest.recursos;

import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.logica.FabricaLogica;
import java.util.List;
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

@Path("/servicio")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class Servicio {

    //<editor-fold defaultstate="collapsed" desc="Usuarios">
    @Path("/login")
    @GET
    public DTUsuario login(@QueryParam("ci") int ci, @QueryParam("pass") String pass) {
        return FabricaLogica.getLogicaUsuario().autenticar(ci, pass);
    }

    @Path("/usuario/agregar")
    @POST
    public Response agregarUsuario(DTUsuario usuario) {
        FabricaLogica.getLogicaUsuario().agregar(usuario);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/usuario/modificar")
    @POST
    public Response modificarUsuario(DTUsuario usuario) {
        FabricaLogica.getLogicaUsuario().modificar(usuario);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/usuario/buscar")
    @GET
    public DTUsuario buscarUsuario(@QueryParam("ci") int ci) {
        return FabricaLogica.getLogicaUsuario().buscar(ci);
    }

    @Path("/usuario/listar")
    @GET
    public List<DTUsuario> listarTodos() {
        return FabricaLogica.getLogicaUsuario().listarTodos();
    }

    @POST
    @Path("/asistencia/agregar")
    public Response agregarAsistencia(DTAsistencia asistencia) {
        FabricaLogica.getControladorReuniones().agregarAsistencia(asistencia.getUsuario(), asistencia.getReunion());
        return Response.status(Response.Status.OK).build();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Generaciones">
    @Path("/generacion/agregar")
    @POST
    public Response agregarGeneracion(DTGeneracion generacion) {
        FabricaLogica.getControladorGeneracion().agregarGeneracion(generacion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/generacion/listar")
    @GET
    public List<DTGeneracion> listarGeneraciones() {
        return FabricaLogica.getControladorGeneracion().listarGeneraciones();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Reuniones">
    @Path("/reunion/agregar")
    @POST
    public Response agregarReunion(DTReunion reunion) {
        FabricaLogica.getControladorReuniones().agregar(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/reunion/eliminar")
    @POST
    public Response eliminarReunion(DTReunion reunion) {
        FabricaLogica.getControladorReuniones().eliminar(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/reunion/modificar")
    @POST
    public Response modificarReunion(DTReunion reunion) {
        FabricaLogica.getControladorReuniones().modificar(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/reunion/iniciar")
    @POST
    public Response iniciarReunion(DTReunion reunion) {
        FabricaLogica.getControladorReuniones().iniciar(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/reunion/habilitar_lista")
    @POST
    public Response habilitarLista(DTReunion reunion) {
        FabricaLogica.getControladorReuniones().habilitarLista(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/reunion/deshabilitar_lista")
    @POST
    public Response deshabilitarLista(DTReunion reunion) {
        FabricaLogica.getControladorReuniones().deshabilitarLista(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/reunion/finalizar")
    @POST
    public Response finalizarReunion(DTReunion reunion) {
        FabricaLogica.getControladorReuniones().finalizar(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/reunion/buscar")
    @GET
    public DTReunion buscarReunion(@QueryParam("id") int id) {
        return FabricaLogica.getControladorReuniones().buscar(id);
    }

    @Path("reunion/listar")
    @GET
    public List<DTReunion> listarTodas() {
        return FabricaLogica.getControladorReuniones().listarTodas();
    }

    @Path("/reunion/iniciadas")
    @GET
    public List<DTReunion> listarReunionesIniciadas() {
        return FabricaLogica.getControladorReuniones().listarIniciadas();
    }

    @Path("reunion/ultima_finalizada")
    @GET
    public DTReunion buscarUltimaReunion(@QueryParam("id_gen") int id_gen) {
        return FabricaLogica.getControladorReuniones().buscarUltimaReunionFinalizada(id_gen);
    }

    @Path("reunion/listar_por_generacion")
    @GET
    public List<DTReunion> listarReunionesPorGeneracion(@QueryParam("id_gen") int id_gen) {
        return FabricaLogica.getControladorReuniones().listarPorGeneracion(id_gen);
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

    @Path("reunion/activa")
    @GET
    public DTReunion buscarReunionDelDia(@QueryParam("id_gen") int id_gen) {
        return FabricaLogica.getControladorReuniones().BuscarReunionDelDia(id_gen);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Encuestas">
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
        FabricaLogica.getControladorEncuesta().habilitarVotacion(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/encuesta/finalizar_votacion")
    @POST
    public Response finalizarVotacionEncuesta(DTReunion reunion) {
        FabricaLogica.getControladorEncuesta().deshabilitarVotacion(reunion);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/encuesta/votar")
    @POST
    public Response agregarVoto(DTVoto voto) {
        FabricaLogica.getControladorEncuesta().agregarVoto(voto);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/encuesta/buscar")
    @GET
    public DTEncuesta buscarEncuesta(@QueryParam("id") int id) {
        return FabricaLogica.getControladorEncuesta().buscarEncuesta(id);
    }

    @Path("/estudiante/listar_por_generacion")
    @GET
    public List<DTUsuario> listarEstudiantesPorGeneracion(@QueryParam("id_gen") int id_gen) {
        return FabricaLogica.getLogicaUsuario().listarEstudiantes(id_gen);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Solicitudes">
    @Path("/solicitud/agregar")
    @POST
    public Response enviarSolicitud(DTSolicitud solicitud) {
        FabricaLogica.getControladorSolicitud().agregarSolicitud(solicitud);
        return Response.status(Response.Status.OK).build();
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

    @Path("/solicitud/listar")
    @GET
    public List<DTSolicitud> getSolicitudes(@QueryParam("generacion") int generacion) {
        return FabricaLogica.getControladorSolicitud().listarSolicitudes(generacion);
    }
    //</editor-fold>
}
