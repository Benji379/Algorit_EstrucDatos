package metodosOrdenamiento;

import ConexionBd.ConexionSQL;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Benji
 */
public class InsertSort extends metodosDAO {

    public static void consultar(String nameTablaSQL, String idCliente) {

        ConexionSQL con1 = new ConexionSQL();
        Connection conet;
        Statement st;
        ResultSet rs;
        String IDClient;
        String nombreCliente;
        String puntos;

        String sql = "select * from " + nameTablaSQL;
        int existe = 0;

        try {
            conet = con1.conexion();
            st = conet.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                IDClient = rs.getString("id");
                nombreCliente = rs.getString("nombre");
                puntos = rs.getString("puntos");
                if (IDClient.equals(idCliente)) {
                    existe = 1;
                    if (IDClient.length() > 8) {
                        System.out.println("\t" + puntos + "\t    " + nombreCliente);
                    } else {
                        System.out.println("\t" + puntos + "\t    " + nombreCliente);
                    }
                }
            }
            if (existe == 0) {
                System.out.println("CLIENTE NO EXISTE");
            }
        } catch (HeadlessException | SQLException e) {

        }
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("\n       VENTAS\n");
        System.out.println(" [1] Cantidad de puntos por cliente");
        System.out.println(" [2] Nueva venta");
        int op = teclado.nextInt();

        if (op == 1) {
            System.out.println(" \n CANTIDAD DE PUNTOS POR CLIENTE\n");
            
            String[] arr = getConsultar("clientes", "puntos");
            String[] id = getConsultar("clientes", "id");
            int puntos[] = new int[arr.length];
            int ID[] = new int[arr.length];

            // ACA PASO TODOS LOS DATOS DEL ARRAY DE STRING DE PUNTOS A UN ARRAY DE ENTEROS LLAMADO "puntos[]"
            for (int i = 0; i < arr.length; i++) {
                puntos[i] = Integer.parseInt(arr[i]);
                ID[i] = Integer.parseInt(id[i]);
            }
            
            insertSort(puntos, ID);
            System.out.println("\n      PUNTOS\t    CLIENTE   \n");
            for (int i = 0; i < puntos.length; i++) {
//                System.out.println(" " + (i + 1) + "- " + puntos[i] + "\t" + ID[i]);
                System.out.print(" " + (i + 1) + ".- ");
                consultar("clientes", String.valueOf(ID[i]));
            }
        } else {
            if (op == 2) {
                System.out.print(" Nombre del cliente: ");
                String name = teclado.next();

                boolean salir = false;
                String productos[] = getConsultar("productos", "nomProducto");

                bubbleSort(productos);
                double acum = 0;
                int puntos;
                while (salir == false) {
                    System.out.println("\n         Productos\n");
                    for (int i = 0; i < productos.length; i++) {
                        System.out.println(" [" + (i + 1) + "] " + productos[i]);
                    }
                    System.out.print(" NUMERO: ");
                    int num = teclado.nextInt();
                    String producto = productos[num - 1];
                    System.out.println("El producto elegido es: " + producto);
                    double precio = Double.parseDouble(getID("productos", producto, "nomProducto", "costo"));
                    System.out.println("Costo: S/." + precio);
                    acum += precio;
                    System.out.println(" ¿Continuar? Si o No");
                    String resp = teclado.next();

                    if (resp.equalsIgnoreCase("no")) {
                        salir = true;
                    }
                }

                System.out.println(" TOTAL A PAGAR: S/." + acum);
                // CADA S/.20 ACUMULADO SE GANA 1 PUNTO
                puntos = (int) Math.round(acum / 20);
                //AGREGO AL CLIENTE Y SUS PUNTOS A LA BD
                AgregarCliente(name, puntos);
//                System.out.println("Tiene " + puntos + " puntos");
            } else {
                System.out.println("OPCION INVALIDA");
            }
        }

    }

    public static void AgregarCliente(String nameCliente, int puntos) {
        ConexionSQL cc = new ConexionSQL();
        Connection con = cc.conexion();
        String SQL = "insert into clientes (nombre,puntos) values(?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, nameCliente);
            pst.setInt(2, puntos);
            pst.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    //METODO DE ORDENAMIENTO INSERSORT
    public static void insertSort(int[] arr, int id[]) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int dd = id[i];
            int j = i - 1;
            while (j >= 0 && arr[j] < key) {
                arr[j + 1] = arr[j];
                id[j + 1] = id[j];
                j = j - 1;
            }
            arr[j + 1] = key;
            //GUARDO EL ID YA QUE ES LO ÚNICO QUE PUEDE DIFERENCIAR A 2 CLIENTES CON LA MISMA CANTIDAD DE PUNTOS
            id[j + 1] = dd;
        }
    }
}
