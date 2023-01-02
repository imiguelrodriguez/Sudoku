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
            System.out.println(fila + " " + columna + " Valor: " + matriu[fila][columna].getValor());
            if(esFactible(posicio)) {
                if (posicio.comparar(8, 8)) {
                    Eines.mostrarMatriu(matriu);
                } else {
                    Posicio aux = new Posicio(fila, columna + 1, DIMENSIO);
                    solucionaBacktracking(aux);
                    if(!matriu[aux.getFila()][aux.getColumna()].isFixa())
                        matriu[aux.getFila()][aux.getColumna()].setValor(0);
                }
            }
            if(matriu[fila][columna].isFixa())
                break;
            else
                matriu[fila][columna].setValor(matriu[fila][columna].getValor()+1);
        }while(matriu[fila][columna].getValor() <= 9);
    }

    private boolean esFactible(Posicio posicio) {
        return revisarFila(posicio) && revisarColumna(posicio) && revisarQuadrat(posicio);
    }

    private boolean revisarQuadrat(Posicio posicio) {
        boolean factible = true;
        int fila = (posicio.getFila()/3)*3;
        int columna = (posicio.getColumna()/3)*3;
        for(int fil=fila; fil<fila+3; fil++){
            for(int col=columna; col<columna+3; col++){
                if(matriu[fil][col].getValor()==matriu[posicio.getFila()][col].getValor())
                    factible = true;
            }
        }
        return factible;
    }

    private boolean revisarColumna(Posicio posicio) {
        boolean factible = true;
        int col = posicio.getColumna();
        for(int fil = 0; fil < posicio.getFila() && factible; fil++){
            if(matriu[fil][col].getValor()==matriu[posicio.getFila()][col].getValor())
                factible = false;
        }
        return factible;
    }

    private boolean revisarFila(Posicio posicio) {
        boolean factible = true;
        int fil = posicio.getFila();
        for(int col = 0; col < posicio.getColumna() && factible; col++){
            if(matriu[fil][col].getValor()==matriu[fil][posicio.getColumna()].getValor())
                factible = false;
        }
        return factible;
    }
}
