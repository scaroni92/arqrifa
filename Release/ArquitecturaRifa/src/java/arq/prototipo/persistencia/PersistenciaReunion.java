package arq.prototipo.persistencia;

import arq.prototipo.datatypes.*;
import static arq.prototipo.persistencia.Persistencia.getConexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

class PersistenciaReunion implements IPersistenciaReunion {

    
    private static PersistenciaReunion instancia = null;

    public static IPersistenciaReunion getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaReunion();
        }
        return instancia;
    }

    private PersistenciaReunion() {
    }

    @Override
    public DTReunion BuscarReunion(int id) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet res = null;

        try {
            con = Persistencia.getConexion();
            stmt = con.prepareStatement("SELECT * FROM reuniones WHERE id = ?");
            stmt.setInt(1, id);
            res = stmt.executeQuery();

            DTReunion reunion = null;
            if (res.next()) {
                Date fecha = res.getDate("fecha");
                boolean obligatoria = res.getBoolean("obligatoria");
                int generacion = res.getInt("generacion");
                reunion = new DTReunion(id, fecha,generacion, obligatoria);
            }
            return reunion;
        } catch (SQLException e) {
            throw new Exception("No se pudo encontrar la reunion - Error de base de datos.");
        } catch (Exception e) {
            throw e;
        } finally {
            if (res != null) {
                res.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    @Override
    public void MarcarAsistencia(DTEstudiante estudiante, DTReunion reunion) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = getConexion();
            stmt = con.prepareStatement("INSERT INTO asistencias VALUES (?, ?)");
            stmt.setInt(1, reunion.getId());
            stmt.setInt(2, estudiante.getCi());
            stmt.execute();
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 1452:
                    throw new Exception("La reunión o el estudiante ingresado no existe.");
                case 1062:
                    throw new Exception("El estudiante ya marcó su asistencia.");
                default:
                    throw new Exception("No se pudo marcar la asistencia - Error de base de datos.");
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

}
