package ArbolBinario;

import ConexionBd.ConexionSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Nodo {

    String DNI;
    String nombreCliente;
    String apellido;
    int puntos;
    String placa;
    String marca;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(String DNI, String nombreCliente, String apellido, int puntos, String placa, String marca) {
        this.DNI = DNI;
        this.nombreCliente = nombreCliente;
        this.apellido = apellido;
        this.puntos = puntos;
        this.placa = placa;
        this.marca = marca;
        this.izquierdo = null;
        this.derecho = null;
    }
}

public class RQMS_GestionCliente {

    private Nodo raiz;

    public RQMS_GestionCliente() {
        raiz = null;
    }

    // Método para insertar un nuevo nodo en el árbol (PILA)
    private Nodo insertar(Nodo nodo, String DNI, String nombreCliente, String apellido, int puntos, String placa, String marca) {
        if (nodo == null) {//evalua si el valor de el nodo sea igual a nulo
            return new Nodo(DNI, nombreCliente, apellido, puntos, placa, marca); // retorna el nuevo nodo
        } else {
            //no se le asigna datos a nodo
        }

        // Si el dato es menor, se inserta a la izquierda
        if (placa.compareTo(nodo.placa) < 0) {
            nodo.izquierdo = insertar(nodo.izquierdo, DNI, nombreCliente, apellido, puntos, placa, marca);
        } else if (placa.compareTo(nodo.placa) > 0) {
            // Si el dato es mayor, se inserta a la derecha
            nodo.derecho = insertar(nodo.derecho, DNI, nombreCliente, apellido, puntos, placa, marca);
        }

        return nodo;
    }

    public void insertar(String DNI, String nombreCliente, String apellido, int puntos, String placa, String marca) {
        raiz = insertar(raiz, DNI, nombreCliente, apellido, puntos, placa, marca);
    }

    // Método para buscar un dato en el árbol
    public Nodo buscar(String dato) {
        return buscar(raiz, dato);
    }

    //BUSCAMOS EL DATO DENTRO 
    private Nodo buscar(Nodo nodo, String dato) {
        if (nodo == null || nodo.placa.equals(dato)) {
            return nodo;
        }
        if (dato.compareTo(nodo.placa) < 0) {
            return buscar(nodo.izquierdo, dato);
        } else {
            return buscar(nodo.derecho, dato);
        }
    }

    // Método para imprimir el árbol en orden (in-order traversal)
    public void imprimirEnOrden() {
        imprimirEnOrden(raiz);
    }

    private void imprimirEnOrden(Nodo nodo) {
        if (nodo != null) {
            imprimirEnOrden(nodo.izquierdo);
            System.out.println("::::::::::::::::::::::::::::::::::");
            System.out.println(" DNI      :" + nodo.DNI);
            System.out.println(" Nombre   :" + nodo.nombreCliente);
            System.out.println(" Apellido :" + nodo.apellido);
            System.out.println(" Puntos   :" + nodo.puntos);
            System.out.println(" Placa    :" + nodo.placa);
            System.out.println(" Marca    :" + nodo.marca);
            System.out.println("::::::::::::::::::::::::::::::::::");
            imprimirEnOrden(nodo.derecho);
        }
    }

    private static void buscarPlaca(String PLACA) {
        // Crear la conexión a la base de datos
        ConexionSQL conexionSQL = new ConexionSQL();
        Connection conexion = conexionSQL.conexion();

        // Crear el árbol y poblarlo con los datos de la base de datos
        RQMS_GestionCliente arbol = new RQMS_GestionCliente();

        String consulta = "SELECT * FROM clientes"; // Reemplazar por el nombre de la tabla real
        try (PreparedStatement pst = conexion.prepareStatement(consulta)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String DNI = rs.getString("dni");
                String nombreCliente = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int puntos = rs.getInt("puntos");
                String placa = rs.getString("placa");
                String marca = rs.getString("marca");

                arbol.insertar(DNI, nombreCliente, apellido, puntos, placa, marca);
            }
        } catch (SQLException ex) {
        }

        Nodo nodoEncontrado = arbol.buscar(PLACA);

        if (nodoEncontrado != null) {
            System.out.println("Cliente encontrado:");
            System.out.println("::::::::::::::::::::::::::::::::::");
            System.out.println(" DNI      :" + nodoEncontrado.DNI);
            System.out.println(" Nombre   :" + nodoEncontrado.nombreCliente);
            System.out.println(" Apellido :" + nodoEncontrado.apellido);
            System.out.println(" Puntos   :" + nodoEncontrado.puntos);
            System.out.println(" Placa    :" + nodoEncontrado.placa);
            System.out.println(" Marca    :" + nodoEncontrado.marca);
            System.out.println("::::::::::::::::::::::::::::::::::");
        } else {
            System.out.println("Cliente no encontrado");
        }
    }

    public static void main(String[] args) {
        System.out.println("\n           BUSCAR\n");
        System.out.println(" [1] por placa");
        Scanner teclado = new Scanner(System.in);
        int op = teclado.nextInt();
        System.out.println("");
        System.out.print("PLACA: ");
        String placa = teclado.next();
        buscarPlaca(placa);
    }
}
