import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Eines {
    public static void llegirMatriu(String fitxer, LinkedHashSet<Integer>[][] matriu) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(fitxer));
        String linia = lector.readLine();
        int i = 0;
        while (linia!=null) {
            String [] camps = linia.split(";");
            for(int j=0; j < camps.length; j++) {
                matriu[i][j] = new LinkedHashSet<Integer>();
                matriu[i][j].add(Integer.parseInt(camps[j]));
            }
            linia = lector.readLine();
            i++;
        }
    }

    public static void llegirMatriu(String fitxer, Candidat[][] matriu) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(fitxer));
        String linia = lector.readLine();
        int i = 0;
        while (linia!=null) {
            String [] camps = linia.split(";");
            for(int j=0; j < camps.length; j++) {
                matriu[i][j] = new Candidat(Integer.parseInt(camps[j]));
            }
            linia = lector.readLine();
            i++;
        }
    }

    public static void mostrarMatriu(LinkedHashSet<Integer>[][] matriu) {
        for(int i = 0; i < matriu[0].length; i++) {
            System.out.println();
            for (int j = 0; j < matriu[0].length; j++)
                if(matriu[i][j].size()==1)
                    System.out.print("\t" + matriu[i][j].iterator().next());
                else
                    System.out.print("\t0");
        }
        System.out.println();
    }

    public static void mostrarMatriu(Candidat[][] matriu) {
        for(int i = 0; i < matriu[0].length; i++) {
            System.out.println();
            for (int j = 0; j < matriu[0].length; j++)
                System.out.print("\t" + matriu[i][j].getValor());
        }
        System.out.println();
    }

    public static void cercaDirectori(String directori){
        File directorio = new File(directori + "/");
        String[] lista = directorio.list();
        Arrays.sort(lista);
        for (int i = 0; i < lista.length; i++) {
            System.out.println("\t" + lista[i]);
        }
    }

    public static String demanarFitxer(Scanner teclat) {
        cercaDirectori("sudokus");
        System.out.println("Escriu el fitxer que vol executar: ");
        return "sudokus/" + teclat.next();
    }

    public static void mostrarMenu() {
        System.out.println("\n****************************");
        System.out.println("*** MENÚ PRÀCTICA SUDOKU ***");
        System.out.println("****************************\n");
        System.out.println("Escull una opció:\n");
        System.out.println("1. Resoldre per algorisme voraç.");
        System.out.println("2. Resoldre per backtracking.");
        System.out.println("3. Obrir interfície gràfica.");
        System.out.println("4. Sortir\n");
    }
}
