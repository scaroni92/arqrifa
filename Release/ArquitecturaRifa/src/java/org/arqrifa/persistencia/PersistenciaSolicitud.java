package org.arqrifa.persistencia;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;

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
    public void agregar(DTSolicitud s) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL AltaSolicitud(?, ?, ?, ?, ?, ?, ?, ?, ?);");
            stmt.setInt(1, s.getUsuario().getCi());
            stmt.setInt(2, s.getUsuario().getGeneracion());
            stmt.setDate(3, new java.sql.Date(s.getFecha().getTime()));
            stmt.setString(4, s.getUsuario().getNombre());
            stmt.setString(5, s.getUsuario().getApellido());
            stmt.setString(6, s.getUsuario().getContrasena());
            stmt.setString(7, s.getUsuario().getEmail());
            stmt.setInt(8, s.getCodigo());
            stmt.registerOutParameter(9, Types.INTEGER);
            stmt.execute();
            int retorno = stmt.getInt(9);
            if (retorno == -1 || retorno == -3) {
                throw new Exception("La cédula ingresada está en uso");
            } else if (retorno == -2 || retorno == -4) {
                throw new Exception("El correo ingresado está en uso");
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo dar de alta la solicitud, error de base de datos.");
        } catch (Exception e) {
            throw e;
        } finally {
            Persistencia.cerrarConexiones(null, stmt, con);
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
            if (stmt.executeUpdate() == 0) {
                throw new Exception("Solicitud no encontrada.");
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo verificar la solicitud, intente más tarde");
        } catch (Exception e) {
            throw e;
        } finally {
            Persistencia.cerrarConexiones(null, stmt, con);
        }
    }

    @Override
    public void confirmar(DTSolicitud s) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL ConfirmarSolicitud(?, ?, ?, ?, ?, ?);");
            stmt.setInt(1, s.getUsuario().getCi());
            stmt.setInt(2, s.getUsuario().getGeneracion());
            stmt.setString(3, s.getUsuario().getNombre());
            stmt.setString(4, s.getUsuario().getApellido());
            stmt.setString(5, s.getUsuario().getContrasena());
            stmt.setString(6, s.getUsuario().getEmail());
            stmt.execute();
        } catch (SQLException e) {
            throw new Exception("No se pudo confirmar la solicitud, error de base de datos.");
        } catch (Exception e) {
            throw e;
        } finally {
            Persistencia.cerrarConexiones(null, stmt, con);
        }
    }

    @Override
    public void rechazar(DTSolicitud solicitud) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL EliminarSolicitud (?)");
            stmt.setInt(1, solicitud.getUsuario().getCi());
            if (stmt.executeUpdate() == 0) {
                throw new Exception("La solicitud que desea rechazar no existe");
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo rechazar la solicitud, error en base de datos.");
        } catch (Exception e) {
            throw e;
        } finally {
            Persistencia.cerrarConexiones(null, stmt, con);
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
                DTUsuario usuario = new DTUsuario(ci, res.getString("nombre"), res.getString("apellido"), res.getString("contrasena"), res.getString("email"), "", res.getInt("id_gen"), 0);
                solicitud = new DTSolicitud(res.getInt("codigo"), res.getDate("fecha"), res.getBoolean("verificada"), usuario);
            }

        } catch (SQLException e) {
            throw new Exception("No se pudo buscar la solicitud, error de base de datos.");
        } catch (Exception e) {
            throw e;
        } finally {
            Persistencia.cerrarConexiones(res, stmt, con);
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
            stmt = con.prepareCall("CALL ListarSolicitudesPorGeneracion(?);");
            stmt.setInt(1, generacion);
            res = stmt.executeQuery();

            while (res.next()) {
                DTUsuario usuario = new DTUsuario(res.getInt("ci"), res.getString("nombre"), res.getString("apellido"), res.getString("contrasena"), res.getString("email"), "", generacion, 0);
                solicitudes.add(new DTSolicitud(res.getInt("codigo"), res.getDate("fecha"), res.getBoolean("verificada"), usuario));
            }
        } catch (SQLException e) {
            throw new Exception("No se pudieron listar las solicitudes, error de base de datos.");

        } catch (Exception e) {
            throw e;
        } finally {
            Persistencia.cerrarConexiones(res, stmt, con);
        }
        return solicitudes;
    }

}
