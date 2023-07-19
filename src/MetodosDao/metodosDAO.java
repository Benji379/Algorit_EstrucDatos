package MetodosDao;

import ConexionBd.ConexionSQL;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Flypaim Machine
 */
public class metodosDAO {

    ConexionSQL cc = new ConexionSQL();
    Connection con = cc.conexion();

    public static void AumentarPuntosCliente(String dniCliente, String puntos) {
        ConexionSQL con1 = new ConexionSQL();
        int idc = Integer.parseInt(getID("clientes", dniCliente, "dni", "id"));
        Connection conet;
        Statement st;
        ResultSet rs;
        String punto = puntos;

        try {
            String sql = "Update clientes set id='" + idc + "', puntos='" + punto + "' where id=" + idc;
            conet = con1.conexion();
            st = conet.createStatement();
            st.executeUpdate(sql);
            System.out.println("Puntos del cliente Modificados");
        } catch (HeadlessException | SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

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
            ArrayList<String> añadiendo = new ArrayList<>();

            while (rs.next()) {
                String marca = rs.getString(nameColumna);
                añadiendo.add(marca);
            }
            // Convertir el ArrayList en un array de tipo String
            String[] ArrayRetornado = new String[añadiendo.size()];
            for (int i = 0; i < añadiendo.size(); i++) {
                ArrayRetornado[i] = añadiendo.get(i);
            }
            return ArrayRetornado;

        } catch (SQLException e) {
            // Manejo de excepciones
        }
        return null; // Si ocurre una excepción o no se encuentran datos, retorna null
    }

    public static String[] getConsultar(String nameTabla, String nameColumna, String nameColumna2) {

        ConexionSQL con1 = new ConexionSQL();
        Connection conet;

        Statement st;
        ResultSet rs;

        String sql = "select * from " + nameTabla;

        try {
            conet = con1.conexion();
            st = conet.createStatement();
            rs = st.executeQuery(sql);
            ArrayList<String> añadiendo = new ArrayList<>();

            while (rs.next()) {
                String datoColumna1 = rs.getString(nameColumna);
                String datoColumna2 = rs.getString(nameColumna2);
                String concatenacion = datoColumna1 + " " + datoColumna2;
                añadiendo.add(concatenacion);
            }

            // Convertir el ArrayList en un array de tipo String
            String[] ArrayRetornado = new String[añadiendo.size()];
            for (int i = 0; i < añadiendo.size(); i++) {
                ArrayRetornado[i] = añadiendo.get(i);
            }
            return ArrayRetornado;

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

                    String aux = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = aux;

                    swapped = true;
                }
            }
            // Si no hubo intercambios en esta iteración, el arreglo ya está ordenado
            if (!swapped) {
                break;
            }
        }
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

    public static void mostrarDatos(String nameTablaSQL, String nameColumna1, String nameColumna2, String datoComparar1, String datoComparar2) {

        ConexionSQL con1 = new ConexionSQL();
        Connection conet;
        Statement st;
        ResultSet rs;

        String DNI, nombreCliente, apellido, placa, marca, puntos;
        String aux1, aux2;
        String sql = "select * from " + nameTablaSQL;
        int existe = 0;

        try {
            conet = con1.conexion();
            st = conet.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                aux1 = rs.getString(nameColumna1);
                aux2 = rs.getString(nameColumna2);
                DNI = rs.getString("dni");
                nombreCliente = rs.getString("nombre");
                apellido = rs.getString("apellido");
                marca = rs.getString("marca");
                placa = rs.getString("placa");
                puntos = rs.getString("puntos");
                if ((aux1.equals(datoComparar1)) && (aux2.equals(datoComparar2))) {
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

    public static void AgregarVenta(String dni, String total, String placa, String fecha) {
        ConexionSQL cc = new ConexionSQL();
        Connection con = cc.conexion();
        String SQL = "insert into ventas (dni,total,placa,fecha) values(?,?,?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, dni);
            pst.setString(2, total);
            pst.setString(3, placa);
            pst.setString(4, fecha);
            pst.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public static String obtenerFechaHoraActual() {
        // Obtener la fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Definir el formato de fecha y hora deseado
        DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Formatear la fecha y hora actual según el formato deseado
        String fechaHoraFormateada = fechaHoraActual.format(formatoFechaHora);

        // Retornar la fecha y hora formateada
        return fechaHoraFormateada;
    }

}
