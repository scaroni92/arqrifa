package org.arqrifa.persistencia;

import java.sql.CallableStatement;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;
import static org.arqrifa.persistencia.Persistencia.getConexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
        CallableStatement stmt = null;
        ResultSet res = null;

        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL BuscarReunion(?)");
            stmt.setInt(1, id);
            res = stmt.executeQuery();

            DTReunion reunion = null;
            if (res.next()) {
                String titulo = res.getString("titulo");
                String descripcion = res.getString("descripcion");
                String resoluciones = res.getString("resoluciones");
                Date fecha = res.getDate("fecha");
                boolean obligatoria = res.getBoolean("obligatoria");
                int generacion = res.getInt("generacion");
                reunion = new DTReunion(id, titulo, descripcion, resoluciones, fecha, obligatoria, generacion, "");
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
    public void MarcarAsistencia(DTUsuario estudiante, DTReunion reunion) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = getConexion();
            stmt = con.prepareCall("INSERT INTO asistencias VALUES (?, ?)");
            stmt.setInt(1, reunion.getId());
            stmt.setInt(2, estudiante.getCi());
            stmt.registerOutParameter(9, Types.INTEGER);
            stmt.execute();
            
            switch (stmt.getInt(3)) {
                case -1:
                    throw new Exception("El estudiante ya marcó su asistencia.");
                case -2:
                    throw new Exception("La reunión ingresada no existe.");
                case -3:
                    throw new Exception("El estudiante ingresado no existe.");
                default:
                    break;
            }
        } catch (SQLException e) {            
            throw new Exception("No se pudo marcar la asistencia - Error de base de datos.");          
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
