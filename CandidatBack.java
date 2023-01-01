public class CandidatBack {
    private int valor;
    private boolean fixa;

    public CandidatBack(int valor) {
        this.valor = valor;
        fixa = valor == 0 ? false:true;
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
}
