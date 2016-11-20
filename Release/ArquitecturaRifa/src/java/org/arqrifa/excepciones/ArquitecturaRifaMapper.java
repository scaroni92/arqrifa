package org.arqrifa.excepciones;

import org.arqrifa.datatypes.DTMensajeError;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ArquitecturaRifaMapper implements ExceptionMapper<ArquitecturaRifaExcepcion> {

    @Override
    public Response toResponse(ArquitecturaRifaExcepcion ex) {
        DTMensajeError mensaje = new DTMensajeError(ex.getMessage());
        return Response.status(Response.Status.CONFLICT).entity(mensaje).build();
    }

}
