package arq.prototipo.rest;

import arq.prototipo.logica.FabricaLogica;
import com.google.gson.Gson;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("servicio")
public class Servicio {

    @Path("autenticar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String autenticar(@QueryParam("user") int user, @QueryParam("pass") String pass) {
        try {
            return new Gson().toJson(FabricaLogica.getSistema().Autenticar(user, pass));
        } catch (Exception ex) {
            return new Gson().toJson(new ExcepcionRest(ex));
        }
    }

    @Path("marcarAsistencia")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String marcarAsistencia(@QueryParam("ci") int ci, @QueryParam("id") int id) {
        try {
            FabricaLogica.getSistema().MarcarAsistencia(ci, id);
            return new Gson().toJson(true);
        } catch (Exception ex) {
            return new Gson().toJson(new ExcepcionRest(ex));
        }
    }

}
