package etapa4.apresentacao.musica;

import javax.swing.*;
import etapa4.gerenciamento.gravadora;
import etapa4.dados.Musica;
import java.util.List;

public class TelaListarMusicas extends JFrame {
    public TelaListarMusicas(gravadora gravadora) {
        setTitle("Músicas Cadastradas");
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        add(scroll);

        List<Musica> musicas = gravadora.listarMusicas();
        if (musicas.isEmpty()) {
            area.setText("Nenhuma música cadastrada.");
        } else {
            for (Musica m : musicas) {
                area.append(m.toString() + "\n");
            }
        }
    }
}
