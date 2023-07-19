package Main;

import ArbolBinario.RQMS_GestionCliente;
import Nodos_Pilas_HashTable.RQMS_RegistroVentas;
import java.util.Scanner;
import metodosOrdenamiento.RQMS_RegistroDatos;
import metodosOrdenamiento.InsertSort;
import metodosOrdenamiento.MergeSort;
import metodosOrdenamiento.RQMS_RegistroInventario;

/**
 *
 * @author Flypaim Machine
 */
public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        String pts = "..................................";
        System.out.println(pts);
        System.out.println("\n    BIENVENIDO AL SISTEMA\n");
        System.out.println(" [1] Registro Datos");//BubbleSort
        System.out.println(" [2] Registro de Inventario");//QuickSort
        System.out.println(" [3] InsertSort");//InsertSort
        System.out.println(" [4] MergeSort");
        System.out.println(" [5] Registro Ventas");//HashMap
        System.out.println(" [6] Gestion Cliente");//Arbol Binario
        System.out.println(pts);
        int op = teclado.nextInt();
        System.out.println(pts);

        switch (op) {
            case 1:
                RQMS_RegistroDatos.main(new String[]{});
                break;
            case 2:
                RQMS_RegistroInventario.main(new String[]{});
                break;
            case 3:
                InsertSort.main(new String[]{});
                break;
            case 4:
                MergeSort.main(new String[]{});
                break;
            case 5:
                RQMS_RegistroVentas.main(new String[]{});
                break;
            case 6:
                RQMS_GestionCliente.main(new String[]{});
                break;
            default:
                System.out.println("OPCIONES NO VALIDA");
        }

    }
}
