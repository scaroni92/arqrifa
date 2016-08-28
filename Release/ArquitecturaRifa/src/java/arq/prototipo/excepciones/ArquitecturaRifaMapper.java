package arq.prototipo.excepciones;

import arq.prototipo.datatypes.MensajeError;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ArquitecturaRifaMapper implements ExceptionMapper<ArquitecturaRifaExcepcion> {

    @Override
    public Response toResponse(ArquitecturaRifaExcepcion exception) {
        MensajeError mensaje = new MensajeError(exception.getMessage());
        return Response.status(Response.Status.OK).entity(mensaje).build();
    }

}
