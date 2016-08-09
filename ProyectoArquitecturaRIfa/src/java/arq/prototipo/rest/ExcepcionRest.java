package arq.prototipo.rest;

public class ExcepcionRest {
    private String mensaje;
    private String tipo = "excepcion";

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ExcepcionRest(Exception ex) {
        this.mensaje = ex.getMessage();
    }
    
    public ExcepcionRest() {
    }
    
    @Override
    public String toString(){
        return "ExcepcionRest[mensaje=" + mensaje + "tipo=" + tipo +"]";
    }
}
