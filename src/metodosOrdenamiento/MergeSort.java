package metodosOrdenamiento;

import ConexionBd.ConexionSQL;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Benji
 */
public class MergeSort extends metodosDAO {

    

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
            System.out.println("\nMARCAS\n");
            for (int i = 0; i < marcas.length; i++) {
                System.out.println(" [" + (i + 1) + "] " + marcas[i]);
            }
            System.out.print(" Marca: ");
            int num = teclado.nextInt();
            marca = marcas[num - 1];
            System.out.println("El producto elegido es: " + marca);
            AgregarCliente(dni, nombre, apellido, placa, marca, "0");

        }
    }
}
