import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TelaEntrada extends JFrame implements ActionListener {

    // --- Constantes do cálculo ---
    private static final double TAMPINHAS_POR_KG = 1000.0; // 1000 tampinhas = 1 kg
    private static final double VALOR_POR_KG = 0.98;       // R$ por quilo de tampinhas

    // --- Campos da interface ---
    private final JTextField campoGarrafas2L;
    private final JTextField campoGarrafas1L;
    private final JTextField campoGarrafasAgua;
    private final JComboBox<String> comboPeriodo;
    private final JTextField campoIdade;
    private final JButton botaoCalcular;

    public TelaEntrada() {

        setTitle("Entrada de Dados - Reciclagem de Tampinhas PET");
        setSize(550, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel painelEntradas = new JPanel(new GridLayout(6, 2, 10, 10));
        painelEntradas.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 1 — Garrafas PET 2L
        painelEntradas.add(new JLabel("Quantas garrafas PET de 2 litros consome?"));
        campoGarrafas2L = new JTextField("0");
        painelEntradas.add(campoGarrafas2L);

        // 2 — Garrafas PET 1L
        painelEntradas.add(new JLabel("Quantas garrafas PET de 1 litro consome?"));
        campoGarrafas1L = new JTextField("0");
        painelEntradas.add(campoGarrafas1L);

        // 3 — Garrafas de água mineral
        painelEntradas.add(new JLabel("Quantas garrafas de Água Mineral consome?"));
        campoGarrafasAgua = new JTextField("0");
        painelEntradas.add(campoGarrafasAgua);

        // 4 — Período de consumo
        painelEntradas.add(new JLabel("Informe o período de consumo:"));
        String[] periodos = {"Diário", "Semanal", "Mensal", "Anual"};
        comboPeriodo = new JComboBox<>(periodos);
        painelEntradas.add(comboPeriodo);
        
        // 5 — Idade do usuário
        painelEntradas.add(new JLabel("Digite a idade do usuário (anos):"));
        campoIdade = new JTextField("0");
        painelEntradas.add(campoIdade);

        // Botão Calcular
        botaoCalcular = new JButton("Calcular Valor Anual (R$)");
        botaoCalcular.addActionListener(this);
        
        add(painelEntradas, BorderLayout.CENTER);
        add(botaoCalcular, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == botaoCalcular) {
            calcularResultados();
        }
    }

    private void calcularResultados() {
        try {
            // --- Coleta e validação ---
            int garrafas2L = Integer.parseInt(campoGarrafas2L.getText());
            int garrafas1L = Integer.parseInt(campoGarrafas1L.getText());
            int garrafasAgua = Integer.parseInt(campoGarrafasAgua.getText());
            int idadeUsuario = Integer.parseInt(campoIdade.getText());
            String periodoSelecionado = (String) comboPeriodo.getSelectedItem();
            
            if (garrafas2L < 0 || garrafas1L < 0 || garrafasAgua < 0 || idadeUsuario < 0) {
                throw new IllegalArgumentException("Todos os valores devem ser positivos.");
            }

            // --- Fator anual ---
            double fatorAnual = switch (periodoSelecionado) {
                case "Diário" -> 365.0;
                case "Semanal" -> 52.0;
                case "Mensal" -> 12.0;
                case "Anual" -> 1.0;
                default -> 1.0;
            };

            // --- Cálculos ---
            int totalTampinhasPeriodo = garrafas2L + garrafas1L + garrafasAgua;
            double totalTampinhasAno = totalTampinhasPeriodo * fatorAnual;

            double totalKgAno = totalTampinhasAno / TAMPINHAS_POR_KG;
            double ganhoAnual = totalKgAno * VALOR_POR_KG;

            int anosRestantesAte50 = Math.max(0, 50 - idadeUsuario);
            double ganhoTotalFuturo = ganhoAnual * anosRestantesAte50;

            // --- Mostrar resultados ---
            new TelaSaida(
                (int) totalTampinhasAno,
                totalKgAno,
                ganhoAnual,
                ganhoTotalFuturo,
                anosRestantesAte50
            );

        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(this, 
                "Digite apenas números válidos.", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (IllegalArgumentException erro) {
            JOptionPane.showMessageDialog(this, 
                erro.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
