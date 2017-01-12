package org.arqrifa.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTUsuario;

 
public class VMListadoUsuarios extends ViewModel{
    private List<DTUsuario> usuarios;

    public VMListadoUsuarios() {
        this(new ArrayList(), ""); 
    }
    
    

    public VMListadoUsuarios(List<DTUsuario> usuarios, String mensaje) {
        super(mensaje);
        this.usuarios = usuarios;
    }

    
    
    public List<DTUsuario> getUsuarios() {
        return usuarios;
    }

 
    public void setUsuarios(List<DTUsuario> usuarios) {
        this.usuarios = usuarios;
    }
    
}
