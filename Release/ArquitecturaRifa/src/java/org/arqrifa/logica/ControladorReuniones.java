package org.arqrifa.logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.persistencia.FabricaPersistencia;
import org.arqrifa.excepciones.ArquitecturaRifaExcepcion;

class ControladorReuniones implements IControladorReuniones {

    private List<Reunion> reunionesActivas = new ArrayList();
    private static ControladorReuniones instancia = null;
    
    public static IControladorReuniones getInstancia() {
        if (instancia == null) {
            instancia = new ControladorReuniones();
        }
        return instancia;
    }
    
    private ControladorReuniones() {
        Reunion r = new Reunion(1, "titulo1", "desc1", "res", new Date(), true, 2010, "listado");
        r.setEstado("listado");
        reunionesActivas.add(r);
        r = new Reunion(2, "titulo2", "desc2", "res", new Date(), true, 2012, "pendiente");
        reunionesActivas.add(r);
    }
    
    @Override
    public void MarcarAsistencia(DTUsuario usuario, DTReunion reunion) {
        
        try {
            if (!usuario.getRol().equals("estudiante")) {
                throw new Exception("El usuario que desea marcar asistencia no es estudiante.");
            }
            for (Reunion reunionActiva : reunionesActivas) {
                if (reunionActiva.getId() == reunion.getId()) {
                    if (reunionActiva.getEstado().equals("listado")) {
                        reunionActiva.marcarAsistencia(usuario);
                    }else {
                        throw new Exception("La lista no ha sido habilitada aún.");
                    }
                }
            }
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
    }

    @Override
    public List<DTReunion> getReunionesActivas() {
        List<DTReunion> resp = new ArrayList();
        for (Reunion reunion : reunionesActivas) {
            resp.add(reunion.getDataType());
        }
        return resp;
    }
    
}
