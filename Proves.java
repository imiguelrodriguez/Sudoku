import java.io.IOException;
import java.util.Scanner;

public class Proves {
    static Scanner teclat = new Scanner(System.in);
    public static void main(String[] args) {
        String fitxer, opcio;
        do {
            Eines.mostrarMenu();
            opcio = teclat.next();
            switch(opcio) {
                case "1": fitxer = Eines.demanarFitxer(teclat);
                    SudokuAvid sudAv = new SudokuAvid();
                    try {
                        sudAv.inicialitzarMatriu(fitxer);
                        System.out.println("Sudoku a resoldre:");
                        Eines.mostrarMatriu(sudAv.getMatriu());
                        if(sudAv.solucionaAvid()) {
                            System.out.println("\nSudoku resolt:");
                            Eines.mostrarMatriu(sudAv.getMatriu());
                        }else
                            System.out.println("\nNo s'ha trobat una solució.");
                    } catch (IOException e) {
                        System.out.println("Error en el fitxer.");
                    }
                    break;
                case "2": fitxer = Eines.demanarFitxer(teclat);
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
                case "3": System.out.println("Obrint interfície gràfica...");
                    new SudokuWindow();
                    break;
                case "4": System.out.println("Sortint del programa...");
                    break;
                default: System.out.println("Opció no vàlida.");
                    break;
            }
            System.out.println("\nPrem qualsevol tecla + ENTER per a continuar...");
            opcio = teclat.next();
        } while(!opcio.equals("4"));
    }
}
