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
        // Configuração da Janela 
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

        
    }
}


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
        botaoCalcular.addActionListener(this); // O 'this' chama o actionPerformed de Jeyferson
        
        // Adiciona painéis
        add(painelEntradas, BorderLayout.CENTER);
        add(botaoCalcular, BorderLayout.SOUTH);

        setVisible(true);
    }
// ... (restante da classe)
// ... (Início do método calcularResultados, dentro do bloco try)
            // ... (Coleta e validação de Jeyferson) ...

            // --- Fator anual (Conversão de Período) --- (Miguel)
            double fatorAnual = switch (periodoSelecionado) {
                case "Diário" -> 365.0;
                case "Semanal" -> 52.0;
                case "Mensal" -> 12.0;
                case "Anual" -> 1.0;
                default -> 1.0;
            };

            // --- Cálculos --- (Miguel)
            // 3.a: Total de Tampinhas Anuais
            int totalTampinhasPeriodo = garrafas2L + garrafas1L + garrafasAgua;
            double totalTampinhasAno = totalTampinhasPeriodo * fatorAnual;

            // 3.b: Conversão para KG
            double totalKgAno = totalTampinhasAno / TAMPINHAS_POR_KG;
            
            // 3.c: Ganho Anual (R$)
            double ganhoAnual = totalKgAno * VALOR_POR_KG;

            // ... (A lógica de Projeção de Rayk continua aqui) ...

// ... (O bloco try-catch de Jeyferson se fecha ao final do método)