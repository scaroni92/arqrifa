package arq.prototipo.persistencia;

import arq.prototipo.datatypes.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

class Persistencia implements IPersistencia {

    private static final String URL_CONEXION = "jdbc:mysql://localhost:3306/prototipodb";
    private static final String USUARIO_BASE_DATOS = "root";
    private static final String CONTRASENA_BASE_DATOS = "root";

    private static Persistencia instancia = null;

    public static IPersistencia getInstancia() {
        if (instancia == null) {
            instancia = new Persistencia();
        }
        return instancia;
    }

    private Persistencia() {
    }

    private Connection getConexion() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(URL_CONEXION, USUARIO_BASE_DATOS, CONTRASENA_BASE_DATOS);
        } catch (ClassNotFoundException e) {
            throw new Exception("Error al instanciar el Driver JDBC.");
        } catch (SQLException e) {
            throw new Exception("Error de conexión con la base de datos.");
        }
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

    @Override
    public DTReunion BuscarReunion(int id) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet res = null;

        try {
            con = getConexion();
            stmt = con.prepareStatement("SELECT * FROM reuniones WHERE id = ?");
            stmt.setInt(1, id);
            res = stmt.executeQuery();

            DTReunion reunion = null;
            if (res.next()) {
                Date fecha = res.getDate("fecha");
                boolean obligatoria = res.getBoolean("obligatoria");
                reunion = new DTReunion(id, fecha, obligatoria);
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
}
