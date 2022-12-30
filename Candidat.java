public class Candidat {

    private int valor, prioritat, ratioC, ratioF;
    private boolean increment = false;

    public Candidat(int valor){
        this.valor = valor;
        this.prioritat = 0;
    }

    public boolean isIncrement() {
        return increment;
    }

    public void setIncrement(boolean increment) {
        this.increment = increment;
    }

    public void incPrioritat() {
        this.prioritat++;
    }

    public void setRatioC(int caselles){
        ratioC = caselles - prioritat;
        prioritat = 0;
    }

    public void setRatioF(int caselles){
        ratioF = caselles - prioritat;
        prioritat = 0;
    }

    public int getValor() {
        return valor;
    }

    public int getPrioritat() {
        return prioritat;
    }

    public int getRatioC() {
        return ratioC;
    }

    public int getRatioF() {
        return ratioF;
    }
}
