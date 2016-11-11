package org.arqrifa.persistencia;

import java.sql.CallableStatement;
import org.arqrifa.datatypes.DTUsuario;
import static org.arqrifa.persistencia.Persistencia.getConexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
    public DTUsuario autenticar(int ci, String contrasena) throws Exception {
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
    public DTUsuario buscarEstudiante(int ci) throws Exception {
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
    public void altaUsuario(DTUsuario usuario) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL AltaUsuario(?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, usuario.getCi());
            stmt.setInt(2, usuario.getGeneracion());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getApellido());
            stmt.setString(5, usuario.getContrasena());
            stmt.setString(6, usuario.getEmail());
            stmt.setString(7, usuario.getRol());
            stmt.registerOutParameter(8, Types.INTEGER);
            stmt.execute();
            int retorno = stmt.getInt(8);
            switch (retorno) {
                case -1:
                    throw new Exception("La cédula ingresada está en uso.");
                case -2:
                    throw new Exception("El correo ingresado está en uso.");
                case -3:
                    throw new Exception("La generación ingresada no existe.");
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo dar de alta al usuario. Error de base de datos.");
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
    public List<DTUsuario> listarEstudiantes(int generacion) throws Exception {
        List<DTUsuario> estudiantes = new ArrayList();
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet res = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL ListarEstudiantes(?)");
            stmt.setInt(1, generacion);
            res = stmt.executeQuery();
            
            DTUsuario estudiante;
            while (res.next()) {
                estudiante = new DTUsuario(res.getInt("ci"),
                        res.getString("nombre"),
                        res.getString("apellido"),
                        res.getString("contrasena"),
                        res.getString("email"),
                        res.getString("rol"),
                        generacion);
                estudiantes.add(estudiante);
            }

        } 
        catch(SQLException e){
            throw new Exception("No se pudieron listar los estudiantes, error de base de datos.");
        }
        catch (Exception e) {
            throw e;
        }
        finally {
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
        return estudiantes;
    }

}
