package Main;

import Nodos_Pilas.Pilas;
import java.util.Scanner;
import metodosOrdenamiento.BubbleSort;
import metodosOrdenamiento.InsertSort;
import metodosOrdenamiento.MergeSort;
import metodosOrdenamiento.QuickSort;

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
        System.out.println(" [1] BubbleSort");
        System.out.println(" [2] QuickSort");
        System.out.println(" [3] InsertSort");
        System.out.println(" [4] MergeSort");
        System.out.println(" [5] Nodos & Pilas");
        System.out.println(pts);
        int op = teclado.nextInt();
        System.out.println(pts);

        switch (op) {
            case 1:
                BubbleSort.main(new String[]{});
                break;
            case 2:
                QuickSort.main(new String[]{});
                break;
            case 3:
                InsertSort.main(new String[]{});
                break;
            case 4:
                MergeSort.main(new String[]{});
                break;
            case 5:
                Pilas.main(new String[]{});
                break;
            default:
                System.out.println("OPCIONES NO VALIDA");
        }

    }
}
