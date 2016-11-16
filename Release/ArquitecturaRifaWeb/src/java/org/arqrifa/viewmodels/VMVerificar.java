package org.arqrifa.viewmodels;

 
public class VMVerificar extends ViewModel{

    private boolean verificada;

    public boolean isVerificada() {
        return verificada;
    }

    public void setVerificada(boolean verificada) {
        this.verificada = verificada;
    }

    public VMVerificar(boolean verificada, String mensaje) {
        super(mensaje);
        this.verificada = verificada;
    }

    public VMVerificar() {
        this(false, "");
    }
    
    
}
