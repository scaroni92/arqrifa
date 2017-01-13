package org.arqrifa.rest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.arqrifa.exceptions.ArquitecturaRifaException;
import org.arqrifa.exceptions.ArquitecturaRifaMapper;
import org.arqrifa.rest.recursos.Servicio;

@ApplicationPath("api")
public class Aplicacion extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();

        classes.add(Servicio.class);
        classes.add(ArquitecturaRifaException.class);
        classes.add(ArquitecturaRifaMapper.class);
        return classes;
    }
}
