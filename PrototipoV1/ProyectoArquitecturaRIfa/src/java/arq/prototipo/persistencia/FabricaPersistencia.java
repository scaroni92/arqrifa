package arq.prototipo.persistencia;

public class FabricaPersistencia {
    public static IPersistencia getPersistencia() {
        return Persistencia.getInstancia();
    }
}
