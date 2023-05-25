package ConexionBd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Benji
 */
public class ConexionSQL {

    Connection conectar = null;

    public Connection conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conectar = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/bdconsesionaria", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conectar;
    }

}
