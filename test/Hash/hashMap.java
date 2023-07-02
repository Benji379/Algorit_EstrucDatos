package Hash;

import ConexionBd.ConexionSQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap; // Importar la clase hashMap
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Flypaim Machine
 */
public class hashMap {

    public static void main(String[] args) {

        System.out.println("\n      VENTAS\n");
        System.out.println(" [1] Buscar Venta");
        System.out.println(" [2] Mostrar Ventas");
        Scanner teclado = new Scanner(System.in);
        int op = teclado.nextInt();
        if (op == 1) {
            String dni;
            System.out.println("\n     BUSCAR POR DNI\n");
            System.out.print("Dni: ");
            dni = teclado.next();
            List<HashMap<String, String>> compras = buscarVentasPorDNI(dni); // Utilizar una lista de hashMap para almacenar las compras
            if (!compras.isEmpty()) {
                System.out.println("\nDATOS");
                imprimirEncabezados();
                imprimirDatos(compras);
            } else {
                System.out.println("No se encontró ninguna compra con el DNI especificado.");
            }
        } else {
            if (op == 2) {
                String nombreTabla = "ventas";
                System.out.println("\n               VENTAS\n");
                List<HashMap<String, String>> listaTabla = getTablaBD(nombreTabla); // Utilizar una lista de hashMap para almacenar la tabla

                if (!listaTabla.isEmpty()) {
                    imprimirEncabezados();
                    imprimirDatos(listaTabla);
                } else {
                    System.out.println("No se pudo obtener la tabla.");
                }
            } else {
                System.out.println("Opción incorrecta");
            }
        }
    }

    public static List<HashMap<String, String>> getTablaBD(String nombreTabla) {
        ConexionSQL con1 = new ConexionSQL();
        Connection conet;
        Statement st;
        ResultSet rs;
        List<HashMap<String, String>> listaTabla = new ArrayList<>(); // Utilizar una lista de hashMap para almacenar la tabla

        try {
            conet = con1.conexion();
            st = conet.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "SELECT * FROM " + nombreTabla;
            rs = st.executeQuery(consulta);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                HashMap<String, String> fila = new HashMap<>(); // Utilizar hashMap para representar una fila de datos
                for (int i = 1; i <= columnCount; i++) {
                    String encabezado = metaData.getColumnName(i);
                    String dato = rs.getString(i);
                    fila.put(encabezado, dato); // Agregar la columna y dato al hashMap (fila)
                }
                listaTabla.add(fila); // Agregar el hashMap (fila) a la lista
            }

            rs.close();
            st.close();
            conet.close();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return listaTabla;
    }

    public static List<HashMap<String, String>> buscarVentasPorDNI(String dni) {
        List<HashMap<String, String>> compras = new ArrayList<>(); // Utilizar una lista de hashMap para almacenar las compras
        String nombreTabla = "ventas";
        ConexionSQL con1 = new ConexionSQL();
        Connection conet;
        Statement st;
        ResultSet rs;

        try {
            conet = con1.conexion();
            st = conet.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "SELECT * FROM " + nombreTabla + " WHERE dni = '" + dni + "'";
            rs = st.executeQuery(consulta);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                HashMap<String, String> compra = new HashMap<>(); // Utilizar hashMap para representar una compra
                for (int i = 1; i <= columnCount; i++) {
                    String encabezado = metaData.getColumnName(i);
                    String dato = rs.getString(i);
                    compra.put(encabezado, dato); // Agregar la columna y dato al hashMap (compra)
                }
                compras.add(compra); // Agregar el hashMap (compra) a la lista
            }

            rs.close();
            st.close();
            conet.close();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return compras;
    }

    public static void imprimirEncabezados() {
        System.out.println("id\t\tdni\t\t\ttotal\t\tplaca\t\tfecha");
    }

    public static void imprimirDatos(List<HashMap<String, String>> listaTabla) {
        listaTabla.forEach((fila) -> {
            System.out.println(fila.get("id") + "\t\t" + fila.get("dni") + "\t\t" + fila.get("total") + "\t\t"
                    + fila.get("placa") + "\t\t" + fila.get("fecha"));
        });
    }
}
