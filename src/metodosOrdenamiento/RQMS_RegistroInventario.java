package metodosOrdenamiento;

import MetodosDao.metodosDAO;
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
 * @author Flypaim Machine
 */
public class RQMS_RegistroInventario extends metodosDAO {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("\n          ALMACEN\n");
        System.out.println(" [1] Menú de los productos");
        System.out.println(" [2] Mostrar productos");
        int op = teclado.nextInt();

        if (op == 1) {
            System.out.println("\n     MENÚ DE LOS PRODUCTOS \n");
            System.out.println(" [1] Ingresar productos ");
            System.out.println(" [2] Ingreso de stock");
            int opc = teclado.nextInt();

            if (opc == 1) {
                System.out.println("\n INGRESAR PRODUCTO\n");
                System.out.print(" Nombre  : ");
                String nombreProducto = teclado.next();
                System.out.print(" Stock   : ");
                int stock = teclado.nextInt();
                System.out.print(" Costo   : S/.");
                double costo = teclado.nextDouble();
                AgregarProducto(nombreProducto, stock, costo);
            } else {
                if (opc == 2) {
                    System.out.println("\n         MODIFICAR STOCK\n");
                    System.out.print(" Nombre del Producto: ");
                    String nameProducto = teclado.next();
                    System.out.print(" Stock : ");
                    int stock = teclado.nextInt();
                    System.out.print(" Precio: ");
                    double precio = teclado.nextDouble();
                    ModificarProducto(nameProducto, precio, stock);
                } else {
                    System.out.println("OPCION INVALIDA");
                }
            }
        } else {
            if (op == 2) {
                String[] arr = getConsultar("productos", "nomProducto");
                System.out.println("\n          STOCK DE PRODUCTO\n");
                System.out.println("\n         NOMBRE                 STOCK\n");
                quickSort(arr, 0, arr.length - 1);
                for (String element : arr) {
                    consultar("productos", element);
                }
            } else {
                System.out.println("OPCIONES INVALIDA");
            }
        }
    }

    public static void consultar(String nameTablaSQL, String nameProducto) {
        ConexionSQL con1 = new ConexionSQL();
        Connection conet;
        Statement st;
        ResultSet rs;
        String idNombre;
        String cantidad;
        String sql = "select * from " + nameTablaSQL;

        int existe = 0;

        try {
            conet = con1.conexion();
            st = conet.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                idNombre = rs.getString("nomProducto");
                cantidad = rs.getString("cantidad");
                if (idNombre.equals(nameProducto)) {
                    existe = 1;
                    if (idNombre.length() > 8) {
                        System.out.println("\t" + idNombre + "\t\t" + cantidad);
                    } else {
                        System.out.println("\t" + idNombre + "\t\t\t" + cantidad);
                    }
                }
            }
            if (existe == 0) {
                System.out.println("PRODUCTO NO EXISTE");
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void AgregarProducto(String nombreProducto, int stock, double costo) {
        ConexionSQL cc = new ConexionSQL();
        Connection con = cc.conexion();
        String SQL = "insert into productos (nomProducto,cantidad,costo) values(?,?,?)";
        
        try {
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, nombreProducto);
            pst.setInt(2, stock);
            pst.setString(3, String.valueOf(costo));
            pst.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void ModificarProducto(String nomProducto, double costo, int stock) {
        ConexionSQL con1 = new ConexionSQL();
        int idc = Integer.parseInt(getID("productos", nomProducto, "nomProducto", "id"));
        Connection conet;
        Statement st;
        ResultSet rs;
        String nom = nomProducto;//MODIFICAR NO OLVAR 
        String precio = String.valueOf(costo);
        String cant = String.valueOf(stock);

        try {
            String sql = "Update productos set id='" + idc + "', nomProducto='" + nom + "', cantidad='" + cant + "', costo='" + precio + "' where id=" + idc;
            conet = con1.conexion();
            st = conet.createStatement();
            st.executeUpdate(sql);
            System.out.println("Datos de servicio Modificados");
        } catch (HeadlessException | SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // Método principal que llama a quickSort para ordenar el array
    public static void quickSort(String[] arr, int low, int high) {
        if (low < high) {
            // Calcula el índice del pivote y realiza la partición del array
            int pivotIndex = partition(arr, low, high);
            // Llama recursivamente a quickSort para ordenar las sub-arrays antes y después del pivote
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    // Método que realiza la partición del array y devuelve el índice del pivote
    public static int partition(String[] arr, int low, int high) {
        // Selecciona el último elemento como pivote
        String pivot = arr[high];
        // Inicializa el índice del elemento más pequeño
        int i = low - 1;
        // Itera sobre el sub-array desde low hasta high-1
        for (int j = low; j < high; j++) {
            // Si el elemento actual es menor o igual que el pivote, lo mueve al sub-array de los elementos más pequeños
            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }
        // Coloca el pivote en la posición correcta
        swap(arr, i + 1, high);

        // Retorna el índice del pivote
        return i + 1;
    }

    // Método auxiliar para intercambiar dos elementos en el array
    public static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
