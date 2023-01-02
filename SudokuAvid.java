import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Classe SudokuAvid: classe que resol el sudoku de manera àvida.
 */
public class SudokuAvid {
    private static final int DIMENSIO = 9;
    private static LinkedHashSet<Integer> [][] matriu = new LinkedHashSet[DIMENSIO][DIMENSIO];

    /**
     * Constructor de la matriu per SudokuAvid.
     * @param fitxer String amb el nom del fitxer per carregar el sudoku.
     * @throws IOException si hi ha problemes amb el fitxer.
     */
    public void inicialitzarMatriu(String fitxer) throws IOException {
        Eines.llegirMatriu(fitxer, matriu);
        colocarCandidats();
    }

    /**
     * Mètode que omple la llista de cada cel·la buida amb tots els possibles
     * candidats de l'1 al 9.
     */
    private void colocarCandidats() {
        for(int i=0; i<DIMENSIO; i++){
            for(int j=0; j<DIMENSIO; j++){
                if(matriu[i][j].iterator().next() == 0){
                    matriu[i][j].clear();
                    for(int k=1; k<=DIMENSIO; k++)
                        matriu[i][j].add(k);
                }
            }
        }
    }

    /**
     * Mètode que tracta de resoldre el sudoku seguint l'estratègia àvida.
     * @return cert si s'ha pogut resoldre, fals altrament.
     */
    public boolean solucionaAvid() {
        boolean solucio = false;
        boolean canvi=true;
        while(!solucio && canvi) {
            canvi = true;
            while(canvi) {
                // Determinar candidats de cada cel·la.
                canvi = actualitzarCandidats();
            }
            // Si no funciona, utilitzar estratègìa dels preemptive sets per files, columnes i quadrats.
            canvi = buscarPreemptiveSets();
            solucio = hiHaSolucio();
        }
        return solucio;
    }

    /**
     * Mètode que busca els preemptive sets per files, columnes i quadrats.
     * @return cert si hi ha hagut canvis, fals altrament.
     */
    private boolean buscarPreemptiveSets() {
        boolean canvi =false;

        for(int conjunt=2; conjunt<DIMENSIO && !canvi; conjunt++) {
            for (int i = 0; i < DIMENSIO && !canvi; i++) {
                for (int j = 0; j < DIMENSIO && !canvi; j++) {
                    if (matriu[i][j].size() == conjunt) {
                        canvi = preemptiveFila(matriu[i][j], i, conjunt);
                        if (!canvi) {
                            canvi = preemptiveColumna(matriu[i][j], j, conjunt);
                            if (!canvi) {
                                canvi = preemptiveQuadrat(matriu[i][j], i, j, conjunt);
                            }
                        }
                    }
                }
            }
        }
        return canvi;
    }

    /**
     * Mètode que busca els preemptive sets per un quadrat concret.
     * @param candidats conjunt amb els candidats de la cel·la.
     * @param fila enter amb la fila de la cel·la.
     * @param columna enter amb la columna de la cel·la.
     * @param conjunt enter amb el nombre de candidats iguals (per exemple,
     *                si conjunt == 2, buscarà dues posicions que tinguin dos
     *                candidats iguals, aleshores hi haurà un preemptive set).
     * @return cert si hi ha preemptive set, fals altrament.
     */
    private boolean preemptiveQuadrat(LinkedHashSet<Integer> candidats, int fila, int columna, int conjunt) {
        Posicio[] posicions = new Posicio[conjunt];
        fila=(fila/3)*3;
        columna = (columna/3)*3;
        int cont=0;
        int caselles = 0;
        for(int fil=fila; fil < fila+3 && cont < posicions.length; fil++){
            for(int col=columna; col<columna+3 && cont < posicions.length; col++) {
                if (matriu[fil][col].size() != 1) {
                    caselles++;
                    if (matriu[fil][col].size() == candidats.size() && comparar(matriu[fil][col], candidats)) {
                        posicions[cont] = new Posicio(fil, col);
                        cont++;
                    }
                }
            }
        }
        posicions = caselles == conjunt ? null:cont == posicions.length ? posicions:null;
        if(posicions == null)
            return false;
        else{
            return actualitzarQuadrat(posicions, fila, columna);
        }
    }

