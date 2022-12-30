import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Iterator;

public class SudokuAvid {
    private static final int DIMENSIO = 9;
    private static int [][] matriu = new int[DIMENSIO][DIMENSIO];

    public void solucionaAvid() {
        boolean solucio = false;
        Posicio posicio;
        int candidat;
        while(!solucio) {
            posicio = seleccionarPosicio();
            if(posicio != null){
                candidat = seleccionarCandidat(posicio);
                matriu[posicio.getFila()][posicio.getColumna()] = candidat;
                System.out.println("[" + posicio.getFila() + "] [" + posicio.getColumna() + "]");
                Eines.mostrarMatriu(matriu);
            }else{
                solucio = true;
            }
        }
    }

    private Posicio seleccionarPosicio() {
        Posicio posicio = null;
        int fLimit = 0;
        // Bucles para recorrer toda la matriz
        for(int fil = 0; fil < DIMENSIO; fil++){
            for(int col = 0; col < DIMENSIO; col++){
                int espOcupats = 0, aux;
                // Mirar que la posición actual esté a 0
                if(matriu[fil][col]==0){
                    // Recorrer fila, si todas las posiciones están ocupadas retornar
                    espOcupats = recorrerFila(fil);
                    if(espOcupats==8)
                        return new Posicio(fil, col);
                    // Recorrer columna, si todas las posiciones están ocupadas retornar
                    aux = recorrerColumna(col);
                    if(aux==8)
                        return new Posicio(fil, col);
                    else if (aux > espOcupats) {
                        espOcupats = aux;
                    }
                    // Recorrer cuadrado, si todas las posiciones están ocupadas retornar
                    aux = recorrerCuadrado(fil, col);
                    if(aux==8)
                        return new Posicio(fil, col);
                    else if (aux > espOcupats) {
                        espOcupats = aux;
                    }
                    // Si no comparar el factor más limitante
                    if(espOcupats > fLimit){
                        fLimit = espOcupats;
                        posicio = new Posicio(fil, col);
                    }
                }
            }
        }
        // Salida del bucle, retornar la posición más limitada
        return posicio;
    }

    private int recorrerCuadrado(int fil, int col) {
        int caselles = 0;
        // Definir la casilla de inicio del cuadrado
        fil = (fil / 3) * 3;
        col = (col / 3) * 3;
        for(int i = fil; i < fil + 3; i++){
            for(int j = col; j < col + 3; j++){
                if(matriu[i][j] != 0){
                    caselles++;
                }
            }
        }
        return caselles;
    }

    private int recorrerColumna(int col) {
        int caselles = 0;
        for(int fil = 0; fil < DIMENSIO; fil++){
            if(matriu[fil][col] != 0){
                caselles++;
            }
        }
        return caselles;
    }

    private int recorrerFila(int fil) {
        int caselles = 0;
        for(int col = 0; col < DIMENSIO; col++){
            if(matriu[fil][col] != 0){
                caselles++;
            }
        }
        return caselles;
    }

    private int seleccionarCandidat(Posicio posicio) {
        Candidat[] c = {new Candidat(1), new Candidat(2), new Candidat(3), new Candidat(4), new Candidat(5), new Candidat(6), new Candidat(7), new Candidat(8), new Candidat(9)};
        LinkedHashSet<Candidat> candidats = new LinkedHashSet<>(Arrays.asList(c));

        mirarFila(candidats, posicio);
        mirarColumna(candidats, posicio);
        mirarQuadrat(candidats, posicio);

        crearPrioritats(candidats, posicio);

        return majorPrioritat(candidats);
    }

    private int majorPrioritat(LinkedHashSet<Candidat> candidats) {
        Candidat millor = null;
        for(Candidat c : candidats){
            if(millor==null) millor = c;
            else if ((millor.getRatioC() > c.getRatioC())||(millor.getRatioF() > c.getRatioF())){
                millor = c;
            }
        }
        return millor.getValor();
    }

