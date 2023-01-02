/**
 * Classe Candidat: serveix per a guardar el valor a cada cel·la i saber si aquest valor
 * pot canviar o no; és a dir, si el valor estava definit al principi o no.
 */
public class Candidat {
    private int valor;
    private boolean fixa;

    /**
     * Constructor de Candidat: rep el valor que guarda a la cel·la. Si
     * hi ha un valor inicial, es marca com a fixa.
     * @param valor enter que indica el valor a guardar.
     */
    public Candidat(int valor) {
        this.valor = valor;
        fixa = valor != 0;
    }

    /**
     * Getter de fixa.
     * @return true si el valor estava predefinit o fals altrament.
     */
    public boolean isFixa() {
        return fixa;
    }

    /**
     * Getter de valor.
     * @return enter amb el valor de la cel·la.
     */
    public int getValor() {
        return valor;
    }

    /**
     * Setter de valor.
     * @param valor enter que indica el valor a guardar a la cel·la.
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

}
