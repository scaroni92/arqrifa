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
            if (con != null) {
                con.rollback();
            }
            throw new Exception("No se pudo agendar la reunión, error de base dadtos.");
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            Persistencia.cerrarConexiones(null, stmt, con);
        }
    }

    @Override
    public void modificar(DTReunion reunion) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            con.setAutoCommit(false);
            stmt = con.prepareCall("CALL ModificarReunion(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, reunion.getId());
            stmt.setInt(2, reunion.getGeneracion());
            stmt.setString(3, reunion.getTitulo());
            stmt.setString(4, reunion.getDescripcion());
            stmt.setString(5, new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(reunion.getFecha()));
            stmt.setInt(6, reunion.getDuracion());
            stmt.setBoolean(7, reunion.isObligatoria());
            stmt.setString(8, reunion.getLugar());
            stmt.registerOutParameter(9, Types.INTEGER);
            stmt.execute();
            if (stmt.getInt(9) == -1) {
                throw new Exception("Ya hay agendada una reunión para la fecha ingresada.");
            }
            this.eliminarTemas(reunion.getId(), con);

            for (String tema : reunion.getTemas()) {
                this.agregarTema(reunion.getId(), tema, con);
            }

            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw new Exception("No se pudo agendar la reunión, error de base dadtos.");
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            Persistencia.cerrarConexiones(null, stmt, con);
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
    public void agregarAsistencia(DTUsuario estudiante, DTReunion reunion) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = getConexion();
            stmt = con.prepareCall("CALL AltaAsistencia(?, ?);");
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
                reunion = cargarDatosReunion(res, con);
            }

        } catch (SQLException e) {
            throw new Exception("No se pudo encontrar la reunion - Error de base de datos.");
        } catch (Exception e) {
            throw e;
        } finally {
            Persistencia.cerrarConexiones(res, stmt, con);
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

            DTReunion reunion;
            while (res.next()) {
                reuniones.add(cargarDatosReunion(res, con));
            }

        } catch (SQLException e) {
            throw new Exception("No se pudo listar las reuniones iniciadas, error de base de datos.");
        } catch (Exception e) {
            throw e;
        } finally {
            Persistencia.cerrarConexiones(res, stmt, con);
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
            throw new Exception("Error al cargar los temas de la reunión ID: " + reunionId);
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

    private List<DTUsuario> listarParticipantes(int reunionId, Connection con) throws Exception {
        List<DTUsuario> participantes = new ArrayList();
        CallableStatement stmt = null;
        ResultSet res = null;
        try {
            stmt = con.prepareCall("CALL ListarAsistenciasDeReunion(?)");
            stmt.setInt(1, reunionId);
            res = stmt.executeQuery();
            while (res.next()) {
                participantes.add(PersistenciaUsuario.getInstancia().buscarEstudiante(res.getInt("ci")));
            }
        } catch (Exception e) {
            throw new Exception("Error al cargar las asistencias de la reunión ID: " + reunionId);
        } finally {
            if (res != null) {
                res.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return participantes;
    }

    @Override
    public DTReunion buscarUltimaReunionFinalizada(int id_gen) throws Exception {
        DTReunion reunion = null;
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet res = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL BuscarUltimaReunionPorGeneracion(?)");
            stmt.setInt(1, id_gen);
            res = stmt.executeQuery();
            if (res.next()) {
                reunion = cargarDatosReunion(res, con);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            Persistencia.cerrarConexiones(res, stmt, con);
        }
        return reunion;
    }

    @Override
    public List<DTReunion> listarPorGeneracion(int id_gen) throws Exception {
        List<DTReunion> reuniones = new ArrayList();
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet res = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL ListarReunionesPorGeneracion(?)");
            stmt.setInt(1, id_gen);
            res = stmt.executeQuery();
            while (res.next()) {
                reuniones.add(cargarDatosReunion(res, con));
            }

        } catch (SQLException e) {
            throw new Exception("No se pudo listar las reuniones, error de base de datos.");
        } catch (Exception e) {
            throw e;
        } finally {
            Persistencia.cerrarConexiones(res, stmt, con);
        }
        return reuniones;
    }

    @Override
    public DTReunion buscarProximaReunion(int id_gen) throws Exception {
        DTReunion reunion = null;
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet res = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL BuscarProximaReunionPorGeneracion(?)");
            stmt.setInt(1, id_gen);
            res = stmt.executeQuery();
            if (res.next()) {
                reunion = cargarDatosReunion(res, con);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            Persistencia.cerrarConexiones(res, stmt, con);
        }
        return reunion;
    }

    @Override
    public void eliminar(DTReunion reunion) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL BajaReunion(?, ?)");
            stmt.setInt(1, reunion.getId());
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.execute();
            if (stmt.getInt(2) == -1) {
                throw new Exception("No se puede dar de baja una reunión en progreso.");
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo dar de baja la reunión, error de base de datos.");
        } catch (Exception e) {
            throw e;
        } finally {
            Persistencia.cerrarConexiones(null, stmt, con);
        }
    }

    private void agregarTema(int reunionId, String tema, Connection con) throws Exception {
        try (CallableStatement stmt = con.prepareCall("CALL AltaTema(?,?)")) {
            stmt.setInt(1, reunionId);
            stmt.setString(2, tema);
            if (stmt.executeUpdate() == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("No se pudo dar de alta el tema, error de base de datos");
        }
    }

    private void agregarResolucion(int reunionId, String resolucion, Connection con) throws Exception {
        try (CallableStatement stmt = con.prepareCall("CALL AltaResolucion(?,?)")) {
            stmt.setInt(1, reunionId);
            stmt.setString(2, resolucion);
            if (stmt.executeUpdate() == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("No se pudo dar de alta la resolucion, error de base de datos");
        }
    }

    private void eliminarTemas(int id_reunion, Connection con) throws Exception {
        try (CallableStatement stmt = con.prepareCall("CALL BajaTemas(?)")) {
            stmt.setInt(1, id_reunion);
            if (stmt.executeUpdate() == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("No se pudo dar de baja los temas de la reunión, error de base de datos");
        }
    }

    private DTReunion cargarDatosReunion(ResultSet res, Connection con) throws Exception {
        return new DTReunion(res.getInt("id"),
                res.getInt("id_gen"),
                res.getString("titulo"),
                res.getString("descripcion"),
                new Date(res.getTimestamp("fecha").getTime()),
                res.getInt("duracion"),
                res.getBoolean("obligatoria"),
                res.getString("lugar"),
                res.getString("observaciones"),
                res.getString("estado"),
                PersistenciaEncuesta.getInstancia().buscar(res.getInt("id")),
                this.listarTemas(res.getInt("id"), con),
                this.listarResoluciones(res.getInt("id"), con),
                this.listarParticipantes(res.getInt("id"), con));
    }

    // mover método a Persistencia
 
}