    private void crearPrioritats(LinkedHashSet<Candidat> candidats, Posicio posicio) {
        int caselles = 0;
        for(int col = 0; col < DIMENSIO; col++){
            if(col != posicio.getColumna()){
                if(matriu[posicio.getFila()][col]==0){
                    caselles++;
                    prioritatsQuadrat(candidats, new Posicio(posicio.getFila(), col), "c");
                }
            }
        }
        for(Candidat c : candidats){
            c.setRatioC(caselles);
        }
        caselles = 0;
        for(int fil = 0; fil < DIMENSIO; fil++){
            if(fil != posicio.getFila()){
                if(matriu[fil][posicio.getColumna()]==0){
                    caselles++;
                    prioritatsQuadrat(candidats, new Posicio(fil, posicio.getColumna()), "f");
                }
            }
        }
        for(Candidat c : candidats){
            c.setRatioF(caselles);
        }
    }

    private void prioritatsQuadrat(LinkedHashSet<Candidat> candidats, Posicio posicio, String seccio) {
        int fil = (posicio.getFila() / 3) * 3;
        int col = (posicio.getColumna() / 3) * 3;
        Iterator it = candidats.iterator();
        for(int i = fil; i < fil + 3; i++){
            for(int j = col; j < col + 3; j++){
                if(matriu[i][j] != 0){
                    while(it.hasNext()){
                        Candidat c = (Candidat) it.next();
                        if(c.getValor() == matriu[i][j]) {
                            c.incPrioritat();
                            c.setIncrement(true);
                        }
                    }
                    it = candidats.iterator();
                }
            }
        }
        if(seccio.equals("f")){
            for(col = 0; col < DIMENSIO; col++){
                if(matriu[posicio.getFila()][col] != 0){
                    while(it.hasNext()){
                        Candidat c = (Candidat) it.next();
                        if(c.getValor() == matriu[posicio.getFila()][col]){
                            if(!c.isIncrement()) {
                                c.incPrioritat();
                            }else {
                                c.setIncrement(false);
                            }
                        }
                    }
                    it = candidats.iterator();
                }
            }
        }else{
            for(fil = 0; fil < DIMENSIO; fil++){
                if(matriu[fil][posicio.getColumna()] != 0){
                    while(it.hasNext()){
                        Candidat c = (Candidat) it.next();
                        if(c.getValor() == matriu[fil][posicio.getColumna()] && !c.isIncrement()) {
                            c.incPrioritat();
                        }else{
                            c.setIncrement(false);
                        }
                    }
                    it = candidats.iterator();
                }
            }
        }
    }

    private void mirarQuadrat(LinkedHashSet<Candidat> candidats, Posicio posicio) {
        int quadrat = posicio.getQuadrat();
        int fila, columna;

        if (quadrat < 3) fila = 0;
        else if (quadrat < 6) fila = 3;
        else fila = 6;

        if (quadrat == 0 || quadrat == 3 || quadrat == 6) columna = 0;
        else if (quadrat == 1 || quadrat == 4 || quadrat == 7) columna = 3;
        else columna = 6;
        int aux = columna;
        int limitFiles = fila + 3, limitCols = columna + 3;
        while(fila < limitFiles) {
            columna = aux;
            while(columna < limitCols) {
                if(matriu[fila][columna]!=0) eliminarCandidat(matriu[fila][columna], candidats);
                columna++;
            }
            fila++;
        }
    }

    private void mirarColumna(LinkedHashSet<Candidat> candidats, Posicio posicio) {
        int fila = 0;
        int columna = posicio.getColumna(), actual;

        while(fila < DIMENSIO){
            actual = matriu[fila][columna];
            if(actual != 0) eliminarCandidat(actual, candidats);
            fila++;
        }
    }

    private void mirarFila(LinkedHashSet<Candidat> candidats, Posicio posicio) {
        int fila = posicio.getFila();
        int columna = 0, actual;

        while(columna < DIMENSIO){
            actual = matriu[fila][columna];
            if(actual != 0) eliminarCandidat(actual, candidats);
            columna++;

        }
    }

    private void eliminarCandidat(int actual, LinkedHashSet<Candidat> candidats) {
        Candidat escollit = null;
        for(Candidat c: candidats) {
            if(c.getValor() == actual)
               escollit = c;
        }
        candidats.remove(escollit);
    }
    private boolean hiHaSolucio() {
        for(int i = 0 ; i < DIMENSIO; i++)
            for(int j = 0 ; j < DIMENSIO; j++)
                if(matriu[i][j]==0) return false;
        return true;
    }
    public int[][] getMatriu() {
        return matriu;
    }
    public void inicialitzarMatriu(String fitxer) throws IOException {
        Eines.llegirMatriu(fitxer, matriu);
    }
}

