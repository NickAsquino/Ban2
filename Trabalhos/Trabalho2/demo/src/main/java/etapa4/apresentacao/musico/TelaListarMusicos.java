package etapa4.apresentacao.musico;

import javax.swing.*;
import etapa4.gerenciamento.gravadora;
import etapa4.dados.Musico;
import java.util.List;

public class TelaListarMusicos extends JFrame {
    public TelaListarMusicos(gravadora gravadora) {
        setTitle("Músicos Cadastrados");
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        add(scroll);

        List<Musico> musicos = gravadora.listarMusicos();
        if (musicos.isEmpty()) {
            area.setText("Nenhum músico cadastrado.");
        } else {
            for (Musico m : musicos) {
                area.append(m.toString() + "\n");
            }
        }
    }
}
