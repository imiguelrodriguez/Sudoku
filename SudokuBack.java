import java.io.IOException;
import java.util.LinkedHashSet;

public class SudokuBack {
    private static final int DIMENSIO = 9;
    private static CandidatBack[][] matriu = new CandidatBack[DIMENSIO][DIMENSIO];

    public static CandidatBack[][] getMatriu() {
        return matriu;
    }

    public void inicialitzarMatriu(String fitxer) throws IOException {
        Eines.llegirMatriu(fitxer, matriu);
    }

    public void solucionaBacktracking(Posicio posicio) {
        int fila = posicio.getFila(), columna = posicio.getColumna();
        if(!matriu[fila][columna].isFixa())
            matriu[fila][columna].setValor(1);
        do{
            if(esFactible(posicio)) {
                if (posicio.comparar(8, 8)) {
                    Eines.mostrarMatriu(matriu);
                } else {
                    Posicio aux = new Posicio(fila, columna + 1, DIMENSIO - 1);
                    solucionaBacktracking(aux);
                    matriu[aux.getFila()][aux.getColumna()].setValor(0);
                }
            }
            if(!matriu[fila][columna].isFixa())
                break;
            else
                matriu[fila][columna].setValor(matriu[fila][columna].getValor()+1);
        }while(matriu[fila][columna].getValor() <= 9);
    }
}
