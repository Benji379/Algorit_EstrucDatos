package bubblesort;

import ConexionBd.ConexionSQL;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Benji
 */
public class metodosDAO {

    ConexionSQL cc = new ConexionSQL();
    Connection con = cc.conexion();

    public void AgregarMarca(String marca) {
        String SQL = "insert into marca_de_autos (marca) values(?)";
        try {
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, marca);
            pst.executeUpdate();
        } catch (HeadlessException | SQLException e) {
        }
    }

    public static String[] getConsultar(String nameTabla, String nameColumna) {

        ConexionSQL con1 = new ConexionSQL();
        Connection conet;

        Statement st;
        ResultSet rs;

        String sql = "select * from " + nameTabla;

        try {
            conet = con1.conexion();
            st = conet.createStatement();
            rs = st.executeQuery(sql);
            ArrayList<String> autos = new ArrayList<>();

            while (rs.next()) {
                String marca = rs.getString(nameColumna);
                autos.add(marca);
            }
            // Convertir el ArrayList en un array de tipo String
            String[] autosArray = new String[autos.size()];
            for (int i = 0; i < autos.size(); i++) {
                autosArray[i] = autos.get(i);
            }
            return autosArray;

        } catch (SQLException e) {
            // Manejo de excepciones
        }
        return null; // Si ocurre una excepción o no se encuentran datos, retorna null
    }
    
    public static String getID(String nameTablaSQL, String nameProducto, String nameColumnaIngresar, String nameColumnaRetornar) {
        ConexionSQL con1 = new ConexionSQL();
        Connection conet;
        Statement st;
        ResultSet rs;
        String idNombre;
        String ID;
        String sql = "select * from " + nameTablaSQL;

        int existe = 0;
        String idRetornado = null;
        try {
            conet = con1.conexion();
            st = conet.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                idNombre = rs.getString(nameColumnaIngresar);
                ID = rs.getString(nameColumnaRetornar);
                if (idNombre.equals(nameProducto)) {
                    idRetornado = ID;
                    existe = 1;
                }
            }
            if (existe == 0) {
                System.out.println("PRODUCTO NO EXISTE");
            }
        } catch (HeadlessException | SQLException e) {

        }
        String id = idRetornado;
        return id;
    }

    public static void bubbleSort(String[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    // Intercambiar elementos
                    String temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // Si no hubo intercambios en esta iteración, el arreglo ya está ordenado
            if (!swapped) {
                break;
            }
        }
    }

}
