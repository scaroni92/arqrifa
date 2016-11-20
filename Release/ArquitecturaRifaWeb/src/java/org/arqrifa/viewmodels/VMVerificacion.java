package org.arqrifa.viewmodels;

 
public class VMVerificacion extends ViewModel{

    private boolean verificada;

    public boolean isVerificada() {
        return verificada;
    }

    public void setVerificada(boolean verificada) {
        this.verificada = verificada;
    }

    public VMVerificacion(boolean verificada, String mensaje) {
        super(mensaje);
        this.verificada = verificada;
    }

    public VMVerificacion() {
        this(false, "");
    }
    
    
}
