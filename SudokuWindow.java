import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * Classe SudokuWindow: permet carregar sudokus i visualitzar-los. També permet anar resolent-los i veure la solució.
 */
public class SudokuWindow extends JFrame {

    private JFormattedTextField [][] matriu = new JFormattedTextField[9][9];
    private JPanel panellCaselles = new JPanel();
    private JPanel panellOpcions = new JPanel();
    private JButton butoCarrega, butoSolucio, butoComprovar;
    private String titol = "Finestra Sudoku";

    public SudokuWindow(){
        super();
        this.setTitle(titol);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setResizable(false);
        SudokuWindow finestra = this;
        this.setLayout(new BorderLayout());

        panellCaselles.setLayout(new GridLayout(9,9));
        panellOpcions.setLayout(new GridLayout(1, 2));

        Font font1 = new Font("SansSerif", Font.BOLD, 25);
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(9);

        formatter.setAllowsInvalid(true);
        formatter.setCommitsOnValidEdit(false);

        JFormattedTextField aux;
        for (int i=0; i<9; i++)
            for (int j=0; j<9; j++) {
                matriu[i][j] = new JFormattedTextField(formatter);
                matriu[i][j].setHorizontalAlignment(JTextField.CENTER);
                matriu[i][j].setFont(font1);
                matriu[i][j].setBackground(Color.white);
                matriu[i][j].setEditable(false);
                aux = matriu[i][j];
                JFormattedTextField finalAux = aux;
                int finalI = i;
                int finalJ = j;
                matriu[i][j].getDocument().addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {

                        updateCell();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        if(!finalAux.getBackground().equals(Color.gray)) {
                            finalAux.setValue(null);
                            Eines.getSudBa().getMatriu()[finalI][finalJ].setValor(0);
                            finalAux.setBackground(Color.white);
                        }

                    }
                    public void insertUpdate(DocumentEvent e) {
                        if(!finalAux.getBackground().equals(Color.gray)) updateCell();
                    }

                    public void updateCell() {
                        finalAux.setBackground(Color.white);
                        boolean factible;
                        factible = Eines.revisarQuadrat(finalI, finalJ, Integer.parseInt(finalAux.getText())) && Eines.revisarFila(finalI, finalJ, Integer.parseInt(finalAux.getText())) && Eines.revisarColumna(finalI, finalJ, Integer.parseInt(finalAux.getText()));
                        if(factible) {
                            Eines.getSudBa().getMatriu()[finalI][finalJ].setValor(Integer.parseInt(finalAux.getText()));
                        }
                        else finalAux.setBackground(Color.RED);
                    }
                });

                panellCaselles.add(matriu[i][j]);
            }

        Eines.pintarQuadrats(finestra);

        this.butoCarrega = new JButton("Carrega Sudoku");
        this.butoSolucio = new JButton("Soluciona");
        this.butoComprovar = new JButton("Comprova");


        this.panellOpcions.add(butoCarrega);
        butoCarrega.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV Documents", "csv"));
                fileChooser.setAcceptAllFileFilterUsed(false);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        Eines.carregarSudoku(finestra, selectedFile.getAbsolutePath());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        this.panellOpcions.add(butoComprovar);
        butoComprovar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean solucio = Eines.comprovaSolucio();
                if(solucio)
                    JOptionPane.showMessageDialog(finestra, "Solució correcta!", "Molt bé", JOptionPane.PLAIN_MESSAGE);
                else JOptionPane.showMessageDialog(finestra, "Solució incorrecta! Torna a provar.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.panellOpcions.add(butoSolucio);
        butoSolucio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Eines.solucionaFinestra(finestra);
            }
        });



        this.add(panellCaselles, BorderLayout.CENTER);
        this.add(panellOpcions, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public JFormattedTextField[][] getMatriu() {
        return matriu;
    }
}
