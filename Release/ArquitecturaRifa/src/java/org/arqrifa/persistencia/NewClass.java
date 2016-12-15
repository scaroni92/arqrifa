package org.arqrifa.persistencia;

//@author Ale

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTReunion;

public class NewClass {

    public static void main(String[] args) {
        DTReunion r = new DTReunion();
        r.setId(2);
        
        DTEncuesta e = new DTEncuesta();
        e.setTitulo("tituloEncuesta");
        e.setDuracion(10);
        
        DTPropuesta p = new DTPropuesta();
        p.setPregunta("?=??");
        List<String> respuestas = new ArrayList();
        respuestas.add("res1");
        respuestas.add("res2");
        respuestas.add("res3");    
        p.setRespuestas(respuestas);
        
        List<DTPropuesta> propuestas = new ArrayList();
        propuestas.add(p);
        
        e.setPropuestas(propuestas);
        r.setEncuesta(e);
        try {
            PersistenciaEncuesta.getInstancia().altaEncuesta(r);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }
}
