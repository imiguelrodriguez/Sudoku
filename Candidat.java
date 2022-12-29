public class Candidat {

    private int valor, prioritat;

    public Candidat(int valor){
        this.valor = valor;
        this.prioritat = 0;
    }

    public void incPrioritat() {
        this.prioritat++;
    }

    public int getValor() {
        return valor;
    }

    public int getPrioritat() {
        return prioritat;
    }
}
