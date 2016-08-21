package arq.prototipo.logica;

import arq.prototipo.datatypes.*;
import arq.prototipo.excepciones.ArquitecturaRifaExcepcion;
import arq.prototipo.persistencia.*;

class Sistema implements ISistema {

    private static Sistema instancia = null;
    private IPersistencia persistencia = FabricaPersistencia.getPersistencia();

    public static ISistema getInstancia() {
        if (instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }

    private Sistema() {
    }

    @Override
    public DTEstudiante Autenticar(int ci, String contrasena) {
        DTEstudiante resp = null;
        try {
            resp = persistencia.Autenticar(ci, contrasena);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
        return resp;
    }

    @Override
    public void MarcarAsistencia(int ci) {
        try {
            DTEstudiante estudiante = FabricaPersistencia.getPersistencia().BuscarEstudiante(ci);
            DTReunion reunion = FabricaPersistencia.getPersistencia().BuscarReunion(1);
            if (estudiante == null) {
                throw new Exception("El estudiante no puede ser nulo.");
            }
            if (reunion == null) {
                throw new Exception("La reunion no puede ser nula.");
            }
            persistencia.MarcarAsistencia(estudiante, reunion);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
    }

}
