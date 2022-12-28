import java.io.*;

public class SudokuAvid {
    private static final int DIMENSIO = 9;
    private static int [][] matriu = new int[DIMENSIO][DIMENSIO];



    public static void main(String[] args) throws IOException {
        Eines.llegirMatriu("sudoku.csv", matriu);
        Eines.mostrarMatriu(matriu);
    }
}

