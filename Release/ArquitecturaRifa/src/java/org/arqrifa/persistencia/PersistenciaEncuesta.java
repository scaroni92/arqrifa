package org.arqrifa.persistencia;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTEncuesta;
import org.arqrifa.datatypes.DTPropuesta;
import org.arqrifa.datatypes.DTRespuesta;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTVoto;

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
    public void alta(DTReunion reunion) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            con.setAutoCommit(false);
            stmt = con.prepareCall("CALL AltaEncuesta(?, ?, ?, ?)");
            stmt.setInt(1, reunion.getId());
            stmt.setString(2, reunion.getEncuesta().getTitulo());
            stmt.setInt(3, reunion.getEncuesta().getDuracion());
            stmt.registerOutParameter(4, Types.INTEGER);
            if (stmt.executeUpdate() > 0) {
                for (DTPropuesta p : reunion.getEncuesta().getPropuestas()) {
                    this.altaPropuesta(stmt.getInt(4), p, con);
                }
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw new Exception(e.getErrorCode() == 1062 ? "La reunión ya posee una encuesta." : "No se pudo dar de alta la encuesta, error de base de datos");
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
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
                for (DTRespuesta r : propuesta.getRespuestas()) {
                    this.altaRespuesta(stmt.getInt(3), r, con);
                }
            }
        } catch (Exception e) {
            throw new Exception("No se pudo dar de alta la propuesta, error de base de datos.");
        }
    }

    private void altaRespuesta(int propuestaId, DTRespuesta respuesta, Connection con) throws Exception {
        try (CallableStatement stmt = con.prepareCall("CALL AltaRespuesta(?, ?)")) {
            stmt.setInt(1, propuestaId);
            stmt.setString(2, respuesta.getRespuesta());
            stmt.execute();
        } catch (Exception e) {
            throw new Exception("No se pudo dar de alta la respuesta, error de base de datos.");
        }
    }

    public DTEncuesta buscar(int reunionId) throws Exception {
        DTEncuesta encuesta = null;
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet res = null;

        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL BuscarEncuestaDeReunion(?)");
            stmt.setInt(1, reunionId);
            res = stmt.executeQuery();
            if (res.next()) {
                encuesta = new DTEncuesta(res.getInt("id"), res.getString("titulo"), res.getInt("duracion"), res.getBoolean("habilitada"), this.listarPropuestas(res.getInt("id"), con));
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo buscar la encuesta de la reunión, error de base de datos.");
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
        return encuesta;
    }

    private List<DTPropuesta> listarPropuestas(int encuestaId, Connection con) throws Exception {
        List<DTPropuesta> propuestas = new ArrayList();
        CallableStatement stmt = null;
        ResultSet res = null;

        try {
            stmt = con.prepareCall("CALL ListarPropuestasDeEncuesta(?)");
            stmt.setInt(1, encuestaId);
            res = stmt.executeQuery();
            while (res.next()) {
                propuestas.add(new DTPropuesta(res.getInt("id"), res.getString("pregunta"), this.listarRespuestas(res.getInt("id"), con)));
            }
        } catch (Exception e) {
            throw new Exception("No se pudo listar las propuestas de la encuesta, error de base de datos.");
        } finally {
            if (res != null) {
                res.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return propuestas;
    }

    private List<DTRespuesta> listarRespuestas(int propuestaId, Connection con) throws Exception {
        List<DTRespuesta> respuestas = new ArrayList();
        CallableStatement stmt = null;
        ResultSet res = null;

        try {
            stmt = con.prepareCall("CALL ListarRespuestasDePropuesta(?)");
            stmt.setInt(1, propuestaId);
            res = stmt.executeQuery();
            while (res.next()) {
                respuestas.add(new DTRespuesta(res.getInt("id"), res.getString("respuesta")));
            }

        } catch (Exception e) {
            throw new Exception("No se pudieron listar las respuestas de la propuesta, error de base de datos.");
        } finally {
            if (res != null) {
                res.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return respuestas;
    }

    @Override
    public void habilitarVotacion(DTEncuesta encuesta) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL habilitarVotacion(?)");
            stmt.setInt(1, encuesta.getId());
            if (stmt.executeUpdate() == 0) {
                throw new Exception("La encuesta que desea iniciar no existe");
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo habilitar la votación, error de base de datos.");
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
    public void altaVoto(DTVoto voto) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = Persistencia.getConexion();
            con.setAutoCommit(false);
            stmt = con.prepareCall("CALL AltaVoto(?, ?)");
            stmt.setInt(1, voto.getUsuario().getCi());
            for (DTRespuesta r : voto.getRespuestasEscogidas()) {
                stmt.setInt(2, r.getId());
                if (stmt.executeUpdate() == 0) {
                    throw new Exception("No se pudo agregar el voto, error de base de datos.");
                }
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw new Exception(e.getErrorCode() == 1062 ? "El estudiante ya voto previamente en la encuesta." : "No se pudo agregar el voto, error de base de datos.");
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
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
