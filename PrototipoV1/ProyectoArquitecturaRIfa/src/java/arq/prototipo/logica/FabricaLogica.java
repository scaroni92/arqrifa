package arq.prototipo.logica;

public class FabricaLogica {
    public static  ISistema getSistema() {
        return Sistema.getInstancia();
    }
}
