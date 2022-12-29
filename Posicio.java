public class Posicio {
    private int fila;
    private int columna;

    public Posicio(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getQuadrat() {
        int quadrat = -1;
        if(this.fila <=2) {
            quadrat = quadratColumna(0);
        }
        else if(this.fila <=5){
            quadrat = quadratColumna(1);
        }
        else {
            quadrat = quadratColumna(2);
        }
        return quadrat;
}

    private int quadratColumna(int i) {
        int quadrat = -1;
        switch (i) {
            case 0:
                if(this.columna <=2) quadrat = 0;
                else if(this.columna <=5) quadrat = 1;
                else quadrat = 2;
                break;

            case 1:
                if(this.columna <=2) quadrat = 3;
                else if(this.columna <=5) quadrat = 4;
                else quadrat = 5;
                break;

            case 2:
                if(this.columna <=2) quadrat = 6;
                else if(this.columna <=5) quadrat = 7;
                else quadrat = 8;
                break;

        }
        return quadrat;
    }

    public Posicio getSeguent() {
        if(this.columna < 8) return new Posicio(this.fila, this.columna + 1);
        else if(this.fila < 8) return new Posicio(this.fila + 1, 0);
        else return null;
    }
}
