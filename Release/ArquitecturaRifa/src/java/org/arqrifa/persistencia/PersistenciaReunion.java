package org.arqrifa.persistencia;

import java.sql.CallableStatement;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.datatypes.DTReunion;
import static org.arqrifa.persistencia.Persistencia.getConexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.arqrifa.datatypes.DTEncuesta;

class PersistenciaReunion implements IPersistenciaReunion {

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    private static PersistenciaReunion instancia = null;

    public static IPersistenciaReunion getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaReunion();
        }
        return instancia;
    }

    private PersistenciaReunion() {
    }

    //</editor-fold>
    
    @Override
    public void agregar(DTReunion reunion) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            con.setAutoCommit(false);
            stmt = con.prepareCall("CALL AltaReunion(?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, reunion.getGeneracion());
            stmt.setString(2, reunion.getTitulo());
            stmt.setString(3, reunion.getDescripcion());
            stmt.setString(4, new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(reunion.getFecha()));
            stmt.setInt(5, reunion.getDuracion());
            stmt.setBoolean(6, reunion.isObligatoria());
            stmt.setString(7, reunion.getLugar());
            stmt.registerOutParameter(8, Types.INTEGER);
            stmt.execute();
            if (stmt.getInt(8) == -1) {
                throw new Exception("Ya hay agendada una reunión para el día ingresado.");
            }
            if (stmt.getInt(8) > 0) {
                for (String tema : reunion.getTemas()) {
                    this.agregarTema(stmt.getInt(8), tema, con);
                }
            }
            con.commit();

        } catch (SQLException e) {
            con.rollback();
            throw new Exception("No se pudo agendar la reunión, error de base dadtos.");
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

    @Override
    public void iniciar(DTReunion reunion) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL IniciarReunion(?)");
            stmt.setInt(1, reunion.getId());
            if (stmt.executeUpdate() == 0) {
                throw new Exception("Reunión no encontrada");
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo iniciar la reunión, error de base datos.");
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
    public void finalizar(DTReunion reunion) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL finalizarReunion(?, ?)");
            stmt.setInt(1, reunion.getId());
            stmt.setString(2, reunion.getObservaciones());
            if (stmt.executeUpdate() == 0) {
                throw new Exception("La reunión que desea finalizar no ha sido iniciada aún.");
            }
            for (String resolucion : reunion.getResoluciones()) {
                this.agregarResolucion(reunion.getId(), resolucion, con);
            }

        } catch (SQLException e) {
            throw new Exception("No se pudo finalizar la reunión, error de base de datos.");
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
    public void marcarAsistencia(DTUsuario estudiante, DTReunion reunion) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = getConexion();
            stmt = con.prepareCall("CALL MarcarAsistencia(?, ?);");
            stmt.setInt(1, reunion.getId());
            stmt.setInt(2, estudiante.getCi());
            stmt.execute();

        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 1062:
                    throw new Exception("El estudiante marcó su asistencia previamente.");
                case 1452:
                    throw new Exception("El estudiante o la reunión ingresada no existe.");
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
    public DTReunion buscar(int id) throws Exception {
        DTReunion reunion = null;
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet res = null;

        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL BuscarReunion(?)");
            stmt.setInt(1, id);
            res = stmt.executeQuery();
            
            if (res.next()) {
                int generacion = res.getInt("id_gen");
                String titulo = res.getString("titulo");
                String descripcion = res.getString("descripcion");
                // Se utiliza getTimestamp porque getDate() no devuelve la hora.
                Date fecha = new Date(res.getTimestamp("fecha").getTime());
                boolean obligatoria = res.getBoolean("obligatoria");
                String lugar = res.getString("lugar");
                String observaciones = res.getString("observaciones");
                String estado = res.getString("estado");
                List<String> temas = this.listarTemas(id, con);
                List<String> resoluciones = this.listarResoluciones(id, con);
                DTEncuesta encuesta = PersistenciaEncuesta.getInstancia().buscar(id);

                reunion = new DTReunion(id, generacion, titulo, descripcion, fecha, generacion, obligatoria, lugar, observaciones, estado, temas, resoluciones, encuesta);
            }

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
        return reunion;
    }

    @Override
    public List<DTReunion> listarIniciadas() throws Exception {
        List<DTReunion> reuniones = new ArrayList();
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet res = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL ListarReunionesIniciadas()");
            res = stmt.executeQuery();

            int id, generacion;
            String titulo, descripcion, lugar, observaciones, estado;
            Date fecha;
            boolean obligatoria;
            List<String> temas, resoluciones;
            while (res.next()) {
                id = res.getInt("id");
                generacion = res.getInt("id_gen");
                titulo = res.getString("titulo");
                descripcion = res.getString("descripcion");
                fecha = new Date(res.getTimestamp("fecha").getTime());
                obligatoria = res.getBoolean("obligatoria");
                lugar = res.getString("lugar");
                observaciones = res.getString("observaciones");
                estado = res.getString("estado");
                temas = this.listarTemas(id, con);
                resoluciones = this.listarResoluciones(id, con);

                reuniones.add(new DTReunion(id, generacion, titulo, descripcion, fecha, generacion, obligatoria, lugar, observaciones, estado, temas, resoluciones));
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo listar las reuniones iniciadas, error de base de datos.");
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
        return reuniones;
    }

    private List<String> listarTemas(int reunionId, Connection con) throws Exception {
        List<String> temas = new ArrayList();
        CallableStatement stmt = null;
        ResultSet res = null;
        try {
            stmt = con.prepareCall("CALL ListarTemasDeReunion(?)");
            stmt.setInt(1, reunionId);
            res = stmt.executeQuery();
            while (res.next()) {
                temas.add(res.getString("tema"));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
            //throw new Exception("Error al cargar los temas de la reunión");
        } finally {
            if (res != null) {
                res.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return temas;
    }

    private List<String> listarResoluciones(int reunionId, Connection con) throws Exception {
        List<String> resoluciones = new ArrayList();
        CallableStatement stmt = null;
        ResultSet res = null;
        try {
            stmt = con.prepareCall("CALL ListarResolucionesDeReunion(?)");
            stmt.setInt(1, reunionId);
            res = stmt.executeQuery();
            while (res.next()) {
                resoluciones.add(res.getString("resolucion"));
            }
        } catch (Exception e) {
            throw new Exception("Error al cargar las resoluciones de la reunión ID: " + reunionId);
        } finally {
            if (res != null) {
                res.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return resoluciones;
    }

    private void agregarTema(int reunionId, String tema, Connection con) throws Exception {
        CallableStatement stmt = null;
        try {
            stmt = con.prepareCall("CALL AltaTema(?,?)");
            stmt.setInt(1, reunionId);
            stmt.setString(2, tema);
            if (stmt.executeUpdate() == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("No se pudo dar de alta el tema, error de base de datos");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    private void agregarResolucion(int reunionId, String resolucion, Connection con) throws Exception {
        CallableStatement stmt = null;
        try {
            stmt = con.prepareCall("CALL AltaResolucion(?,?)");
            stmt.setInt(1, reunionId);
            stmt.setString(2, resolucion);
            if (stmt.executeUpdate() == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("No se pudo dar de alta la resolucion, error de base de datos");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