    /**
     * Mètode que busca els preemptive sets per una columna concreta.
     * @param candidats conjunt amb els candidats de la cel·la.
     * @param columna enter amb la columna de la cel·la.
     * @param conjunt enter amb el nombre de candidats iguals (per exemple,
     *                si conjunt == 2, buscarà dues posicions que tinguin dos
     *                candidats iguals, aleshores hi haurà un preemptive set).
     * @return cert si hi ha preemptive set, fals altrament.
     */
    private boolean preemptiveColumna(LinkedHashSet<Integer> candidats, int columna, int conjunt) {
        Posicio[] posicions = new Posicio[conjunt];
        int cont=0;
        int caselles = 0;
        for(int fila=0; fila < DIMENSIO && cont < posicions.length; fila++){
            if(matriu[fila][columna].size()!=1){
                caselles++;
                if(matriu[fila][columna].size()==candidats.size() && comparar(matriu[fila][columna], candidats)){
                    posicions[cont] = new Posicio(fila, columna);
                    cont++;
                }
            }
        }
        posicions = caselles == conjunt ? null:cont == posicions.length ? posicions:null;
        if(posicions == null)
            return false;
        else{
            return actualitzarColumna(posicions);
        }
    }


    /**
     * Mètode que busca els preemptive sets per una fila concreta.
     * @param candidats conjunt amb els candidats de la cel·la.
     * @param fila enter amb la fila de la cel·la.
     * @param conjunt enter amb el nombre de candidats iguals (per exemple,
     *                si conjunt == 2, buscarà dues posicions que tinguin dos
     *                candidats iguals, aleshores hi haurà un preemptive set).
     * @return cert si hi ha preemptive set, fals altrament.
     */
    private boolean preemptiveFila(LinkedHashSet<Integer> candidats, int fila, int conjunt) {
        Posicio[] posicions = new Posicio[conjunt];
        int cont=0;
        int caselles = 0;
        for(int columna=0; columna < DIMENSIO && cont < posicions.length; columna++){
            if(matriu[fila][columna].size()!=1){
                caselles++;
                if(matriu[fila][columna].size()==candidats.size() && comparar(matriu[fila][columna], candidats)){
                    posicions[cont] = new Posicio(fila, columna);
                    cont++;
                }
            }
        }
        posicions = caselles == conjunt ? null:cont == posicions.length ? posicions:null;
        if(posicions == null)
            return false;
        else{
             return actualitzarFila(posicions);
        }
    }

    /**
     * Mètode que, un cop detectat un preemptive set, actualitza els valors de la resta
     * de posicions no involucrades dins el quadrat.
     * @param posicions taula de Posicio amb les posicions dels preemptive sets.
     * @param fil enter que indica la fila actual.
     * @param columna enter que indica la columna actual.
     * @return cert si s'ha eliminat algun candidat, fals altrament.
     */
    private boolean actualitzarQuadrat(Posicio[] posicions, int fil, int columna) {
        boolean canvi = false;
        for(int fila=fil; fila<fil+3; fila++){
            for(int col=columna; col<columna+3; col++){
                boolean mateix=false;
                for(Posicio p:posicions){
                    if(p.comparar(fila,col)) {
                        mateix = true;
                        break;
                    }
                }
                if(!mateix && matriu[fila][col].size()!=1){
                    Iterator<Integer> it= matriu[posicions[0].getFila()][posicions[0].getColumna()].iterator();
                    int mida = matriu[fila][col].size();
                    while(it.hasNext()){
                        matriu[fila][col].remove(it.next());
                    }
                    canvi = canvi || mida!=matriu[fila][col].size();
                }
            }
        }
        return canvi;
    }

    /**
     * Mètode que, un cop detectat un preemptive set, actualitza els valors de la resta
     * de posicions no involucrades dins la columna.
     * @param posicions taula de Posicio amb les posicions dels preemptive sets.
     * @return cert si s'ha eliminat algun candidat, fals altrament.
     */
    private boolean actualitzarColumna(Posicio[] posicions) {
        int col = posicions[0].getColumna();
        boolean canvi = false;
        for(int fila=0; fila<DIMENSIO; fila++){
            boolean mateix=false;
            for(Posicio p:posicions){
                if(p.comparar(fila,col)) {
                    mateix = true;
                    break;
                }
            }
            if(!mateix && matriu[fila][col].size()!=1){
                Iterator<Integer> it= matriu[posicions[0].getFila()][posicions[0].getColumna()].iterator();
                int mida = matriu[fila][col].size();
                while(it.hasNext()){
                    matriu[fila][col].remove(it.next());
                }
                canvi = canvi || mida != matriu[fila][col].size();
            }
        }
        return canvi;
    }

