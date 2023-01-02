import javax.swing.*;
import java.awt.*;

public class SudokuWindow extends JFrame {
    private JButton [][] matriu;
    private JPanel panellCaselles = new JPanel();
    private String titol = "Finestra Sudoku";
    private JTextField texte;
    public SudokuWindow(){
        super();
        this.setTitle(titol);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,400);

        texte = new JTextField(3);	// per definir un tamany de visualitzacio

        this.setLayout(new BorderLayout());

        panellCaselles.setLayout(new GridLayout(9,9));

       // AccioBoto accioBoto=new AccioBoto();

        for (int i=0; i<9; i++)
            for (int j=0; j<9; j++) {
                matriu[i][j]=new JButton("\n\n");
                matriu[i][j].setBackground(Color.white);
                //matriu[i][j].addActionListener(accioBoto);
                panellCaselles.add(matriu[i][j]);
            }

        this.add(panellCaselles, BorderLayout.NORTH);

        /*
        panellPregunta.setLayout(new FlowLayout());
        instruc=new JLabel("Quantes caselles tenim de color blanc?");
        panellPregunta.add(instruc);
        panellPregunta.add(texte);

        AccioDelTextField3 accioTextField = new AccioDelTextField3(this);
        texte.addActionListener(accioTextField);

        this.add(panellPregunta, BorderLayout.CENTER);

        missatge=new JTextArea("Resultat de la consulta:\n");
        missatge.setSize(this.getWidth(), 200);
        JScrollPane scroll=new JScrollPane(missatge);
        this.add(scroll, BorderLayout.SOUTH);
*/
        this.setVisible(true);
    }

}
