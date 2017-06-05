package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTUsuario;


public class VMListadoUsuarios extends ViewModel{
    private List<DTUsuario> usuarios;
    private Paginacion paginacion;

    public VMListadoUsuarios() {
        this(new ArrayList(), ""); 
    }    

    public VMListadoUsuarios(List<DTUsuario> usuarios, String mensaje) {
        super(mensaje);
        this.usuarios = usuarios;
        this.paginacion = new Paginacion(usuarios, 2);
    }
    
    

    
    
    public List<DTUsuario> getUsuarios() {
        return usuarios;
    }

    public Paginacion getPaginacion() {
        return paginacion;
    }

    public void setPaginacion(Paginacion paginacion) {
        this.paginacion = paginacion;
    }

 
    public void setUsuarios(List<DTUsuario> usuarios) {
        this.usuarios = usuarios;
        this.paginacion.setElementos(usuarios);
    }
    public void setPg(String pagina) throws Exception {
        this.paginacion.setPaginaSolicitada(pagina);
    }
}
