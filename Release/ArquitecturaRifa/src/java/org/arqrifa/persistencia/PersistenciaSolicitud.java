package org.arqrifa.persistencia;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;

class PersistenciaSolicitud implements IPersistenciaSolicitud {

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    private static PersistenciaSolicitud instancia = null;

    public static PersistenciaSolicitud getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaSolicitud();
        }
        return instancia;
    }

    private PersistenciaSolicitud() {
    }
    //</editor-fold>

    @Override
    public void agregar(DTSolicitud solicitud) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL AltaSolicitud(?, ?, ?, ?, ?, ?, ?, ?, ?);");
            stmt.setInt(1, solicitud.getCi());
            stmt.setInt(2, solicitud.getGeneracion());
            stmt.setDate(3, new java.sql.Date(solicitud.getFecha().getTime()));
            stmt.setString(4, solicitud.getNombre());
            stmt.setString(5, solicitud.getApellido());
            stmt.setString(6, solicitud.getContrasena());
            stmt.setString(7, solicitud.getEmail());
            stmt.setInt(8, solicitud.getCodigo());
            stmt.registerOutParameter(9, Types.INTEGER);
            stmt.execute();
            int retorno = stmt.getInt(9);
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
                con.close();
            }
        }
    }

    @Override
    public void verificar(int codigo) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL VerificarSolicitud(?);");
            stmt.setInt(1, codigo);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new Exception("Solicitud no encontrada.");
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo verificar la solicitud, intente más tarde.");
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
    public void confirmar(DTSolicitud solicitud) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL ConfirmarSolicitud(?, ?, ?, ?, ?, ?, ?);");
            stmt.setInt(1, solicitud.getCi());
            stmt.setInt(2, solicitud.getGeneracion());
            stmt.setString(3, solicitud.getNombre());
            stmt.setString(4, solicitud.getApellido());
            stmt.setString(5, solicitud.getContrasena());
            stmt.setString(6, solicitud.getEmail());
            stmt.registerOutParameter(7, Types.INTEGER);
            stmt.execute();
            if (stmt.getInt(7) == -1) {
                throw new Exception("No se pudo confirmar de alta la solicitud - Error de base de datos");
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo confirmar la solicitud - Error de base de datos.");
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
    public void rechazar(DTSolicitud solicitud) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL EliminarSolicitud (?, ?)");
            stmt.setInt(1, solicitud.getCi());
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.execute();
            if (stmt.getInt(2) == -1) {
                throw new Exception("La solicitud que desea rechazar no existe.");
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo rechazar la solicitud, error en base de datos.");
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
    public DTSolicitud buscar(int ci) throws Exception {
        DTSolicitud solicitud = null;
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet res = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL BuscarSolicitud(?)");
            stmt.setInt(1, ci);
            res = stmt.executeQuery();
            if (res.next()) {
                int generacion = res.getInt("generacion");
                Date fecha = res.getDate("fecha");
                String nombre = res.getString("nombre");
                String apellido = res.getString("apellido");
                String contrasena = res.getString("contrasena");
                String email = res.getString("email");
                int codigo = res.getInt("codigo");
                boolean verficiada = res.getBoolean("verificada");

                solicitud = new DTSolicitud(ci, generacion, fecha, nombre, apellido, contrasena, email, codigo, verficiada);
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo buscar la solicitud, error de base de datos.");
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
        return solicitud;
    }

    @Override
    public List<DTSolicitud> listar(int generacion) throws Exception {
        List<DTSolicitud> solicitudes = new ArrayList();
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet res = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL ListarSolicitudesDeGeneracion(?);");
            stmt.setInt(1, generacion);
            res = stmt.executeQuery();
            while (res.next()) {
                int ci = res.getInt("ci");
                int gen = res.getInt("generacion");
                Date fecha = res.getDate("fecha");
                String nombre = res.getString("nombre");
                String apellido = res.getString("apellido");
                String contrasena = res.getString("contrasena");
                String email = res.getString("email");
                int codigo = res.getInt("codigo");
                boolean verificada = res.getBoolean("verificada");
                solicitudes.add(new DTSolicitud(ci, generacion, fecha, nombre, apellido, contrasena, email, codigo, verificada));
            }
        } catch (SQLException e) {
            throw new Exception("No se pudieron listar las solicitudes. Error de base de datos.");

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
        return solicitudes;
    }

}
