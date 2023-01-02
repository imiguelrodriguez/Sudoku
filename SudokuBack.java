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

        // si ja hem arribat al final de la matriu ja hem acabat, retornem per no fer més backtracking
        if (posicio.comparar(8, 8)) {
            Eines.mostrarMatriu(matriu);
            return true;
        }
        if(matriu[fila][columna].isFixa()) { // si la posició ja té un valor fix, passem al següent
            Posicio aux = new Posicio(fila, columna + 1, DIMENSIO);
            return solucionaBacktracking(aux);
        }
        for(int num = 1; num < 10; num++) {
            matriu[fila][columna].setValor(num);
            matriu[fila][columna].setFixa(true);
            System.out.println(fila + " " + columna + " Valor: " + matriu[fila][columna].getValor());

            if(esFactible(posicio)) {
                 // assignar el valor a la cel·la
                Posicio aux = new Posicio(fila, columna + 1, DIMENSIO);
                if(solucionaBacktracking(aux)) return true;

            }
                matriu[fila][columna].setValor(0); // tornar enrere per si ens hem equivocat
                matriu[fila][columna].setFixa(false);
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
                if(fil != posicio.getFila() && col != posicio.getColumna() && matriu[fil][col].getValor()==matriu[posicio.getFila()][col].getValor())
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
