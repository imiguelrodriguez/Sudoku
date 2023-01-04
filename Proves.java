import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Classe Proves: classe principal per fer el testeig de les dues aproximacions
 *  (àvid i backtracking). Inclou un menú per consola.
 */
public class Proves {
    static Scanner teclat = new Scanner(System.in);
    public static void main(String[] args) {
        String fitxer, opcio;
        do {
            Eines.mostrarMenu();
            opcio = teclat.next();
            switch(opcio) {
                case "1": // Cas per resoldre el sudoku per l'estratègia àvida.
                    fitxer = Eines.demanarFitxer(teclat);
                    SudokuAvid sudAv = new SudokuAvid();
                    try {
                        sudAv.inicialitzarMatriu(fitxer);
                        System.out.println("Sudoku a resoldre:");
                        Eines.mostrarMatriu(sudAv.getMatriu());
                        if(sudAv.solucionaAvid()) {
                            System.out.println("\nSudoku resolt:");
                            Eines.mostrarMatriu(sudAv.getMatriu());
                        }
                        else
                            System.out.println("\nNo s'ha trobat una solució.");
                    } catch (IOException e) {
                        System.out.println("Error en el fitxer.");
                    }
                    break;
                case "2": // Cas per resoldre el sudoku per l'estratègia backtracking.
                    fitxer = Eines.demanarFitxer(teclat);
                    SudokuBacktracking sudBa = new SudokuBacktracking();
                    try {
                        sudBa.inicialitzarMatriu(fitxer);
                        System.out.println("Sudoku a resoldre:");
                        Eines.mostrarMatriu(sudBa.getMatriu());
                        if(!sudBa.solucionaBacktracking(new Posicio(0,0)))
                            System.out.println("\nNo s'ha trobat una solució.");
                    } catch (IOException e) {
                        System.out.println("Error en el fitxer.");
                    }
                    break;
                case "3": // Cas per obrir la interfície gràfica.
                    System.out.println("Obrint interfície gràfica...");
                    new SudokuWindow();
                    break;
                case "4": // Cas per fer l'anàlisi del cost temporal.
                    System.out.println("*** ANÀLISI DEL COST TEMPORAL ***");
                    Eines.analisiCost();
                    break;
                case "5": // Cas per sortir del programa.
                    System.out.println("Sortint del programa...");
                    break;
                default: // Cas per defecte.
                    System.out.println("Opció no vàlida.");
                    break;
            }
            if(!opcio.equals("5")) {
                System.out.println("\nPrem qualsevol tecla + ENTER per a continuar...");
                teclat.next();
            }
        } while(!opcio.equals("5"));
    }
}
