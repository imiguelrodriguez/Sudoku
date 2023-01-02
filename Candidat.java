public class Candidat {
    private int valor;
    private boolean fixa;

    public Candidat(int valor) {
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
}
