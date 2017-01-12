
package org.arqrifa.viewmodels;

import org.arqrifa.datatypes.DTUsuario;


 //@author Ale
 
public class VMUsuario  extends ViewModel{
    private DTUsuario usuario;
    private int inasistencias; 
    
    public VMUsuario() {
    }

    public VMUsuario(DTUsuario usuario, int inasistencias, String mensaje) {
        super(mensaje);
        this.usuario = usuario;
        this.inasistencias = inasistencias;
    }

    public int getInasistencias() {
        return inasistencias;
    }

    public void setInasistencias(int inasistencias) {
        this.inasistencias = inasistencias;
    }

    

    public DTUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(DTUsuario usuario) {
        this.usuario = usuario;
    }
    
    
}
