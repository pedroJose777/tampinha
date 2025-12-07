import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaEntrada extends JFrame implements ActionListener {

    // --- Dados Fornecidos para Cálculo ---
    // Usaremos 1000 tampinhas = 1 kg (referente à água mineral, a proporção mais conservadora)
    private static final double CAPS_PER_KG = 1000.0;
    private static final double PRICE_PER_KG = 0.98;  // Valor médio do quilo (R$ 0,98)

    // --- Componentes da Interface (Para atender aos campos solicitados) ---
    private final JTextField fieldPet2L;
    private final JTextField fieldPet1L;
    private final JTextField fieldPetWater;
    private final JComboBox<String> comboPeriod;
    private final JTextField fieldAge;
    private final JButton btnCalculate;

    public TelaEntrada() {
        // --- Configuração da Janela (JFrame) ---
        setTitle("♻️ Entrada de Dados: Reciclagem de Tampinhas PET");
        setSize(550, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 1. Quantas garrafas PET de 2 litros consome
        inputPanel.add(new JLabel("Quantas garrafas PET de 2 litros consome?"));
        fieldPet2L = new JTextField("0");
        inputPanel.add(fieldPet2L);

        // 2. Quantas garrafas PET de 1 litro consome
        inputPanel.add(new JLabel("Quantas garrafas PET de 1 litro consome?"));
        fieldPet1L = new JTextField("0");
        inputPanel.add(fieldPet1L);

        // 3. Quantas garrafas PET água mineral consome
        inputPanel.add(new JLabel("Quantas garrafas PET Água Mineral consome?"));
        fieldPetWater = new JTextField("0");
        inputPanel.add(fieldPetWater);

        // 4. Informar o período (diário, semanal, mensal, etc.)
        inputPanel.add(new JLabel("Informe o Período de Consumo:"));
        String[] periods = {"Diário", "Semanal", "Mensal", "Anual"};
        comboPeriod = new JComboBox<>(periods);
        inputPanel.add(comboPeriod);
        
        // 5. Botão para digitar a idade do usuário
        inputPanel.add(new JLabel("Digite a idade do usuário (anos):"));
        fieldAge = new JTextField("0");
        inputPanel.add(fieldAge);

        // 6. Botão para calcular o valor potencial se houvesse a reciclagem em reais por ano
        btnCalculate = new JButton("Calcular Valor Potencial Anual (R$)");
        btnCalculate.addActionListener(this);
        
        // Adiciona painéis ao JFrame
        add(inputPanel, BorderLayout.CENTER);
        add(btnCalculate, BorderLayout.SOUTH);

        setVisible(true);
    }

    // --- Lógica de Ação (Implementando Processamento Esperado) ---
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCalculate) {
            calcular();
        }
    }

    private void calcular() {
        try {
            // 1. Coleta e validação dos dados
            int consumption2L = Integer.parseInt(fieldPet2L.getText());
            int consumption1L = Integer.parseInt(fieldPet1L.getText());
            int consumptionWater = Integer.parseInt(fieldPetWater.getText());
            int age = Integer.parseInt(fieldAge.getText());
            String period = (String) comboPeriod.getSelectedItem();
            
            if (consumption2L < 0 || consumption1L < 0 || consumptionWater < 0 || age < 0) {
                 throw new IllegalArgumentException("Todos os valores de entrada devem ser não-negativos.");
            }

            // 2. Determinar o Fator de Conversão Anual
            double annualFactor = switch (period) {
                case "Diário" -> 365.0;
                case "Semanal" -> 52.0;
                case "Mensal" -> 12.0;
                case "Anual" -> 1.0;
                default -> 1.0; 
            };
            
            // 3. Processamento dos Dados
            // a. Calcular o número total de tampinhas geradas pelo consumo informado.
            // Assumimos 1 garrafa = 1 tampinha
            int totalCapsPerPeriod = consumption2L + consumption1L + consumptionWater;
            double totalCapsAnnual = totalCapsPerPeriod * annualFactor;
            
            // b. Converter esse número em quilogramas (usando as proporções acima).
            double totalKgAnnual = totalCapsAnnual / CAPS_PER_KG; // Usando 1000 caps/kg

            // c. Multiplicar pelo valor médio do quilo (R$ 0,98).
            double annualEarnings = totalKgAnnual * PRICE_PER_KG;
            
            // e. Apresentar seu ganho durante quando o usuário tiver 50 anos a partir da data do dia.
            int yearsRemaining = Math.max(0, 50 - age);
            double totalFutureEarnings = annualEarnings * yearsRemaining;

            // d. Exibir o resultado em tela.
            new TelaSaida(
                (int) totalCapsAnnual,
                totalKgAnnual,
                annualEarnings,
                totalFutureEarnings,
                yearsRemaining
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, digite apenas números inteiros válidos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
             JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }
}