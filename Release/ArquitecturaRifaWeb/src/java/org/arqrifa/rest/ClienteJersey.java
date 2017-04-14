package org.arqrifa.rest;

import java.util.Arrays;
import java.util.List;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTMensajeError;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTAsistenciaWrapper;
import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTVoto;

public class ClienteJersey {

    private static final String BASE_URI = "http://localhost:8080/ArquitecturaRifa/api";
    private final WebTarget TARGET;
    private final Client CLIENT;
    private final String JSON_TYPE = "application/json;charset=utf-8";

    public ClienteJersey() {
        CLIENT = ClientBuilder.newClient();
        TARGET = CLIENT.target(BASE_URI).path("servicio");
    }

    private void comprobarError(Response respuesta) throws Exception {
        switch (respuesta.getStatus()) {
            case 400:
                throw new Exception("Solicitud incorrecta");
            case 404:
                throw new Exception("Recurso no encontrado");
            case 409:
                throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Usuarios">
    public DTUsuario login(int ci, String pass) throws ClientErrorException, Exception {
        Response respuesta = TARGET.path("login").queryParam("ci", ci).queryParam("pass", pass).request(JSON_TYPE).get();
        comprobarError(respuesta);
        return respuesta.readEntity(DTUsuario.class);
    }

    public void agregarUsuario(DTUsuario usuario) throws Exception {
        Response respuesta = TARGET.path("usuario/agregar").request(JSON_TYPE).post(Entity.entity(usuario, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void modificarUsuario(DTUsuario usuario) throws Exception {
        Response respuesta = TARGET.path("usuario/modificar").request(JSON_TYPE).post(Entity.entity(usuario, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void agregarAsistencia(DTReunion reunion, DTUsuario usuario) throws Exception {
        Response respuesta = TARGET.path("asistencia/agregar").request(JSON_TYPE).post(Entity.entity(new DTAsistenciaWrapper(usuario, reunion), JSON_TYPE));
        comprobarError(respuesta);
    }

    public DTUsuario buscarUsuario(int ci) throws Exception {
        Response respuesta = TARGET.path("usuario/buscar").queryParam("ci", ci).request(JSON_TYPE).get();
        comprobarError(respuesta);
        return respuesta.readEntity(DTUsuario.class);
    }

    public List<DTUsuario> listarUsuarios() throws Exception {
        Response respuesta = TARGET.path("usuario/listar").request(JSON_TYPE).get();
        comprobarError(respuesta);
        return Arrays.asList(respuesta.readEntity(DTUsuario[].class));
    }

    public List<DTUsuario> listarEstudiantesPorGeneracion(int id_gen) throws Exception {
        Response respuesta = TARGET.path("estudiante/listar_por_generacion").queryParam("id_gen", id_gen).request(JSON_TYPE).get();
        comprobarError(respuesta);
        return Arrays.asList(respuesta.readEntity(DTUsuario[].class));
    }

    public List<DTAsistencia> listarAsistencias(DTReunion reunion) throws Exception {
        Response respuesta = TARGET.path("reunion/listar_asistencias").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarError(respuesta);
        return Arrays.asList(respuesta.readEntity(DTAsistencia[].class));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Generaciones">
    public void agregarGeneracion(DTGeneracion generacion) throws Exception {
        Response respuesta = TARGET.path("generacion/agregar").request(JSON_TYPE).post(Entity.entity(generacion, JSON_TYPE));
        comprobarError(respuesta);
    }

    public List<DTGeneracion> listarGeneraciones() throws Exception {
        Response respuesta = TARGET.path("generacion/listar").request(JSON_TYPE).get();
        comprobarError(respuesta);
        return Arrays.asList(respuesta.readEntity(DTGeneracion[].class));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Solicitudes">
    public void agregarSolicitud(DTSolicitud solicitud) throws Exception {
        Response respuesta = TARGET.path("solicitud/agregar").request(JSON_TYPE).post(Entity.entity(solicitud, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void verificarSolicitud(int codigo) throws ClientErrorException, Exception {
        Response respuesta = TARGET.path("solicitud/verificar").queryParam("codigo", codigo).request(JSON_TYPE).get();
        comprobarError(respuesta);
    }

    public void confirmarSolicitud(DTSolicitud solicitud) throws Exception {
        Response respuesta = TARGET.path("solicitud/confirmar").request(JSON_TYPE).post(Entity.entity(solicitud, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void rechazarSolicitud(DTSolicitud solicitud) throws Exception {
        Response respuesta = TARGET.path("solicitud/rechazar").request(JSON_TYPE).post(Entity.entity(solicitud, JSON_TYPE));
        comprobarError(respuesta);
    }

    public DTSolicitud buscarSolicitud(int ci) throws Exception {
        Response respuesta = TARGET.path("solicitud/buscar").queryParam("ci", ci).request(JSON_TYPE).get();
        comprobarError(respuesta);
        return respuesta.readEntity(DTSolicitud.class);
    }

    public List<DTSolicitud> listarSolicitudes(int generacion) throws Exception {
        Response respuesta = TARGET.path("solicitud/listar").queryParam("generacion", generacion).request(JSON_TYPE).get();
        comprobarError(respuesta);
        return Arrays.asList(respuesta.readEntity(DTSolicitud[].class));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Reuniones">
    public void agregarReunion(DTReunion reunion) throws Exception {
        Response respuesta = TARGET.path("reunion/agregar").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void eliminarReunion(DTReunion reunion) throws Exception {
        Response respuesta = TARGET.path("reunion/eliminar").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void modificarReunion(DTReunion reunion) throws Exception {
        Response respuesta = TARGET.path("reunion/modificar").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void iniciarReunion(DTReunion reunion) throws Exception {
        Response respuesta = TARGET.path("reunion/iniciar").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void habilitarLista(DTReunion reunion) throws Exception {
        Response respuesta = TARGET.path("reunion/habilitar_lista").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void deshabilitarLista(DTReunion reunion) throws Exception {
        Response respuesta = TARGET.path("reunion/deshabilitar_lista").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void finalizarReunion(DTReunion reunion) throws Exception {
        Response respuesta = TARGET.path("reunion/finalizar").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarError(respuesta);
    }

    public DTReunion buscarReunion(int id) throws Exception {
        Response respuesta = TARGET.path("reunion/buscar").queryParam("id", id).request(JSON_TYPE).get();
        comprobarError(respuesta);
        return respuesta.readEntity(DTReunion.class);
    }

    public DTReunion buscarSiguienteReunion(int id_gen) throws Exception {
        Response respuesta = TARGET.path("reunion/siguiente").queryParam("id_gen", id_gen).request(JSON_TYPE).get();
        comprobarError(respuesta);
        return respuesta.readEntity(DTReunion.class);
    }

    public DTReunion buscarUltimaReunionFinalizada(int id_gen) throws Exception {
        Response respuesta = TARGET.path("reunion/ultima_finalizada").queryParam("id_gen", id_gen).request(JSON_TYPE).get();
        comprobarError(respuesta);
        return respuesta.readEntity(DTReunion.class);
    }
    
     public DTReunion buscarReunionDelDia(int id_gen) throws Exception {
        Response respuesta = TARGET.path("reunion/activa").queryParam("id_gen", id_gen).request(JSON_TYPE).get();
        comprobarError(respuesta);
        return respuesta.readEntity(DTReunion.class);
    }

    public List<DTReunion> listarReunionesTodas() throws Exception {
        Response respuesta = TARGET.path("reunion/listar").request(JSON_TYPE).get();
        comprobarError(respuesta);
        return Arrays.asList(respuesta.readEntity(DTReunion[].class));
    }

    public List<DTReunion> listarReunionesPorGeneracion(int id_gen) throws Exception {
        Response respuesta = TARGET.path("reunion/listar_por_generacion").queryParam("id_gen", id_gen).request(JSON_TYPE).get();
        comprobarError(respuesta);
        return Arrays.asList(respuesta.readEntity(DTReunion[].class));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Encuestas">
    public void agregarEncuesta(DTReunion reunion) throws Exception {
        Response respuesta = TARGET.path("encuesta/agregar").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void eliminarEncuesta(DTEncuesta encuesta) throws Exception {
        Response respuesta = TARGET.path("encuesta/eliminar").request(JSON_TYPE).post(Entity.entity(encuesta, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void modificarEncuesta(DTEncuesta encuesta) throws Exception {
        Response respuesta = TARGET.path("encuesta/modificar").request(JSON_TYPE).post(Entity.entity(encuesta, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void iniciarVotacion(DTReunion reunion) throws Exception {
        Response respuesta = TARGET.path("encuesta/iniciar_votacion").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void finalizarVotacion(DTReunion reunion) throws Exception {
        Response respuesta = TARGET.path("encuesta/finalizar_votacion").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarError(respuesta);
    }

    public void agregarVoto(DTVoto voto) throws Exception {
        Response respuesta = TARGET.path("encuesta/votar").request(JSON_TYPE).post(Entity.entity(voto, JSON_TYPE));
        comprobarError(respuesta);
    }

    public DTEncuesta buscarEncuesta(int id) throws Exception {
        Response respuesta = TARGET.path("encuesta/buscar").queryParam("id", id).request(JSON_TYPE).get();
        comprobarError(respuesta);
        return respuesta.readEntity(DTEncuesta.class);
    }
    //</editor-fold>
}
