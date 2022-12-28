import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Eines {
    public static void llegirMatriu(String fitxer, int [][] matriu) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(fitxer));
        String linia = lector.readLine();
        int i = 0;
        while (linia!=null) {
            String [] camps = linia.split(";");
            for(int j=0; j < camps.length; j++) {
                matriu[i][j] = Integer.parseInt(camps[j]);
            }
            linia = lector.readLine();
            i++;
        }
    }

    public static void mostrarMatriu(int [][] matriu) {
        for(int i = 0; i < matriu[0].length; i++) {
            System.out.println();
            for (int j = 0; j < matriu[0].length; j++)
                System.out.print(matriu[i][j] + "\t");
        }
    }
}
