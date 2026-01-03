package etapa4.apresentacao.autor;

import javax.swing.*;
import etapa4.gerenciamento.gravadora;
import etapa4.dados.Autor;
import java.util.List;

public class TelaListarAutores extends JFrame {
    
    public TelaListarAutores(gravadora gravadora) {
        setTitle("Autores Cadastrados");
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        add(scroll);

        List<Autor> autores = gravadora.listarAutores();

        if (autores.isEmpty()) {
            area.setText("Nenhum autor cadastrado.");
        } else {
            for (Autor a : autores) {
                area.append(a.toString() + "\n");
            }
        }
    }
}
