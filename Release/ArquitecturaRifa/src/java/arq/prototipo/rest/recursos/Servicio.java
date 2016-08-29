package arq.prototipo.rest.recursos;

import arq.prototipo.datatypes.DTEstudiante;
import arq.prototipo.datatypes.DTReunion;
import arq.prototipo.logica.FabricaLogica;
import arq.prototipo.repository.Repository;
import java.util.List;
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
        return FabricaLogica.getLogicaEstudiante().Autenticar(ci, pass);
    }
    
    @Path("/asistencia")
    @GET
    public void marcarAsistencia(@QueryParam("ci") int ci){
        FabricaLogica.getLogicaReunion().MarcarAsistencia(ci);
    }
    
    @Path("/reuniones/getActivas")
    @GET
    public List<DTReunion> getReunionesActias(){
        return Repository.reunionesActivas;
    }
    
}
