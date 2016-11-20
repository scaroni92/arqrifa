package org.arqrifa.persistencia;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;

class PersistenciaGeneracion implements IPersistenciaGeneracion {

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    private static PersistenciaGeneracion instancia = null;

    public static PersistenciaGeneracion getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaGeneracion();
        }
        return instancia;
    }

    private PersistenciaGeneracion() {
    }
    //</editor-fold>

    @Override
    public void agregar(DTGeneracion generacion) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL AltaGeneracion(?, ?)");
            stmt.setInt(1, generacion.getId());
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeQuery();
            if (stmt.getInt(2) == -1) {
                throw new Exception("Ya existe una generación para ese año.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("No se pudo dar de alta la generación, erro de base de datos.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
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
    public List<DTGeneracion> listar() throws Exception {
        List<DTGeneracion> generaciones = new ArrayList();
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet res = null;
        try {
            con = Persistencia.getConexion();
            stmt = con.prepareCall("CALL ListarGeneraciones();");
            res = stmt.executeQuery();
            while (res.next()) {
                int generacion = res.getInt("genId");
                generaciones.add(new DTGeneracion(generacion));
            }
        } catch (SQLException e) {
            throw new Exception("No se pudieron listar las generaciones. Error de base de datos.");

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
        return generaciones;
    }

}
