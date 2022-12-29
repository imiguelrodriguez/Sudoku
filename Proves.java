import java.io.IOException;

public class Proves {
    public static void main(String[] args) {
        SudokuAvid sudAv = new SudokuAvid();
        try {
            sudAv.inicialitzarMatriu("sudoku2.csv");
        } catch (IOException e) {
            System.out.println("Error en el fitxer.");
        }
        sudAv.solucionaAvid();
        Eines.mostrarMatriu(sudAv.getMatriu());
    }
}
