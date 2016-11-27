package arq.prototipo.rest;

//<editor-fold defaultstate="collapsed" desc="imports">
import arq.prototipo.datatypes.DTEstado;
import arq.prototipo.datatypes.DTReunion;
import arq.prototipo.datatypes.DTMensajeError;
import arq.prototipo.datatypes.DTUsuario;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
//</editor-fold>

public class ClienteRest {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/ArquitecturaRifa/api";

    public ClienteRest() {
        client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
        webTarget = client.target(BASE_URI).path("servicio");
    }

    public void marcarAsistencia(String ci) throws Exception {
        webTarget = webTarget.path("asistencias/marcar");

        // Código de prueba
        DTUsuario u = new DTUsuario(Integer.parseInt(ci), "juan", "garcia", "1234", "asd@asd.com", "estudiante", 2010);
        DTReunion r = new DTReunion(1, "asd", "asd", "asd", new Date(), true, 2010, DTEstado.LISTADO);

        MultiPart multipart = new FormDataMultiPart()
                .field("usuario", u, MediaType.APPLICATION_JSON_TYPE)
                .field("reunion", r, MediaType.APPLICATION_JSON_TYPE);
        Response respuesta = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(multipart, multipart.getMediaType()));

        if (respuesta.getStatus() == 409) {
            throw new Exception(respuesta.readEntity(DTMensajeError.class).getMensaje());
        } else {
            System.out.println("Asistencia marcada");
        }

    }

    public List<DTReunion> getReunionesActivas() throws Exception {
        webTarget = webTarget.path("reunion/iniciadas");
        Response resultado = webTarget.request(MediaType.APPLICATION_JSON).get();

        switch (resultado.getStatus()) {
            case 204:
                return null;
            case 409:
                throw new Exception(resultado.readEntity(DTMensajeError.class).getMensaje());
            default:
                return Arrays.asList(resultado.readEntity(DTReunion[].class));
        }
    }

    public void close() {
        client.close();
    }

}
