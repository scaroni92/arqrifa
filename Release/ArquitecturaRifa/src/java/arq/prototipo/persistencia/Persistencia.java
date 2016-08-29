package arq.prototipo.persistencia;

import arq.prototipo.datatypes.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

class Persistencia {

    private static final String URL_CONEXION = "jdbc:mysql://localhost:3306/prototipodb";
    private static final String USUARIO_BASE_DATOS = "root";
    private static final String CONTRASENA_BASE_DATOS = "root";

    protected static Connection getConexion() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(URL_CONEXION, USUARIO_BASE_DATOS, CONTRASENA_BASE_DATOS);
        } catch (ClassNotFoundException e) {
            throw new Exception("Error al instanciar el Driver JDBC.");
        } catch (SQLException e) {
            throw new Exception("Error de conexión con la base de datos.");
        }
    } 
}