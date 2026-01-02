package etapa4.apresentacao.banda;

import javax.swing.*;
import etapa4.gerenciamento.gravadora;
import etapa4.dados.Banda;
import java.util.List;

public class TelaListarBandas extends JFrame {
    public TelaListarBandas(gravadora gravadora) {
        setTitle("Bandas Cadastradas");
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        add(scroll);

        List<Banda> bandas = gravadora.listarBandas();
        if (bandas.isEmpty()) {
            area.setText("Nenhuma banda cadastrada.");
        } else {
            for (Banda b : bandas) {
                area.append(b.toString() + "\n");
            }
        }
    }
}
