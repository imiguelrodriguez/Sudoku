/**
 * Classe Posicio: serveix per a desar una posició de la matriu i conté la fila i la columna
 * que fan referència a aquella posició.
 */
public class Posicio {
    private int fila;
    private int columna;

    /**
     * Constructor base de Posicio.
     * @param fila enter que indica la fila de la matriu.
     * @param columna enter que indica la columna de la matriu.
     */
    public Posicio(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Constructor de Posicio (utilitzat en Backtracking per incrementar una posició
     * a la matriu respectant els límits).
     * @param fila enter que indica la fila de la matriu.
     * @param columna enter que indica la columna de la matriu.
     * @param dimensio enter que indica la dimensió màxima de la matriu (per les files i les columnes).
     */
    public Posicio(int fila, int columna, int dimensio) {
        this.fila = fila + columna / dimensio;
        this.columna = columna % dimensio;
    }

    /**
     * Getter de fila.
     * @return enter que indica la fila.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Getter de la columna.
     * @return enter que indica la columna.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Mètode que compara dues posicions.
     * @param fila enter que indica la fila a comparar.
     * @param col enter que indica la columna a comparar.
     * @return cert si són la mateixa posició, fals altrament.
     */
    public boolean comparar(int fila, int col) {
        return fila==this.fila && col==this.columna;
    }
}
