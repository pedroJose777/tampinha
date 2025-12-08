import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TelaEntrada extends JFrame implements ActionListener {

    // --- Constantes do cálculo (Definidas por Miguel, declaradas por Kaua) ---
    private static final double TAMPINHAS_POR_KG = 1000.0;
    private static final double VALOR_POR_KG = 0.98;

    // --- Campos da interface (Declaração de todos) ---
    private final JTextField campoGarrafas2L;
    private final JTextField campoGarrafas1L;
    private final JTextField campoGarrafasAgua;
    private final JComboBox<String> comboPeriodo;
    private final JTextField campoIdade;
    private final JButton botaoCalcular;

    public TelaEntrada() {
        // Configuração da Janela (Kaua)
        setTitle("Entrada de Dados - Reciclagem de Tampinhas PET");
        setSize(550, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel painelEntradas = new JPanel(new GridLayout(6, 2, 10, 10));
        painelEntradas.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 1 — Garrafas PET 2L (Kaua)
        painelEntradas.add(new JLabel("Quantas garrafas PET de 2 litros consome?"));
        campoGarrafas2L = new JTextField("0");
        painelEntradas.add(campoGarrafas2L);

        // 2 — Garrafas PET 1L (Kaua)
        painelEntradas.add(new JLabel("Quantas garrafas PET de 1 litro consome?"));
        campoGarrafas1L = new JTextField("0");
        painelEntradas.add(campoGarrafas1L);

        // *** CONTINUA NA PARTE DO EDUARDO ***
    }
    // ... métodos omitidos ...
}