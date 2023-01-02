public class CandidatBack {
    private int valor;
    private boolean fixa;

    public CandidatBack(int valor) {
        this.valor = valor;
        fixa = valor != 0;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isFixa() {
        return fixa;
    }

    public void setFixa(boolean fixa) {
        this.fixa = fixa;
    }
}
