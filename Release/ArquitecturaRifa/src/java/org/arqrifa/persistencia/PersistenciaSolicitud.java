package org.arqrifa.persistencia;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import org.arqrifa.datatypes.DTSolicitud;

class PersistenciaSolicitud implements IPersistenciaSolicitud {

    private static PersistenciaSolicitud instancia = null;

    public static PersistenciaSolicitud getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaSolicitud();
        }
        return instancia;
    }

    private PersistenciaSolicitud() {
    }

    @Override
    public void alta(DTSolicitud solicitud) throws Exception {
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
        }
        catch(SQLException e){
            throw new Exception("No se pudo rechazar la solicitud, error en base de datos.");
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

}
