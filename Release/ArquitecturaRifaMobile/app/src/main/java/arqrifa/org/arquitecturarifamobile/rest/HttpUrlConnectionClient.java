package arqrifa.org.arquitecturarifamobile.rest;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import arqrifa.org.arquitecturarifamobile.datatypes.DTMensajeError;
import arqrifa.org.arquitecturarifamobile.datatypes.DTReunion;
import arqrifa.org.arquitecturarifamobile.datatypes.DTUsuario;
import arqrifa.org.arquitecturarifamobile.datatypes.DTVotacion;


public class HttpUrlConnectionClient {

    private static final String BASE_URI = "http://192.168.0.104:8080/ArquitecturaRifa/api/";
    private URL url;

    public DTUsuario login(String ci, String pass) throws Exception {
        String urlString = BASE_URI + "usuarios/login?ci=" + ci + "&pass=" + pass;
        url = new URL(urlString);
        return getRequest(DTUsuario.class);
    }

    public DTReunion getReunionActual(int gen) throws Exception {
        String urlString = BASE_URI+"reuniones/actual?gen=" + gen;
        url = new URL(urlString);
        return getRequest(DTReunion.class);
    }

    public DTReunion getProximaReunion(int gen) throws Exception {
        String urlString = BASE_URI+"reuniones/siguiente?gen=" + gen;
        url = new URL(urlString);
        return getRequest(DTReunion.class);
    }

    public DTReunion getUltimaReunion(int gen) throws Exception {
        String urlString = BASE_URI+"reuniones/ultima?gen=" + gen;
        url = new URL(urlString);
        return getRequest(DTReunion.class);
    }

    public List<DTReunion> getReuniones(int gen) throws Exception {
        String urlString = BASE_URI+"reuniones?gen=" + gen;
        url = new URL(urlString);
        return Arrays.asList(getRequest(DTReunion[].class));
    }

    public DTVotacion getVotacion(int ci, int reunionId) throws Exception{
        String urlString = BASE_URI + "encuestas/votacion?ci="+ ci +"&reunionId=" + reunionId;
        url = new URL(urlString);
        return getRequest(DTVotacion.class);
    }

    public void postVotacion(DTVotacion votacion) throws Exception {
        String urlString = BASE_URI + "encuestas/votacion";
        url = new URL(urlString);
        String entity = new Gson().toJson(votacion);
        postRequest(entity);
    }

    private <T> T getRequest(Class<T> responseType) throws Exception{
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.connect();

        return readEntity(con, responseType);
    }

    private <T> T readEntity(HttpURLConnection con, Class<T> responseType) throws Exception {
        Object response;

        BufferedReader reader;
        try {
            if (con.getResponseCode() < HttpURLConnection.HTTP_CONFLICT){
                reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                response = new Gson().fromJson(reader.readLine(), responseType);
            } else {
                reader = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
                response = new Gson().fromJson(reader.readLine(),DTMensajeError.class);
            }
            reader.close();
        } catch (Exception ex) {
            throw ex;
        } finally {
            con.disconnect();
        }

        if (response instanceof DTMensajeError) {
            throw new Exception(((DTMensajeError)response).getMensaje());
        }

        return (T)response;
    }

    private void postRequest(String entity) throws Exception{
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");
        con.connect();

        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(entity);
        wr.flush();

        // Se comprueba si hubo errores
        readEntity(con, Object.class);
    }

}
