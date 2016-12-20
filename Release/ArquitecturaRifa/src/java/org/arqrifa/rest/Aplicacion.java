package org.arqrifa.rest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.arqrifa.excepciones.ArquitecturaRifaException;
import org.arqrifa.excepciones.ArquitecturaRifaMapper;
import org.arqrifa.rest.recursos.Servicio;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

@ApplicationPath("api")
public class Aplicacion extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();

        classes.add(Servicio.class);
        classes.add(MultiPartFeature.class);
        classes.add(ArquitecturaRifaException.class);
        classes.add(ArquitecturaRifaMapper.class);
        return classes;
    }
}
