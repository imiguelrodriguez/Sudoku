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

    public boolean solucionaBacktracking(Posicio posicio) {
        int fila = posicio.getFila(), columna = posicio.getColumna();

        for(int num = 1; num < 10; num++) {
            if(!matriu[fila][columna].isFixa())
                matriu[fila][columna].setValor(num);
            if(esFactible(posicio)) {
                // si ja hem arribat al final de la matriu ja hem acabat, retornem per no fer més backtracking
                if (posicio.comparar(8, 8)) {
                    Eines.mostrarMatriu(matriu);
                    return true;
                }else{
                    // assignar el valor a la cel·la
                    Posicio aux = new Posicio(fila, columna + 1, DIMENSIO);
                    if(solucionaBacktracking(aux)) return true;
                    if(!matriu[aux.getFila()][aux.getColumna()].isFixa())
                        matriu[aux.getFila()][aux.getColumna()].setValor(0); // tornar enrere per si ens hem equivocat
                }

            }
            if(matriu[fila][columna].isFixa()){
                break;
            }
        }

        return false;
    }

    private boolean esFactible(Posicio posicio) {
        return revisarFila(posicio) && revisarColumna(posicio) && revisarQuadrat(posicio);
    }

    private boolean revisarQuadrat(Posicio posicio) {
        boolean factible = true;
        int fila = (posicio.getFila()/3)*3;
        int columna = (posicio.getColumna()/3)*3;
        for(int fil=fila; fil<fila+3 && factible; fil++){
            for(int col=columna; col<columna+3 && factible; col++){
                if(!(fil == posicio.getFila() && col == posicio.getColumna()) && matriu[fil][col].getValor()==matriu[posicio.getFila()][posicio.getColumna()].getValor())
                    factible = false;
            }
        }
        return factible;
    }

    private boolean revisarColumna(Posicio posicio) {
        boolean factible = true;
        int col = posicio.getColumna();
        for(int fil = 0; fil < DIMENSIO && factible; fil++){
            if(fil != posicio.getFila() && matriu[fil][col].getValor()==matriu[posicio.getFila()][col].getValor())
                factible = false;
        }
        return factible;
    }

    private boolean revisarFila(Posicio posicio) {
        boolean factible = true;
        int fil = posicio.getFila();
        for(int col = 0; col < DIMENSIO && factible; col++){
            if(col != posicio.getColumna() && matriu[fil][col].getValor()==matriu[fil][posicio.getColumna()].getValor())
                factible = false;
        }
        return factible;
    }
}
