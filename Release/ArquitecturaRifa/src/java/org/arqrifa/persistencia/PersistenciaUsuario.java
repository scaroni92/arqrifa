package org.arqrifa.persistencia;

import java.sql.CallableStatement;
import org.arqrifa.datatypes.DTUsuario;
import static org.arqrifa.persistencia.Persistencia.getConexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

class PersistenciaUsuario implements IPersistenciaUsuario {

    private static PersistenciaUsuario instancia = null;

    public static IPersistenciaUsuario getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaUsuario();
        }
        return instancia;
    }

    private PersistenciaUsuario() {
    }

    @Override
    public DTUsuario Autenticar(int ci, String contrasena) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet res = null;

        try {
            con = getConexion();
            stmt = con.prepareCall("CALL Autenticar(?,?)");
            stmt.setInt(1, ci);
            stmt.setString(2, contrasena);
            res = stmt.executeQuery();

            DTUsuario estudiante = null;
            if (res.next()) {
                String nombre = res.getString("nombre");
                String apellido = res.getString("apellido");
                String email = res.getString("email");
                String rol = res.getString("rol");
                int gen = res.getInt("generacion");
                estudiante = new DTUsuario(ci, nombre, apellido, contrasena, email, rol, gen);
            }
            return estudiante;
        } catch (SQLException e) {
            throw new Exception("No se pudo autenticar al usuario - Error de base de datos.");
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
    public DTUsuario BuscarEstudiante(int ci) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet res = null;

        try {
            con = getConexion();
            stmt = con.prepareCall("CALL BuscarEstudiante(?)");
            stmt.setInt(1, ci);
            res = stmt.executeQuery();

            DTUsuario estudiante = null;
            if (res.next()) {
                String nombre = res.getString("nombre");
                String apellido = res.getString("apellido");
                String contrasena = res.getString("contrasena");
                String email = res.getString("email");
                String rol = res.getString("rol");
                int gen = res.getInt("generacion");
                estudiante = new DTUsuario(ci, nombre, apellido, contrasena, email, rol, gen);
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
