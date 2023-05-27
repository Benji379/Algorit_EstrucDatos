package metodosOrdenamiento;

import java.util.Scanner;

/**
 *
 * @author Benji
 */
public class BubbleSort extends metodosDAO {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BubbleSort p = new BubbleSort();
        System.out.println(" [1] Ingresar datos");
        System.out.println(" [2] Mostrar datos");
        int op = scanner.nextInt();
        
        if (op == 1) {
            System.out.print("Ingrese la cantidad de marcas: ");
            int n = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea después de leer el entero
            //      String[] nombres = new String[n];
            String marca;
            for (int i = 0; i < n; i++) {
                System.out.print("Ingrese La marca " + (i + 1) + ": ");
                //          nombres[i] = scanner.nextLine();
                marca = scanner.nextLine();
                p.AgregarMarca(marca);
            }
        } else {
            if (op == 2) {
                // el metodo "getConsultar" me retorna el Array de tipo String de la marca de los autos
                //Estoy pasando los datos del metodo getConsultar a mi nuevo Araay String [] NOMBRES
                String[] NOMBRES = getConsultar("marca_de_autos", "marca");
                //String[] NOMBRES = {DATOS DESORDENADOS};    
                bubbleSort(NOMBRES);
                //String [] NOMBRES = {DATOS OREDENADOS};
                System.out.println("\n\tMARCAS\n");
                
                for (int i = 0; i < NOMBRES.length; i++) {
                    System.out.println((i+1) + ").\t" + NOMBRES[i]);
                }
            } else {
                System.out.println("OPCION NO VALIDA");
            }
        }

    }
}
