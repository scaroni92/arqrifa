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
            stmt = con.prepareCall("CALL AltaReunion(?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, reunion.getTitulo());
            stmt.setString(2, reunion.getDescripcion());
            stmt.setString(3, new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(reunion.getFecha()));
            stmt.setInt(4, reunion.getGeneracion());
            stmt.setBoolean(5, reunion.isObligatoria());
            stmt.setString(6, reunion.getLugar());
            stmt.registerOutParameter(7, Types.INTEGER);
            stmt.execute();
            if (stmt.getInt(7) == -1) {
                throw new Exception("Ya hay agendada una reunión para el día ingresado.");
            }
        } catch (SQLException e) {
            throw new Exception("No se pudo agendar la reunión, error de base dadtos.");
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
            stmt = con.prepareCall("CALL finalizarReunion(?, ?, ?)");
            stmt.setInt(1, reunion.getId());
            stmt.setString(2, reunion.getResoluciones());
            stmt.registerOutParameter(3, Types.INTEGER);
            int filasAfectadas = stmt.executeUpdate();
            if (stmt.getInt(3) == -1) {
                throw new Exception("La reunión ingresada no existe.");
            }
            if (filasAfectadas == 0) {
                throw new Exception("No se puede finalizar una reunión que no está iniciada.");
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
            stmt = con.prepareCall("CALL MarcarAsistencia(?, ?, ?);");
            stmt.setInt(1, reunion.getId());
            stmt.setInt(2, estudiante.getCi());
            stmt.registerOutParameter(3, Types.INTEGER);
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

    @Override
    public DTReunion buscar(int id) throws Exception {
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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date fecha = sdf.parse(sdf.format(res.getTimestamp("fecha")));
                boolean obligatoria = res.getBoolean("obligatoria");
                int generacion = res.getInt("generacion");
                String estado = res.getString("estado");
                String lugar = res.getString("lugar");
                reunion = new DTReunion(id, titulo, descripcion, resoluciones, fecha, obligatoria, generacion, estado, lugar);
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
    public List<DTReunion> listarIniciadas() throws Exception {
        List<DTReunion> reuniones = new ArrayList();
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet res = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL ListarReunionesIniciadas()");
            res = stmt.executeQuery();

            String titulo, descripcion, resoluciones, estado, lugar;
            int generacion, id;
            Date fecha;
            boolean obligatoria;
            
            while (res.next()) {
                id = res.getInt("id");
                titulo = res.getString("titulo");
                descripcion = res.getString("descripcion");
                resoluciones = res.getString("resoluciones");
                // Se utiliza getTimestamp porque getDate() no devuelve la hora.
                fecha = new Date(res.getTimestamp("fecha").getTime());
                obligatoria = res.getBoolean("obligatoria");
                generacion = res.getInt("generacion");
                estado = res.getString("estado");
                lugar = res.getString("lugar");
                
                reuniones.add(new DTReunion(id, titulo, descripcion, resoluciones, fecha, obligatoria, generacion, estado, lugar));
            }
        }
        catch(SQLException e) {
            throw new Exception("No se pudo listar las reuniones iniciadas, error de base de datos.");
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
        return reuniones;
    }

}