    /**
     * Mètode que, un cop detectat un preemptive set, actualitza els valors de la resta
     * de posicions no involucrades dins la fila.
     * @param posicions taula de Posicio amb les posicions dels preemptive sets.
     * @return cert si s'ha eliminat algun candidat, fals altrament.
     */
    private boolean actualitzarFila(Posicio[] posicions) {
        int fila = posicions[0].getFila();
        boolean canvi = false;
        for(int col=0; col<DIMENSIO; col++){
            boolean mateix=false;
            for(Posicio p:posicions){
                if(p.comparar(fila,col)) {
                    mateix = true;
                    break;
                }
            }
            if(!mateix && matriu[fila][col].size()!=1){
                Iterator<Integer> it= matriu[posicions[0].getFila()][posicions[0].getColumna()].iterator();
                int mida = matriu[fila][col].size();
                while(it.hasNext()){
                    matriu[fila][col].remove(it.next());
                }
                canvi = canvi || mida != matriu[fila][col].size();
            }
        }
        return canvi;
    }

    /**
     * Mètode que recorre la matriu eliminant els candidats no vàlids de cada cel·la.
     * @return cert si hi ha hagut canvis, fals altrament.
     */
    private boolean actualitzarCandidats() {
        Posicio posicio;
        boolean canvi = false;
        for(int i=0; i<DIMENSIO; i++){
            for(int j=0; j<DIMENSIO; j++){
                if(matriu[i][j].size()>1){
                    posicio = new Posicio(i, j);
                    canvi = mirarFila(matriu[i][j], posicio) || canvi;
                    canvi = mirarColumna(matriu[i][j], posicio) || canvi;
                    canvi = mirarQuadrat(matriu[i][j], posicio) || canvi;
                }
            }
        }
        return canvi;
    }

    /**
     * Mètode que actualitza els candidats d'una cel·la respecte al seu quadrat.
     * @param candidats conjunt d'enters amb els candidats.
     * @param posicio Posicio a estudiar.
     * @return cert si la cel·la ja té només un valor adjudicat, fals altrament.
     */
    private boolean mirarQuadrat(LinkedHashSet<Integer> candidats, Posicio posicio) {
        int fila = (posicio.getFila() / 3) * 3;
        int columna = (posicio.getColumna() / 3) * 3;

        for(int f = fila; f< fila+3; f++) {
            for (int c = columna; c < columna + 3; c++) {
                if(matriu[f][c].size() == 1 && !(f== posicio.getFila() && c== posicio.getColumna())){
                    Iterator<Integer> it = matriu[f][c].iterator();
                    candidats.remove(it.next());
                }
            }
        }
        return candidats.size() == 1;
    }

    /**
     * Mètode que actualitza els candidats d'una cel·la respecte a la seva columna.
     * @param candidats conjunt d'enters amb els candidats.
     * @param posicio Posicio a estudiar.
     * @return cert si la cel·la ja té només un valor adjudicat, fals altrament.
     */
    private boolean mirarColumna(LinkedHashSet<Integer> candidats, Posicio posicio) {
        int fila = 0, columna = posicio.getColumna();

        while(fila < DIMENSIO){
            if(matriu[fila][columna].size() == 1 && fila!=posicio.getFila()){
                Iterator<Integer> it = matriu[fila][columna].iterator();
                candidats.remove(it.next());
            }
            fila++;
        }
        return candidats.size() == 1;
    }

    /**
     * Mètode que actualitza els candidats d'una cel·la respecte a la seva fila.
     * @param candidats conjunt d'enters amb els candidats.
     * @param posicio Posicio a estudiar.
     * @return cert si la cel·la ja té només un valor adjudicat, fals altrament.
     */
    private boolean mirarFila(LinkedHashSet<Integer> candidats, Posicio posicio) {
        int fila = posicio.getFila(), columna = 0;

        while(columna < DIMENSIO){
            if(matriu[fila][columna].size() == 1 && columna!=posicio.getColumna()){
                Iterator<Integer> it = matriu[fila][columna].iterator();
                candidats.remove(it.next());
            }
            columna++;
        }
        return candidats.size() == 1;
    }

    /**
     * Mètode que comprova si ja hi ha una solució.
     * @return true si hi ha solució, fals altrament.
     */
    private boolean hiHaSolucio() {
        for(int i = 0 ; i < DIMENSIO; i++)
            for(int j = 0 ; j < DIMENSIO; j++)
                if(matriu[i][j].size()!=1) return false;
        return true;
    }

    /**
     * Getter de matriu.
     * @return matriu de conjunt d'enters amb el sudoku.
     */
    public LinkedHashSet<Integer> [][] getMatriu() {
        return matriu;
    }

    /**
     * Mètode que compara dues llistes de candidats.
     * @param posicio conjunt d'enters amb els candidats possibles.
     * @param candidats conjunt d'enters amb els candidats possibles.
     * @return cert si els conjunts són iguals, fals altrament.
     */
    private boolean comparar(LinkedHashSet<Integer> posicio, LinkedHashSet<Integer> candidats) {
        boolean mateix = true;
        for(Integer i:posicio){
            mateix = mateix && candidats.contains(i);
        }
        return mateix;
    }
}
