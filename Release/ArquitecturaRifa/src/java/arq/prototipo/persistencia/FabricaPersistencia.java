package arq.prototipo.persistencia;

public class FabricaPersistencia {
    
    public static IPersistenciaReunion getPersistenciaReunion() {
        return PersistenciaReunion.getInstancia();
    }
    
     public static IPersistenciaEstudiante getPersistenciaEstudiante() {
        return PersistenciaEstudiante.getInstancia();
    }
}
