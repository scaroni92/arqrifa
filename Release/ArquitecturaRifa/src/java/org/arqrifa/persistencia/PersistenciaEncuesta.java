package org.arqrifa.persistencia;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTReunion;

class PersistenciaEncuesta implements IPersistenciaEncuesta {

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    private static PersistenciaEncuesta instancia = null;
    
    public static PersistenciaEncuesta getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaEncuesta();
        }
        return instancia;
    }
    
    private PersistenciaEncuesta() {
    }
    //</editor-fold>

    @Override
    public void altaEncuesta(DTReunion reunion) throws Exception {
        DTEncuesta encuesta = reunion.getEncuesta();
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            con.setAutoCommit(false);
            stmt = con.prepareCall("CALL AltaEncuesta(?, ?, ?, ?)");
            stmt.setInt(1, reunion.getId());
            stmt.setString(2, encuesta.getTitulo());
            stmt.setInt(3, encuesta.getDuracion());
            stmt.registerOutParameter(4, Types.INTEGER);
            if (stmt.executeUpdate() > 0) {
                for (DTPropuesta p : encuesta.getPropuestas()) {
                    this.altaPropuesta(stmt.getInt(4), p, con);
                }
            }
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            if (e.getErrorCode() == 1062) {
                throw new Exception("La reunión ya posee una encuesta.");
            }
        } catch (Exception e) {
            con.rollback();
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
    
    private void altaPropuesta(int encuestaId, DTPropuesta propuesta, Connection con) throws Exception {
        try (CallableStatement stmt = con.prepareCall("CALL AltaPropuesta(?, ?, ?)")) {
            stmt.setInt(1, encuestaId);
            stmt.setString(2, propuesta.getPregunta());
            stmt.registerOutParameter(3, Types.INTEGER);
            if (stmt.executeUpdate() > 0) {
                for (String r : propuesta.getRespuestas()) {
                    this.altaRespuesta(stmt.getInt(3), r, con);
                }
            }
        } catch (Exception e) {
            throw new Exception("No se pudo dar de alta la propuesta, error de base de datos.");
        }
    }
    
    private void altaRespuesta(int propuestaId, String respuesta, Connection con) throws Exception {
        try (CallableStatement stmt = con.prepareCall("CALL AltaRespuesta(?, ?)")){
            stmt.setInt(1, propuestaId);
            stmt.setString(2, respuesta);
            stmt.execute();
        } catch (Exception e) {
            throw new Exception("No se pudo dar de alta la respuesta, error de base de datos.");
        }
    }
    
}
