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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Flypaim Machine
 */
public class MergeSort extends metodosDAO {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("\n        CLIENTE\n");
        System.out.println(" [1] Registrar nuevo cliente");
        System.out.println(" [2] Mostrar Clientes");
        int op = teclado.nextInt();

        String dni, nombre, apellido, placa, marca;

        if (op == 1) {
            System.out.println("\n       RESGITRAR CLIENTE\n");
            System.out.print(" - DNI     : ");
            dni = teclado.next();
            System.out.print(" - Nombre  : ");
            nombre = teclado.next();
            System.out.print(" - Apellido: ");
            apellido = teclado.next();
            System.out.print(" - Placa   : ");
            placa = teclado.next();
            String marcas[] = getConsultar("marca_de_autos", "marca");
            bubbleSort(marcas);
            System.out.println("\n        MARCAS\n");
            for (int i = 0; i < marcas.length; i++) {
                System.out.println(" [" + (i + 1) + "] " + marcas[i]);
            }
            System.out.print(" Marca: ");
            int num = teclado.nextInt();
            marca = marcas[num - 1];
            System.out.println("El producto elegido es: " + marca);
            AgregarCliente(dni, nombre, apellido, placa, marca, "0");
        } else {
            if (op == 2) {
                System.out.println("\n         CLIENTES\n");
                System.out.println("Ordenas por: ");
                System.out.println(" [1] Dni");
                System.out.println(" [2] Nombres y Apellidos");
                System.out.println(" [3] Placa");
                System.out.println(" [4] Marca");
                int opc = teclado.nextInt();

                switch (opc) {
                    case 1://DNI
                        System.out.println("\n\tDNI\n");
                        String DniBD[] = getConsultar("clientes", "dni");
                        mergeSort(DniBD);
                        for (int i = 0; i < DniBD.length; i++) {
                            System.out.println("[" + (i + 1) + "]   " + DniBD[i]);
                        }
                        System.out.println("Seleccione el # de fila correspondiente al DNI");
                        System.out.print(" # Fila: ");
                        int nFila = teclado.nextInt();
                        mostrarDatos("clientes", "dni", DniBD[nFila - 1]);
                        break;
                    case 2: //NOMBRES Y APELLIDOS
                        System.out.println("\n\tNOMBRES Y APELLIDOS\n");
                        String nameCompleto[] = getConsultar("clientes", "nombre", "apellido");
                        mergeSort(nameCompleto);
                        for (int i = 0; i < nameCompleto.length; i++) {
                            System.out.println("[" + (i + 1) + "]   " + nameCompleto[i]);
                        }
                        System.out.println("Seleccione el # de fila correspondiente al NOMBRE COMPLETO");
                        System.out.print(" # Fila: ");
                        int nFilas = teclado.nextInt();
                        String nombreApellido = nameCompleto[nFilas - 1];
                        String NOMBRE = getSepararNombre(nombreApellido);
                        String APELLIDO = getSepararApellido(nombreApellido);
                        mostrarDatos("clientes", "nombre", "apellido", NOMBRE, APELLIDO);
                        break;
                    case 3://PLACAS
                        System.out.println("\n\tPLACAS\n");
                        String listPlacas[] = getConsultar("clientes", "placa");
                        mergeSort(listPlacas);
                        ElegirPlacaMostrarUsuario(listPlacas);
                        break;
                    case 4://MARCAS
                        System.out.println("\n\tMARCAS\n");
                        String marcas[] = getConsultar("marca_de_autos", "marca");
                        mergeSort(marcas);
                        for (int i = 0; i < marcas.length; i++) {
                            System.out.println("[" + (i + 1) + "]   " + marcas[i]);
                        }
                        System.out.println("Ingrese la fila respectiva a la marca");
                        System.out.print(" # de Fila: ");
                        int nfila = teclado.nextInt();
                        String marcaElegida = marcas[nfila - 1];
                        System.out.println("\n" + marcaElegida + "\n");
                        String placas[] = obtenerPlacasPorMarca("clientes", "marca", marcaElegida, "placa");
                        mergeSort(placas);
                        ElegirPlacaMostrarUsuario(placas);
                        break;
                    default:
                        System.out.println("ERROR: Opcion invalida");
                }
            }
        }
    }

    public static void AgregarCliente(String dni, String nameCliente, String apellido, String placa, String marca, String puntos) {
        ConexionSQL cc = new ConexionSQL();
        Connection con = cc.conexion();
        String SQL = "insert into clientes (dni,nombre,apellido,placa,marca,puntos) values(?,?,?,?,?,?)";

        try {
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, dni);
            pst.setString(2, nameCliente);
            pst.setString(3, apellido);
            pst.setString(4, placa);
            pst.setString(5, marca);
            pst.setString(6, puntos);
            pst.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public static String getSepararNombre(String nombreCompleto) {
        String[] partes = nombreCompleto.split(" ", 2);
        String nombre = partes[0];
        return nombre;
    }

    public static String getSepararApellido(String nombreCompleto) {
        String[] partes = nombreCompleto.split(" ", 2);
        String apellido = partes.length > 1 ? partes[1] : "";
        return apellido;
    }

    public static void ElegirPlacaMostrarUsuario(String[] arrayPlacas) {
        Scanner teclado = new Scanner(System.in);
        for (int i = 0; i < arrayPlacas.length; i++) {
            System.out.println("[" + (i + 1) + "]   " + arrayPlacas[i]);
        }
        System.out.println("Ingrese el # de fila correspondiente a la placa");
        System.out.print(" # de Fila: ");
        int nFilaMarc = teclado.nextInt();
        String placaElegida = arrayPlacas[nFilaMarc - 1];
        mostrarDatos("clientes", "placa", placaElegida);
    }

    public static void mostrarDatos(String nameTablaSQL, String nameColumna, String datoComparar) {

        ConexionSQL con1 = new ConexionSQL();
        Connection conet;
        Statement st;
        ResultSet rs;

        String DNI, nombreCliente, apellido, placa, marca, puntos;
        String aux;
        String sql = "select * from " + nameTablaSQL;
        int existe = 0;

        try {
            conet = con1.conexion();
            st = conet.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                aux = rs.getString(nameColumna);
                DNI = rs.getString("dni");
                nombreCliente = rs.getString("nombre");
                apellido = rs.getString("apellido");
                marca = rs.getString("marca");
                placa = rs.getString("placa");
                puntos = rs.getString("puntos");
                if (aux.equals(datoComparar)) {
                    existe = 1;
                    System.out.println("::::::::::::::::::::::::::::::::::");
                    System.out.println(" DNI      :" + DNI);
                    System.out.println(" Nombre   :" + nombreCliente);
                    System.out.println(" Apellido :" + apellido);
                    System.out.println(" Puntos   :" + puntos);
                    System.out.println(" Placa    :" + placa);
                    System.out.println(" Marca    :" + marca);
                    System.out.println("::::::::::::::::::::::::::::::::::");
                }
            }
            if (existe == 0) {
                System.out.println("CLIENTE NO EXISTE");
            }
        } catch (HeadlessException | SQLException e) {

        }
    }

    public static String[] obtenerPlacasPorMarca(String nombreTabla,
            String nombreColumnaMarcas, String marca, String nombreColumnaPlacas) {
        List<String> placas = new ArrayList<>();
        ConexionSQL con1 = new ConexionSQL();
        Connection conet;
        Statement st;
        ResultSet rs;
        try {
            // Obtén la conexión existente
            conet = con1.conexion();

            // Construye la consulta SQL parametrizada
            String consultaSQL = "SELECT " + nombreColumnaPlacas + " FROM " + nombreTabla
                    + " WHERE " + nombreColumnaMarcas + " = ?";

            try ( // Crea el objeto PreparedStatement y establece el valor del parámetro de marca
                    PreparedStatement statement = conet.prepareStatement(consultaSQL)) {
                statement.setString(1, marca);

                // Itera sobre los resultados y agrega las placas al ArrayList
                try ( // Ejecuta la consulta
                        ResultSet resultSet = statement.executeQuery()) {
                    // Itera sobre los resultados y agrega las placas al ArrayList
                    while (resultSet.next()) {
                        String placa = resultSet.getString(nombreColumnaPlacas);
                        placas.add(placa);
                    }
                    // Cierra los recursos
                }
            }
            conet.close();
        } catch (SQLException e) {
            System.out.println("ERROR de consulta por grupo de marca: " + e.getMessage());
        }

        // Convierte el ArrayList a un array de String y lo retorna
        return placas.toArray(new String[0]);
    }

    public static void mergeSort(String[] arr) {
        if (arr.length <= 1) {
            return;
        }

        // Divide el arreglo en dos mitades
        int mid = arr.length / 2;
        String[] left = new String[mid];
        String[] right = new String[arr.length - mid];

        System.arraycopy(arr, 0, left, 0, mid);
        System.arraycopy(arr, mid, right, 0, arr.length - mid);

        // Llama recursivamente a mergeSort para ordenar las dos mitades
        mergeSort(left);
        mergeSort(right);

        // Combina las dos mitades ordenadas
        merge(arr, left, right);
    }

    public static void merge(String[] arr, String[] left, String[] right) {
        int i = 0; // Índice para recorrer el subarreglo izquierdo
        int j = 0; // Índice para recorrer el subarreglo derecho
        int k = 0; // Índice para recorrer el arreglo original

        while (i < left.length && j < right.length) {
            if (left[i].compareTo(right[j]) < 0) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        // Copia los elementos restantes del subarreglo izquierdo, si los hay
        while (i < left.length) {
            arr[k] = left[i];
            i++;
            k++;
        }

        // Copia los elementos restantes del subarreglo derecho, si los hay
        while (j < right.length) {
            arr[k] = right[j];
            j++;
            k++;
        }
    }
}
