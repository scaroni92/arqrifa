package org.arqrifa.persistencia;

import java.sql.CallableStatement;
import org.arqrifa.datatypes.DTUsuario;
import static org.arqrifa.persistencia.Persistencia.getConexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.arqrifa.datatypes.DTSolicitud;

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

    @Override
    public void AltaSolicitud(DTSolicitud solicitud) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL AltaSolicitud(?, ?, ?, ?, ?, ?, ?, ?);");
            stmt.setInt(1, solicitud.getCi());
            stmt.setInt(2, solicitud.getGeneracion());
            stmt.setDate(3, new java.sql.Date(solicitud.getFecha().getTime()));
            stmt.setString(4, solicitud.getNombre());
            stmt.setString(5, solicitud.getApellido());
            stmt.setString(6, solicitud.getContrasena());
            stmt.setString(7, solicitud.getEmail());
            stmt.registerOutParameter(8, Types.INTEGER);
            stmt.executeQuery();
            int retorno = stmt.getInt(8);
            if (retorno == -1 || retorno == -3) {
                throw new Exception("La cédula ingresada está en uso.");
            }
            if (retorno == -2 || retorno == -4) {
                throw new Exception("El correo ingresado está en uso.");
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo dar de alta la solicitud - Error de base de datos.");
        } catch (Exception e) {
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                stmt.close();
            }
        }
    }

}
