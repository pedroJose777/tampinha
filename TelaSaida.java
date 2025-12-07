import java.awt.*;
import javax.swing.*;

public class TelaSaida extends JFrame {

    // A constante PRICE_PER_KG é usada aqui para referência no JLabel
    private static final double PRICE_PER_KG = 0.98;

    public TelaSaida(int totalCaps, double totalKg, double ganhoAno, double ganhoVida, int anosRestantes) {

        setSize(450, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 5, 5)); 
        
        
        
        add(new JLabel(String.format(" Peso Total das Tampinhas Geradas (Anual): %.2f kg", totalKg)));
        
        add(new JLabel(String.format(" Valor Potencial Anual (R$ %.2f/kg): R$ %.2f", PRICE_PER_KG, ganhoAno)));
        
        String futureLabel = (anosRestantes > 0) 
            ? String.format(" Projeção de Ganho Total até os 50 anos (%d anos): R$ %.2f", anosRestantes, ganhoVida)
            : " Projeção: Você já tem 50 anos ou mais. Ganho futuro R$ 0,00.";
        add(new JLabel(futureLabel));

        add(new JLabel("Tampinhas Totais Geradas por Ano: " + formatCaps + " unidades"));


        setVisible(true);
    }
}