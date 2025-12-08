import java.awt.*;
import javax.swing.*;

public class TelaSaida extends JFrame {

    // Valor pago por kg de tampinhas
    private static final double VALOR_POR_KG = 0.98;

    public TelaSaida(int totalTampinhasAno, double totalKgAno, double ganhoAnual, double ganhoFuturo, int anosRestantes) {

        setTitle("Resultado dos Cálculos");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 5, 5)); 
        
        // Peso total em kg
        add(new JLabel(String.format("Peso total das tampinhas por ano: %.2f kg", totalKgAno)));
        
        // Valor anual
        add(new JLabel(String.format("Valor potencial anual (R$ %.2f/kg): R$ %.2f", VALOR_POR_KG, ganhoAnual)));
        
        // Projeção futura até os 50 anos
        String textoProjecao = (anosRestantes > 0) 
            ? String.format("Ganho total até os 50 anos (%d anos restantes): R$ %.2f", anosRestantes, ganhoFuturo)
            : "Projeção: Você já tem 50 anos ou mais. Ganho futuro: R$ 0,00.";

        add(new JLabel(textoProjecao));

        // Total de tampinhas geradas por ano
        add(new JLabel("Tampinhas totais geradas por ano: " + totalTampinhasAno + " unidades"));

        // Botão fechar
        JButton botaoFechar = new JButton("Fechar");
        botaoFechar.addActionListener(e -> dispose());
        add(botaoFechar);

        setVisible(true);
    }
}
