package org.arqrifa.rest.recursos;

import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.logica.FabricaLogica;
import org.arqrifa.repository.Repository;
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
    public DTUsuario login(@QueryParam("ci") int ci, @QueryParam("pass") String pass) {
        return FabricaLogica.getLogicaUsuario().Autenticar(ci, pass);
    }
    
    @Path("/asistencia")
    @GET
    public void marcarAsistencia(@QueryParam("ci") int ci){
        DTUsuario usuario = null;
        DTReunion reunion = null;
        FabricaLogica.getControladorReuniones().MarcarAsistencia(usuario, reunion);
    }
    
    @Path("/reuniones/getActivas")
    @GET
    public List<DTReunion> getReunionesActias(){
        //return Repository.reunionesActivas;
        return FabricaLogica.getControladorReuniones().getReunionesActivas();
    }
    
}
