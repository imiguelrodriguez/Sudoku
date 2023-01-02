public class Posicio {
    private int fila;
    private int columna;

    public Posicio(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public Posicio(int fila, int columna, int dimensio) {
        this.fila = fila + columna / dimensio;
        this.columna = columna % dimensio;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public boolean comparar(int fila, int col) {
        return fila==this.fila && col==this.columna;
    }
}
