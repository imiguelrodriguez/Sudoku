import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class SudokuAvid {
    private static final int DIMENSIO = 9;
    private static int [][] matriu = new int[DIMENSIO][DIMENSIO];

    public void solucionaAvid() {
        boolean solucio = false, hiHaCandidats = true;
        Posicio posicio = new Posicio(0, 0), aux;
        int candidat;
        while(hiHaCandidats && !solucio) {
            if(matriu[posicio.getFila()][posicio.getColumna()]==0) {
                candidat = seleccionarCandidat(posicio);
                matriu[posicio.getFila()][posicio.getColumna()] = candidat;
                solucio = hiHaSolucio();
            }
                posicio = posicio.getSeguent();
                hiHaCandidats = (posicio != null);
                if (!hiHaCandidats && !solucio) {
                    hiHaCandidats = true;
                    posicio = new Posicio(0, 0);
                }

        }
    }

    private int seleccionarCandidat(Posicio posicio) {
        Integer[] c = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        LinkedHashSet<Integer> candidats = new LinkedHashSet<>(Arrays.asList(c));

        mirarFila(candidats, posicio);
        mirarColumna(candidats, posicio);
        mirarQuadrat(candidats, posicio);

        return candidats.iterator().next();
    }

    private void mirarQuadrat(HashSet<Integer> candidats, Posicio posicio) {
        int quadrat = posicio.getQuadrat();
        int fila, columna;

        if (quadrat < 3) fila = 0;
        else if (quadrat < 6) fila = 3;
        else fila = 6;

        if (quadrat == 0 || quadrat == 3 || quadrat == 6) columna = 0;
        else if (quadrat == 1 || quadrat == 4 || quadrat == 7) columna = 3;
        else columna = 6;
        int aux = columna;
        int limitFiles = fila + 3, limitCols = columna + 3;
        while(fila < limitFiles) {
            columna = aux;
            while(columna < limitCols) {
                if(matriu[fila][columna]!=0) candidats.remove(matriu[fila][columna]);
                columna++;
            }
            fila++;
        }
    }

    private void mirarColumna(HashSet<Integer> candidats, Posicio posicio) {
        int fila = 0;
        int columna = posicio.getColumna(), actual;

        while(fila < DIMENSIO){
            actual = matriu[fila][columna];
            if(actual != 0) candidats.remove(matriu[fila][columna]);
            fila++;
        }
    }

    private void mirarFila(HashSet<Integer> candidats, Posicio posicio) {
        int fila = posicio.getFila();
        int columna = 0, actual;

        while(columna < DIMENSIO){
            actual = matriu[fila][columna];
            if(actual != 0) candidats.remove(matriu[fila][columna]);
            columna++;
        }
    }

    private boolean hiHaSolucio() {
        for(int i = 0 ; i < DIMENSIO; i++)
            for(int j = 0 ; j < DIMENSIO; j++)
                if(matriu[i][j]==0) return false;
        return true;
    }
    public int[][] getMatriu() {
        return matriu;
    }
    public void inicialitzarMatriu(String fitxer) throws IOException {
        Eines.llegirMatriu(fitxer, matriu);
    }
}

