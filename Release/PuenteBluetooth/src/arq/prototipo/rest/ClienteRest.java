/*
 * Clase generada por NetBeans para el acceso a la API REST
 */
package arq.prototipo.rest;

import arq.prototipo.datatypes.DTReunion;
import arq.prototipo.datatypes.DTMensajeError;
import arq.prototipo.datatypes.DTUsuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class ClienteRest {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/ArquitecturaRifa/api";

    public ClienteRest() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("servicio");
    }

    public void marcarAsistencia(String ci) throws Exception {
        Client cliente = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
        WebTarget target = cliente.target("http://localhost:8080/ArquitecturaRifa/api/servicio").path("asistencias/marcar");

        DTUsuario u = new DTUsuario(Integer.parseInt(ci), "juan", "garcia", "1234", "asd@asd.com", "estudiante", 2010);
        DTReunion r = new DTReunion(1, "asd", "asd", "asd", new Date(), true, 2010, "listado");
        MultiPart multipart = new FormDataMultiPart()
                .field("usuario", u, MediaType.APPLICATION_JSON_TYPE)
                .field("reunion", r, MediaType.APPLICATION_JSON_TYPE);

        Response respuesta = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(multipart, multipart.getMediaType()));
        // Código provisional
        if (respuesta.getStatus() == 409) {
            System.out.println(respuesta.readEntity(DTMensajeError.class).getMensaje());
        }
        else {
            System.out.println("Asistencia marcada");
        }
        
    }

    public List<DTReunion> getReunionesIniciadas() throws Exception {
        WebTarget resource = webTarget;
        resource = resource.path("reuniones");
        resource = resource.path("getActivas");
        String resultado = resource.request(MediaType.APPLICATION_JSON).get(String.class);

        if (resultado.isEmpty()) {
            return null;
        } else if (resultado.contains("id")) {
            //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.zzz").create();
            DTReunion[] reunionArray = new Gson().fromJson(resultado, DTReunion[].class);
            return new ArrayList<>(Arrays.asList(reunionArray));
        } else {
            DTMensajeError me = new Gson().fromJson(resultado, DTMensajeError.class);
            throw new Exception(me.getMensaje());
        }
    }

    public void close() {
        client.close();
    }

}
