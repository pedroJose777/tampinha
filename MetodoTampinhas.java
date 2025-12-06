import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class MetodoTampinhas extends JFrame {

    // constantes do enunciado
    private static final double KG_PER_500_TAMPAS = 1.0 / 500.0; // 500 tampas = 1 kg
    private static final double KG_PER_1000_MINERAL = 1.0 / 1000.0; // 1000 tampas mineral = 1 kg
    private static final double VALOR_POR_KG = 0.98; // R$ 0,98 por kg

    // componentes da UI
    private JTextField tf2L;
    private JTextField tf1L;
    private JTextField tfMineral;
    private JComboBox<String> cbPeriodo;
    private JTextField tfIdade;
    private JTextArea taResultado;

    public MetodoTampinhas() {
        super("Reciclagem de Tampinhas PET - Calculadora");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel p = new JPanel(new BorderLayout(8,8));
        p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // inputs
        JPanel inputs = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4,4,4,4);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0; g.gridy = 0;

        inputs.add(new JLabel("Quantas garrafas PET de 2 litros consome (número):"), g);
        g.gridx = 1;
        tf2L = new JTextField("0", 10);
        inputs.add(tf2L, g);

        g.gridx = 0; g.gridy++;
        inputs.add(new JLabel("Quantas garrafas PET de 1 litro consome (número):"), g);
        g.gridx = 1;
        tf1L = new JTextField("0", 10);
        inputs.add(tf1L, g);

        g.gridx = 0; g.gridy++;
        inputs.add(new JLabel("Quantas garrafas PET (água mineral) consome (número):"), g);
        g.gridx = 1;
        tfMineral = new JTextField("0", 10);
        inputs.add(tfMineral, g);

        g.gridx = 0; g.gridy++;
        inputs.add(new JLabel("Período dos números acima:"), g);
        g.gridx = 1;
        cbPeriodo = new JComboBox<>(new String[] {"Diário", "Semanal", "Mensal", "Anual"});
        inputs.add(cbPeriodo, g);

        g.gridx = 0; g.gridy++;
        inputs.add(new JLabel("Sua idade atual (anos):"), g);
        g.gridx = 1;
        tfIdade = new JTextField("0", 10);
        inputs.add(tfIdade, g);

        p.add(inputs, BorderLayout.NORTH);

        // botões
        JPanel botoes = new JPanel();
        JButton btCalcular = new JButton("Calcular (mostrar valores anuais)");
        JButton btLimpar = new JButton("Limpar");
        botoes.add(btCalcular);
        botoes.add(btLimpar);
        p.add(botoes, BorderLayout.CENTER);

        // área de resultados
        taResultado = new JTextArea();
        taResultado.setEditable(false);
        taResultado.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane sp = new JScrollPane(taResultado);
        sp.setPreferredSize(new Dimension(580, 220));
        p.add(sp, BorderLayout.SOUTH);

        add(p);

        // ações
        btCalcular.addActionListener(e -> calcular());
        btLimpar.addActionListener(e -> {
            tf2L.setText("0");
            tf1L.setText("0");
            tfMineral.setText("0");
            cbPeriodo.setSelectedIndex(0);
            tfIdade.setText("0");
            taResultado.setText("");
        });
    }

    private void calcular() {
        // ler e validar entradas
        int qtd2L, qtd1L, qtdMineral, idade;
        try {
            qtd2L = parseNonNegativeInt(tf2L.getText().trim(), "2 litros");
            qtd1L = parseNonNegativeInt(tf1L.getText().trim(), "1 litro");
            qtdMineral = parseNonNegativeInt(tfMineral.getText().trim(), "água mineral");
            idade = parseNonNegativeInt(tfIdade.getText().trim(), "idade");
        } catch (NumberFormatException ex) {
            // parseNonNegativeInt já mostra o erro
            return;
        }

        // converter para valores anuais conforme período
        String periodo = (String) cbPeriodo.getSelectedItem();
        double fator = periodoParaFator(periodo);

        double anual2L = qtd2L * fator;
        double anual1L = qtd1L * fator;
        double anualMineral = qtdMineral * fator;

        // total de tampinhas por ano
        double totalTampasAnual = anual2L + anual1L + anualMineral;

        // converter para kg usando as proporções (1L e 2L juntos -> 500 tampas = 1kg, mineral -> 1000 tampas = 1kg)
        double kgFrom1and2 = (anual1L + anual2L) * KG_PER_500_TAMPAS;
        double kgFromMineral = anualMineral * KG_PER_1000_MINERAL;
        double totalKgAnual = kgFrom1and2 + kgFromMineral;

        // valor anual
        double valorAnual = totalKgAnual * VALOR_POR_KG;

        // cálculo até os 50 anos
        int anosAte50 = Math.max(0, 50 - idade);
        double ganhoAte50 = valorAnual * anosAte50;
        LocalDate hoje = LocalDate.now();
        LocalDate aniversario50 = hoje.plusYears(anosAte50);

        // montar resultado formatado (com precisão adequada)
        StringBuilder sb = new StringBuilder();
        sb.append("===== RESULTADO =====\n\n");
        sb.append(String.format("Período informado: %s (fator anual = %.3f)\n\n", periodo, fator));
        sb.append(String.format("Tampinhas por ano (2L): %.0f\n", anual2L));
        sb.append(String.format("Tampinhas por ano (1L): %.0f\n", anual1L));
        sb.append(String.format("Tampinhas por ano (água mineral): %.0f\n\n", anualMineral));
        sb.append(String.format("Total de tampinhas por ano: %.0f tampas\n\n", totalTampasAnual));

        sb.append("Conversão para peso (aproximação):\n");
        sb.append(String.format(" - KG de 1L/2L (500 tampas = 1 kg): %.4f kg\n", kgFrom1and2));
        sb.append(String.format(" - KG de água mineral (1000 tampas = 1 kg): %.4f kg\n", kgFromMineral));
        sb.append(String.format(" -> Peso total aproximado por ano: %.4f kg\n\n", totalKgAnual));

        sb.append(String.format("Valor médio por kg: R$ %.2f\n", VALOR_POR_KG));
        sb.append(String.format("Valor anual aproximado se reciclasse TODAS as tampinhas: R$ %.2f\n\n", valorAnual));

        if (anosAte50 > 0) {
            sb.append(String.format("Você tem %d anos (hoje). Faltam %d anos para completar 50.\n", idade, anosAte50));
            sb.append(String.format("Se mantiver este mesmo consumo/reciclagem até completar 50 anos (ano %d),\n", aniversario50.getYear()));
            sb.append(String.format("ganharia acumulado (apenas somando anos): R$ %.2f\n\n", ganhoAte50));
        } else if (idade == 50) {
            sb.append("Você já tem 50 anos hoje. Ganho anual (para este ano):\n");
            sb.append(String.format("R$ %.2f\n\n", valorAnual));
        } else { // idade > 50
            sb.append(String.format("Você já tem %d anos (>50). O cálculo de \"até 50 anos\" não se aplica.\n", idade));
            sb.append(String.format("Ganho anual aproximado (se reciclasse todas as tampinhas este ano): R$ %.2f\n\n", valorAnual));
        }

        sb.append("Observações:\n");
        sb.append("- Valores são aproximações com base nas proporções fornecidas.\n");
        sb.append("- Não há consideração de variação de preço do quilo ao longo dos anos.\n");
        sb.append("- Os números devem ser intepretados como estimativas simples.\n");

        taResultado.setText(sb.toString());
    }

    private double periodoParaFator(String periodo) {
        switch (periodo) {
            case "Diário": return 365.0;
            case "Semanal": return 52.0;
            case "Mensal": return 12.0;
            case "Anual": return 1.0;
            default: return 1.0;
        }
    }

    private int parseNonNegativeInt(String s, String fieldName) throws NumberFormatException {
        if (s.isEmpty()) s = "0";
        int v;
        try {
            v = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido em : " + fieldName + ". Digite um número inteiro não-negativo.", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new NumberFormatException();
        }
        if (v < 0) {
            JOptionPane.showMessageDialog(this, "Valor negativo em : " + fieldName + ". Use 0 ou número positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new NumberFormatException();
        }
        return v;
    }

    public static void main(String[] args) {
        // usar look & feel padrão (não customizamos)
        SwingUtilities.invokeLater(() -> {
            MetodoTampinhas app = new MetodoTampinhas();
            app.setVisible(true);
        });
    }
}
