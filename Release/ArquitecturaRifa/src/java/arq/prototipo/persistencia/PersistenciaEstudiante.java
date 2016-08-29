package arq.prototipo.persistencia;

import arq.prototipo.datatypes.*;
import static arq.prototipo.persistencia.Persistencia.getConexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

class PersistenciaEstudiante implements IPersistenciaEstudiante {

    
    private static PersistenciaEstudiante instancia = null;

    public static IPersistenciaEstudiante getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaEstudiante();
        }
        return instancia;
    }

    private PersistenciaEstudiante() {
    }

    @Override
    public DTEstudiante Autenticar(int ci, String contrasena) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet res = null;

        try {
            con = getConexion();
            stmt = con.prepareStatement("SELECT * FROM estudiantes WHERE ci = ? AND contrasena = ?");
            stmt.setInt(1, ci);
            stmt.setString(2, contrasena);
            res = stmt.executeQuery();

            DTEstudiante estudiante = null;
            if (res.next()) {
                String nombre = res.getString("nombre");
                String apellido = res.getString("apellido");
                estudiante = new DTEstudiante(ci, nombre, apellido, contrasena);
            }
            return estudiante;
        } catch (SQLException e) {
            throw new Exception("No se pudo autenticar al estudiante - Error de base de datos.");
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
    public DTEstudiante BuscarEstudiante(int ci) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet res = null;

        try {
            con = getConexion();
            stmt = con.prepareStatement("SELECT * FROM estudiantes WHERE ci = ?");
            stmt.setInt(1, ci);
            res = stmt.executeQuery();

            DTEstudiante estudiante = null;
            if (res.next()) {
                String nombre = res.getString("nombre");
                String apellido = res.getString("apellido");
                String contrasena = res.getString("contrasena");
                estudiante = new DTEstudiante(ci, nombre, apellido, contrasena);
            }
            return estudiante;
        } catch (SQLException e) {
            throw new Exception("No se pudo encontrar al estudiante - Error de base de datos.");
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

}
