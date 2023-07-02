package Nodos_Pilas;

import ConexionBd.ConexionSQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Pilas {

    public static void main(String[] args) {
        String nombreTabla = "ventas";
        System.out.println("\n               VENTAS\n");
        List<NodoTabla> listaTabla = getTablaBD(nombreTabla);

        if (listaTabla != null && !listaTabla.isEmpty()) {
            int numeroColumnas = listaTabla.get(0).getCantidadDatos();
            imprimirEncabezados(listaTabla, numeroColumnas);
            imprimirDatos(listaTabla, numeroColumnas);
        } else {
            System.out.println("No se pudo obtener la tabla.");
        }
    }

    public static List<NodoTabla> getTablaBD(String nombreTabla) {
        ConexionSQL con1 = new ConexionSQL();
        Connection conet;
        Statement st;
        ResultSet rs;
        List<NodoTabla> listaTabla = new ArrayList<>();

        try {
            conet = con1.conexion();
            st = conet.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "SELECT * FROM " + nombreTabla;
            rs = st.executeQuery(consulta);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                NodoTabla nodo = new NodoTabla(metaData.getColumnName(i));

                while (rs.next()) {
                    nodo.agregarDato(rs.getString(i));
                }

                rs.beforeFirst();
                listaTabla.add(nodo);
            }

            rs.close();
            st.close();
            conet.close();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return listaTabla;
    }

    public static class NodoTabla {

        private final String encabezado;
        private final Stack<String> datos;

        public NodoTabla(String encabezado) {
            this.encabezado = encabezado;
            this.datos = new Stack<>();
        }
        
        public String getEncabezado() {
            return encabezado;
        }

        public void agregarDato(String dato) {
            datos.push(dato);
        }

        public int getCantidadDatos() {
            return datos.size();
        }

        public String getDato(int indice) {
            return datos.get(indice);
        }
    }

    public static void imprimirEncabezados(List<NodoTabla> listaTabla, int numeroColumnas) {
        listaTabla.forEach((nodo) -> {
            System.out.print(nodo.getEncabezado() + "\t\t");
        });
        System.out.println();

        for (int i = 0; i < numeroColumnas; i++) {
//            for (NodoTabla nodo : listaTabla) {
//                System.out.print("----------\t");
//            }
            System.out.println();
        }
    }

    public static void imprimirDatos(List<NodoTabla> listaTabla, int numeroColumnas) {
        int numeroFilas = listaTabla.get(0).getCantidadDatos();

        for (int fila = 0; fila < numeroFilas; fila++) {
            for (NodoTabla nodo : listaTabla) {
                System.out.print(nodo.getDato(fila) + "\t\t");
            }
            System.out.println();
        }
    }
}
