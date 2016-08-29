package arq.prototipo.logica;

public class FabricaLogica {
    
    public static  ILogicaEstudiante getLogicaEstudiante() {
        return LogicaEstudiante.getInstancia();
    }
    
    public static  ILogicaReunion getLogicaReunion() {
        return LogicaReunion.getInstancia();
    }
}
