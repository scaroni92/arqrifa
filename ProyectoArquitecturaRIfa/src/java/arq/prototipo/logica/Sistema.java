package arq.prototipo.logica;

import arq.prototipo.datatypes.*;
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
    public DTEstudiante Autenticar(int ci, String contrasena) throws Exception {
        if (String.valueOf(ci).length() < 7) {
            throw new Exception("La cédula ingresada no es válida.");
        }
        if (contrasena.isEmpty()) {
            throw new Exception("Ingrese su contraseña.");
        }
        return persistencia.Autenticar(ci, contrasena);
    }

    @Override
    public void MarcarAsistencia(int ci, int reunionId) throws Exception {
        DTEstudiante estudiante = FabricaPersistencia.getPersistencia().BuscarEstudiante(ci);
        DTReunion reunion = FabricaPersistencia.getPersistencia().BuscarReunion(reunionId);
        if (estudiante == null) {
            throw new Exception("El estudiante no puede ser nulo.");
        }
        if (reunion == null) {
            throw new Exception("La reunion no puede ser nula.");
        }
        persistencia.MarcarAsistencia(estudiante, reunion);
    }

}
