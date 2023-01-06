import javax.swing.*;
import java.awt.*;
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
    private static SudokuBacktracking sudBa;
    private static SudokuBacktracking sudBaInicial;
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
     * Mètode que busca tots els arxius que hi ha dins d'un directori específic per fer les proves temporals.
     * @param directori String que indica el nom del directori on hi ha els fitxers.
     * @return vector de String amb els noms dels fitxers de prova.
     */
    public static String[] fitxersProva(String directori){
        File dir = new File(directori + "/");
        String[] llista = dir.list();
        if(llista!=null) {
            Arrays.sort(llista);
            for(int i = 0; i < llista.length; i++)
                llista[i] = directori+"/"+llista[i];
           return llista;
        }
        else {
            System.out.println("No existeix aquest directori.");
            return null;
        }
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
     * Mètode que analitza el cost temporal d'ambdues estratègies amb els
     * sudokus de prova del directori sudokus (opció 4 del menú).
     */
    public static void analisiCost() {
        System.out.println("A continuació es farà una anàlisi del cost temporal comparant ambdós algorismes.\n");
        String[] fitxers = fitxersProva("sudokus");
        long tempsIni, tempsFi;
        if(fitxers != null) {
            for (String fitxer : fitxers) {
                System.out.print("****");
                fitxer.chars().forEach(car -> System.out.print("*"));
                System.out.println("\n| " + fitxer + " |");
                fitxer.chars().forEach(car -> System.out.print("*"));
                System.out.print("****\n");
                System.out.println("\n*** ESTRATÈGIA BACKTRACKING ***\n");
                SudokuBacktracking sudBa = new SudokuBacktracking();
                try {
                    tempsIni = System.currentTimeMillis();
                    sudBa.inicialitzarMatriu(fitxer);
                    System.out.println("Sudoku a resoldre:");
                    Eines.mostrarMatriu(sudBa.getMatriu());
                    if (!sudBa.solucionaBacktracking(new Posicio(0, 0)))
                        System.out.println("\nNo s'ha trobat una solució.");
                    tempsFi = System.currentTimeMillis();
                    System.out.println("\nL'estratègia Backtracking ha trigat: " + (tempsFi-tempsIni) + " mil·lisegons.\n");
                } catch (IOException e) {
                    System.out.println("Error en el fitxer.");
                }

                System.out.println("*** ESTRATÈGIA ÀVIDA ***\n");
                SudokuAvid sudAv = new SudokuAvid();
                try {
                    tempsIni = System.currentTimeMillis();
                    sudAv.inicialitzarMatriu(fitxer);
                    System.out.println("Sudoku a resoldre:");
                    Eines.mostrarMatriu(sudAv.getMatriu());
                    if(sudAv.solucionaAvid()) {
                        System.out.println("\nSudoku resolt:");
                        Eines.mostrarMatriu(sudAv.getMatriu());
                    }
                    else
                        System.out.println("\nNo s'ha trobat una solució.");
                    tempsFi = System.currentTimeMillis();
                    System.out.println("\nL'estratègia Àvida ha trigat: " + (tempsFi-tempsIni) + " mil·lisegons.\n");
                } catch (IOException e) {
                    System.out.println("Error en el fitxer.");
                }
            }
        }
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
        System.out.println("4. Anàlisi del cost temporal.");
        System.out.println("5. Sortir\n");
    }

    /**
     * Mètode que carrega un sudoku en la finestra principal.
     * @param finestra instància de SudokuWindow.
     * @param path String amb la ruta de l'arxiu on hi ha definit el Sudoku.
     * @throws IOException si hi ha problemes amb el fitxer.
     */
    public static void carregarSudoku(SudokuWindow finestra, String path) throws IOException {
        sudBa = new SudokuBacktracking();
        sudBaInicial = new SudokuBacktracking();
        Eines.llegirMatriu(path, sudBa.getMatriu());
        Eines.llegirMatriu(path, sudBaInicial.getMatriu());
        JFormattedTextField[][] matriu = finestra.getMatriu();

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(sudBa.getMatriu()[i][j].getValor()!=0) {
                    matriu[i][j].setValue(null); // esborra el que hi hagués abans
                    matriu[i][j].setBackground(Color.white);
                    matriu[i][j].setText(String.valueOf(sudBa.getMatriu()[i][j].getValor()));
                    matriu[i][j].setBackground(Color.GRAY);
                    matriu[i][j].setEditable(false);
                }
                else {
                    matriu[i][j].setEditable(true);
                    matriu[i][j].setValue(null); // esborra el que hi hagués abans
                    matriu[i][j].setBackground(Color.white);
                }
            }
        }
    }

    /**
     * Mètode que soluciona el Sudoku que hi ha carregat a la finestra.
     * @param finestra instància de SudokuWindow.
     */
    public static void solucionaFinestra(SudokuWindow finestra) {
        finestra.setSolucio(true);
        sudBaInicial.solucionaBacktracking(new Posicio(0, 0));
        JFormattedTextField[][] matriu = finestra.getMatriu();

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                matriu[i][j].setValue(null);
                matriu[i][j].setText(String.valueOf(sudBaInicial.getMatriu()[i][j].getValor()));
                matriu[i][j].setEditable(false);
            }
        }
    }

    /**
     * Mètode que pinta els quadrats del Sudoku a la finestra.
     * @param finestra instància de SudokuWindow.
     */
    public static void pintarQuadrats (SudokuWindow finestra) {
        JFormattedTextField[][] matriu = finestra.getMatriu();
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(i == 0 || i == 3 || i == 6)
                    matriu[i][j].setBorder(BorderFactory.createCompoundBorder(matriu[i][j].getBorder(), BorderFactory.createMatteBorder(3, 0, 0, 0, Color.black)));
                if(i == 8)
                    matriu[i][j].setBorder(BorderFactory.createCompoundBorder(matriu[i][j].getBorder(), BorderFactory.createMatteBorder(0, 0, 3, 0, Color.black)));

                if(j == 0 || j == 3 || j == 6)
                    matriu[i][j].setBorder(BorderFactory.createCompoundBorder(matriu[i][j].getBorder(), BorderFactory.createMatteBorder(0, 3, 0, 0, Color.black)));
                if (j == 8)
                    matriu[i][j].setBorder(BorderFactory.createCompoundBorder(matriu[i][j].getBorder(), BorderFactory.createMatteBorder(0, 0, 0, 3, Color.black)));
            }
        }
    }

    /**
     * Getter de sudBa.
     * @return instància de SudokuBacktracking.
     */
    public static SudokuBacktracking getSudBa() {
        return sudBa;
    }

    /**
     * Mètode que revisa que no hi hagi coincidències al quadrat.
     * @param i fila
     * @param j columna
     * @param valor el valor de la casella
     * @return cert si és factible, fals altrament
     */
    public static boolean revisarQuadrat(int i, int j, int valor) {
        boolean factible = true;
        int fila = (i/3)*3;
        int columna = (j/3)*3;
        for(int fil=fila; fil<fila+3 && factible; fil++){
            for(int col=columna; col<columna+3 && factible; col++){
                if(!(fil == i && col == j) && sudBa.getMatriu()[fil][col].getValor()== valor)
                    factible = false;
            }
        }
        return factible;
    }

    /**
     * Mètode que revisa que no hi hagi coincidències a la fila.
     * @param i fila
     * @param j columna
     * @param valor el valor de la casella
     * @return cert si és factible, fals altrament
     */
    public static boolean revisarFila(int i, int j, int valor) {
        boolean factible = true;
        int fil = i;
        for(int col = 0; col < 9 && factible; col++){
            if(col != j && sudBa.getMatriu()[fil][col].getValor()== valor)
                factible = false;
        }
        return factible;
    }

    /**
     * Mètode que revisa que no hi hagi coincidències a la columna.
     * @param i fila
     * @param j columna
     * @param valor el valor de la casella
     * @return cert si és factible, fals altrament
     */
    public static boolean revisarColumna(int i, int j, int valor) {
        boolean factible = true;
        int col = j;
        for(int fil = 0; fil < 9 && factible; fil++){
            if(fil != i && sudBa.getMatriu()[fil][col].getValor()== valor)
                factible = false;
        }
        return factible;
    }

    /**
     * Mètode que comprova que una solució sigui correcta. Utilitzat des de SudokuWindow.
     * @return cert si és correcta, fals altrament.
     */
    public static boolean comprovaSolucio() {
        boolean factible = true;
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                factible =  revisarColumna(i, j, sudBa.getMatriu()[i][j].getValor()) && revisarFila(i, j, sudBa.getMatriu()[i][j].getValor()) && revisarQuadrat(i, j, sudBa.getMatriu()[i][j].getValor());
                if(!factible) return false;
            }
        }
        return true;
    }
}
