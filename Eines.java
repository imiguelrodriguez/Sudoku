import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Classe Eines: classe auxiliar que permetrà fer diverses operacions de suport.
 * No té constructor perquè es farà servir com a llibreria.
 */
public class Eines {
    /**
     * Mètode sobrecarregat que llegeix un sudoku de fitxer i el carrega en la variable matriu.
     * @param fitxer String amb el nom del fitxer a carregar.
     * @param matriu matriu de conjunt d'enters a inicialitzar.
     * @throws IOException si hi ha problemes amb els fitxers.
     */
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

    /**
     * Mètode sobrecarregat que llegeix un sudoku de fitxer i el carrega en la variable matriu.
     * @param fitxer String amb el nom del fitxer a carregar.
     * @param matriu matriu de Candidats a inicialitzar.
     * @throws IOException si hi ha problemes amb els fitxers.
     */
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

    /**
     * Mètode que imprimeix la matriu per pantalla.
     * @param matriu conjunt de cel·les a imprimir.
     */
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

    /**
     * Mètode que imprimeix la matriu per pantalla.
     * @param matriu conjunt de Candidats a imprimir.
     */
    public static void mostrarMatriu(Candidat[][] matriu) {
        for(int i = 0; i < matriu[0].length; i++) {
            System.out.println();
            for (int j = 0; j < matriu[0].length; j++)
                System.out.print("\t" + matriu[i][j].getValor());
        }
        System.out.println();
    }

    /**
     * Mètode que busca tots els arxius que hi ha dins d'un directori específic.
     * @param directori String que indica el nom del directori on hi ha els fitxers.
     */
    public static void cercaDirectori(String directori){
        File dir = new File(directori + "/");
        String[] llista = dir.list();
        if(llista!=null) {
            Arrays.sort(llista);
            for (String s : llista) {
                System.out.println("\t" + s);
            }
        }
        else System.out.println("No existeix aquest directori.");

    }

    /**
     * Mètode que demana a l'usuari que esculli un fitxer dels que s'han mostrat
     * per pantalla.
     * @param teclat Scanner que serveix de teclat al programa de proves.
     * @return String amb el path del fitxer escollit.
     */
    public static String demanarFitxer(Scanner teclat) {
        cercaDirectori("sudokus");
        System.out.println("Escriu el fitxer que vol executar: ");
        return "sudokus/" + teclat.next();
    }

    /**
     * Mètode que mostra el menú principal.
     */
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
