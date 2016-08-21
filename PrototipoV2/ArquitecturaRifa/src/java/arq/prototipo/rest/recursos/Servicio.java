package arq.prototipo.rest.recursos;

import arq.prototipo.datatypes.DTEstudiante;
import arq.prototipo.logica.FabricaLogica;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/servicio")
@Produces(MediaType.APPLICATION_JSON)
public class Servicio {

    @Path("/login")
    @GET
    public DTEstudiante login(@QueryParam("ci") int ci, @QueryParam("pass") String pass) {
        return FabricaLogica.getSistema().Autenticar(ci, pass);
    }
    
    @Path("/asistencia")
    @GET
    public void marcarAsistencia(@QueryParam("ci") int ci){
        FabricaLogica.getSistema().MarcarAsistencia(ci);
    }
}
