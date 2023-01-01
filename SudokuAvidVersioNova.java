import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class SudokuAvidVersioNova {
    private static final int DIMENSIO = 9;
    private static LinkedHashSet<Integer> [][] matriu = new LinkedHashSet[DIMENSIO][DIMENSIO];

    // Primero tengo que inicializar la matriz y determinar las opciones
    public void inicialitzarMatriu(String fitxer) throws IOException {
        Eines.llegirMatriu(fitxer, matriu);
        colocarCandidats();
    }

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

    public LinkedHashSet<Integer> [][] getMatriu() {
        return matriu;
    }

    public void solucionaAvid() {
        boolean solucio = false;
        boolean canvi;
        while(!solucio) {
            canvi = true;
            while(canvi) {
                // Determinar candidatos de cada celda
                canvi = actualitzarCandidats();
                Eines.mostrarMatriu(matriu);
            }
            // Si no funciona, preemptive sets rows, columns y sub board
            buscarPreemptiveSets();
            Eines.mostrarMatriu(matriu);
            solucio = hiHaSolucio();
        }
    }

    private void buscarPreemptiveSets() {
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
    }

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
            actualitzarQuadrat(posicions, fila, columna);
            return true;
        }
    }

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
            for(Posicio p:posicions)
                System.out.println(p.getFila() + " " + p.getColumna());
            actualitzarColumna(posicions);
            return true;
        }
    }

    private boolean comparar(LinkedHashSet<Integer> posicio, LinkedHashSet<Integer> candidats) {
        boolean mateix = true;
        for(Integer i:posicio){
            mateix = mateix && candidats.contains(i);
        }
        return mateix;
    }

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
            actualitzarFila(posicions);
            return true;
        }
    }

    private void actualitzarQuadrat(Posicio[] posicions, int fil, int columna) {
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
                    Iterator it= matriu[posicions[0].getFila()][posicions[0].getColumna()].iterator();
                    while(it.hasNext()){
                        matriu[fila][col].remove(it.next());
                    }
                }
            }
        }
    }

    private void actualitzarColumna(Posicio[] posicions) {
        int col = posicions[0].getColumna();
        for(int fila=0; fila<DIMENSIO; fila++){
            boolean mateix=false;
            for(Posicio p:posicions){
                if(p.comparar(fila,col)) {
                    mateix = true;
                    break;
                }
            }
            if(!mateix && matriu[fila][col].size()!=1){
                Iterator it= matriu[posicions[0].getFila()][posicions[0].getColumna()].iterator();
                while(it.hasNext()){
                    matriu[fila][col].remove(it.next());
                }
            }
        }
    }

    private void actualitzarFila(Posicio[] posicions) {
        int fila = posicions[0].getFila();
        for(int col=0; col<DIMENSIO; col++){
            boolean mateix=false;
            for(Posicio p:posicions){
                if(p.comparar(fila,col)) {
                    mateix = true;
                    break;
                }
            }
            if(!mateix && matriu[fila][col].size()!=1){
                Iterator it= matriu[posicions[0].getFila()][posicions[0].getColumna()].iterator();
                while(it.hasNext()){
                    matriu[fila][col].remove(it.next());
                }
            }
        }
    }

    private boolean escollirCelles() {
        return true;
    }

    private boolean hiHaSolucio() {
        for(int i = 0 ; i < DIMENSIO; i++)
            for(int j = 0 ; j < DIMENSIO; j++)
                if(matriu[i][j].size()!=1) return false;
        return true;
    }

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

    private boolean mirarQuadrat(LinkedHashSet<Integer> candidats, Posicio posicio) {
        int fila = (posicio.getFila() / 3) * 3;
        int columna = (posicio.getColumna() / 3) * 3;

        for(int f = fila; f< fila+3; f++) {
            for (int c = columna; c < columna + 3; c++) {
                if(matriu[f][c].size() == 1 && !(f== posicio.getFila() && c== posicio.getColumna())){
                    Iterator it = matriu[f][c].iterator();
                    candidats.remove(it.next());
                }
            }
        }
        return candidats.size() == 1;
    }

    private boolean mirarColumna(LinkedHashSet<Integer> candidats, Posicio posicio) {
        int fila = 0, columna = posicio.getColumna();

        while(fila < DIMENSIO){
            if(matriu[fila][columna].size() == 1 && fila!=posicio.getFila()){
                Iterator it = matriu[fila][columna].iterator();
                candidats.remove(it.next());
            }
            fila++;
        }
        return candidats.size() == 1;
    }

    private boolean mirarFila(LinkedHashSet<Integer> candidats, Posicio posicio) {
        int fila = posicio.getFila(), columna = 0;

        while(columna < DIMENSIO){
            if(matriu[fila][columna].size() == 1 && columna!=posicio.getColumna()){
                Iterator it = matriu[fila][columna].iterator();
                candidats.remove(it.next());
            }
            columna++;
        }
        return candidats.size() == 1;
    }

    private void eliminarCandidat(int actual, LinkedHashSet<Integer> candidats) {
        Integer escollit = null;
        for(Integer c: candidats) {
            if(c == actual)
                escollit = c;
        }
        candidats.remove(escollit);
    }

}
