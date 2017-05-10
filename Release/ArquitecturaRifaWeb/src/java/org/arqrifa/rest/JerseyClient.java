package org.arqrifa.rest;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.arqrifa.datatypes.DTAsistencia;
import org.arqrifa.datatypes.DTAsistenciaWrapper;
import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTMensajeError;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTVoto;

public class JerseyClient {

    private final WebTarget webTarget;
    private static final String BASE_URI = "http://localhost:8080/ArquitecturaRifa/api";
    private static final String JSON_TYPE = "application/json;charset=utf-8";

    public JerseyClient() {
        webTarget = ClientBuilder.newClient().target(BASE_URI);
    }

    private void comprobarEstado(Response response) throws Exception {
        switch (response.getStatus()) {
            case 400:
                throw new Exception("Solicitud incorrecta");
            case 404:
                throw new Exception("Recurso no encontrado");
            case 409:
                throw new Exception(response.readEntity(DTMensajeError.class).getMensaje());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Usuarios">
    public DTUsuario buscarUsuario(String ci) throws Exception {
        Response response = webTarget.path("usuarios/" + ci).request(JSON_TYPE).get();
        return response.readEntity(DTUsuario.class);
    }

    public void modificarUsuario(DTUsuario usuario) throws Exception {
        Response response = webTarget.path("usuarios").request(JSON_TYPE).put(Entity.entity(usuario, JSON_TYPE));
        comprobarEstado(response);
    }

    public void agregarUsuario(DTUsuario usuario) throws Exception {
        Response response = webTarget.path("usuarios").request(JSON_TYPE).post(Entity.entity(usuario, JSON_TYPE));
        comprobarEstado(response);
    }

    public List<DTUsuario> listarUsuarios(int generacion) throws Exception {
        Response response = webTarget.path("usuarios").queryParam("gen", generacion).request(JSON_TYPE).get();
        return Arrays.asList(response.readEntity(DTUsuario[].class));
    }

    public void agregarAsistencia(DTAsistencia asistencia) throws Exception {
        Response response = webTarget.path("usuarios/asistencias").request(JSON_TYPE).post(Entity.entity(asistencia, JSON_TYPE));
        comprobarEstado(response);
    }

    public DTUsuario login(String ci, String pass) throws Exception {
        Response response = webTarget.path("usuarios/login").queryParam("ci", ci).queryParam("pass", pass).request(JSON_TYPE).get();
        return response.readEntity(DTUsuario.class);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Solicitudes">
    public void confirmarSolicitud(DTSolicitud solicitud) throws Exception {
        Response response = webTarget.path("solicitudes/confirmar").request(JSON_TYPE).put(Entity.entity(solicitud, JSON_TYPE));
        comprobarEstado(response);
    }

    public void eliminarSolicitud(String ci) throws Exception {
        Response response = webTarget.path("solicitudes/" + ci).request(JSON_TYPE).delete();
        comprobarEstado(response);
    }

    public DTSolicitud buscarSolicitud(String ci) throws Exception {
        Response response = webTarget.path("solicitudes/" + ci).request(JSON_TYPE).get();
        comprobarEstado(response);
        return response.readEntity(DTSolicitud.class);
    }

    public List<DTSolicitud> listarSolicitudes(int gen) throws Exception {
        Response response = webTarget.path("solicitudes").queryParam("gen", gen).request(JSON_TYPE).get();
        comprobarEstado(response);
        return Arrays.asList(response.readEntity(DTSolicitud[].class));
    }

    public void verificarSolicitud(String codigo) throws Exception {
        Response response = webTarget.path("solicitudes/verificar").queryParam("codigo", codigo).request(JSON_TYPE).get();
        comprobarEstado(response);
    }

    public void agregarSolicitud(DTSolicitud solicitud) throws Exception {
        Response response = webTarget.path("solicitudes").request(JSON_TYPE).post(Entity.entity(solicitud, JSON_TYPE));
        comprobarEstado(response);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Generaciones">
    public List<DTGeneracion> listarGeneraciones() throws Exception {
        Response response = webTarget.path("generaciones").request(JSON_TYPE).get();
        comprobarEstado(response);
        return Arrays.asList(response.readEntity(DTGeneracion[].class));
    }

    public void agregarGeneracion(DTGeneracion generacion) throws Exception {
        Response response = webTarget.path("generaciones").request(JSON_TYPE).post(Entity.entity(generacion, JSON_TYPE));
        comprobarEstado(response);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Reuniones">
    public DTReunion buscarReunion(String id) throws Exception {
        Response response = webTarget.path("reuniones/" + id).request(JSON_TYPE).get();
        comprobarEstado(response);
        return response.readEntity(DTReunion.class);
    }

    public DTReunion buscarSiguienteReunion(int gen) throws Exception {
        Response response = webTarget.path("reuniones/siguiente").queryParam("gen", gen).request(JSON_TYPE).get();
        comprobarEstado(response);
        return response.readEntity(DTReunion.class);
    }

    public DTReunion buscarUltimaReunionFinalizada(int gen) throws Exception {
        Response response = webTarget.path("reuniones/ultima").queryParam("gen", gen).request(JSON_TYPE).get();
        comprobarEstado(response);
        return response.readEntity(DTReunion.class);
    }

    public DTReunion buscarReunionActual(int gen) throws Exception {
        Response response = webTarget.path("reuniones/actual").queryParam("gen", gen).request(JSON_TYPE).get();
        comprobarEstado(response);
        return response.readEntity(DTReunion.class);
    }

    //TODO: retornar reunión
    public void agregarReunion(DTReunion reunion) throws Exception {
        Response response = webTarget.path("reuniones").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public void modificarReunion(DTReunion reunion) throws Exception {
        Response response = webTarget.path("reuniones").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public void eliminarReunion(int id) throws Exception {
        Response response = webTarget.path("reuniones/" + id).request(JSON_TYPE).delete();
        comprobarEstado(response);
    }

    public List<DTReunion> listarReuniones(int gen) throws Exception {
        Response response = webTarget.path("reuniones").queryParam("gen", gen).request(JSON_TYPE).get();
        comprobarEstado(response);
        return Arrays.asList(response.readEntity(DTReunion[].class));
    }

    public void iniciarReunion(DTReunion reunion) throws Exception {
        Response response = webTarget.path("reuniones/iniciar").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public void finalizarReunion(DTReunion reunion) throws Exception {
        Response response = webTarget.path("reuniones/finalizar").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public void hablitarLista(DTReunion reunion) throws Exception {
        Response response = webTarget.path("reuniones/habilitarLista").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public void deshablitarLista(DTReunion reunion) throws Exception {
        Response response = webTarget.path("reuniones/deshabilitarLista").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    public List<DTAsistencia> listarAsistencias(int reunionId) throws Exception {
        Response response = webTarget.path("reuniones/" + reunionId + "/asistencias").request(JSON_TYPE).get();
        comprobarEstado(response);
        return Arrays.asList(response.readEntity(DTAsistencia[].class));
    }

    public void agregarAsistencia(DTUsuario usuario, DTReunion reunion) throws Exception {
        Response response = webTarget.path("reuniones/asistencias").request(JSON_TYPE).post(Entity.entity(new DTAsistenciaWrapper(usuario, reunion), JSON_TYPE));
        comprobarEstado(response);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Encuestas">
    public void agregarEncuesta(DTReunion reunion) throws Exception {
        Response response = webTarget.path("encuestas").request(JSON_TYPE).post(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(response);
    }

    //TODO: enviar reunión para comprobar estado
    public void modificarEncuesta(DTEncuesta encuesta) throws Exception {
        Response response = webTarget.path("encuestas").request(JSON_TYPE).put(Entity.entity(encuesta, JSON_TYPE));
        comprobarEstado(response);
    }

    public void eliminarEncuesta(int reunionId) throws Exception {
        Response response = webTarget.path("encuestas/" + reunionId).request(JSON_TYPE).delete();
        comprobarEstado(response);
    }

    public void iniciarVotacion(DTReunion reunion) throws Exception {
        Response respuesta = webTarget.path("encuestas/iniciar").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(respuesta);
    }

    public void finalizarVotacion(DTReunion reunion) throws Exception {
        Response respuesta = webTarget.path("encuestas/finalizar").request(JSON_TYPE).put(Entity.entity(reunion, JSON_TYPE));
        comprobarEstado(respuesta);
    }

    public void agregarVoto(DTVoto voto) throws Exception {
        Response respuesta = webTarget.path("encuestas/voto").request(JSON_TYPE).post(Entity.entity(voto, JSON_TYPE));
        comprobarEstado(respuesta);
    }

//</editor-fold>
}
