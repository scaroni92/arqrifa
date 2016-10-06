package org.arqrifa.persistencia;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;

class PersistenciaGeneracion implements IPersistenciaGeneracion {

    private static PersistenciaGeneracion instancia = null;

    public static PersistenciaGeneracion getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaGeneracion();
        }
        return instancia;
    }

    private PersistenciaGeneracion() {
    }

    @Override
    public List<DTSolicitud> listarSolicitudes(int generacion) throws Exception {
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
                solicitudes.add(new DTSolicitud(ci, generacion, fecha, nombre, apellido, contrasena, email));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
