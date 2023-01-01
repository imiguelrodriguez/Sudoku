import java.io.IOException;

public class Proves {
    public static void main(String[] args) {
        SudokuAvidVersioNova sudAv = new SudokuAvidVersioNova();
        try {
            sudAv.inicialitzarMatriu("sudoku1.csv");
        } catch (IOException e) {
            System.out.println("Error en el fitxer.");
        }
        sudAv.solucionaAvid();
        Eines.mostrarMatriu(sudAv.getMatriu());
    }
}
