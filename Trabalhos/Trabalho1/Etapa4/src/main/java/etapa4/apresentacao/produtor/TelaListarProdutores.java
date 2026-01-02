package etapa4.apresentacao.produtor;

import javax.swing.*;
import etapa4.gerenciamento.gravadora;
import etapa4.dados.Produtor;
import java.util.List;

public class TelaListarProdutores extends JFrame {
    public TelaListarProdutores(gravadora gravadora) {
        setTitle("Produtores Cadastrados");
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        add(scroll);

        List<Produtor> produtores = gravadora.listarProdutores();
        if (produtores.isEmpty()) {
            area.setText("Nenhum produtor cadastrado.");
        } else {
            for (Produtor p : produtores) {
                area.append(p.toString() + "\n");
            }
        }
    }
}
