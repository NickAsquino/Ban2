package etapa4.apresentacao.produtor;

import javax.swing.*;
import etapa4.dados.Produtor;
import etapa4.gerenciamento.gravadora;
import java.awt.*;

public class TelaInserirProdutor extends JFrame {
    public TelaInserirProdutor(gravadora gravadora) {
        setTitle("Inserir Produtor");
        setSize(350, 200);
        setLocation(150, 250);
        setLayout(new GridLayout(3, 2, 5, 5));

        JLabel lblNome = new JLabel("Nome do produtor:");
        JTextField txtNome = new JTextField();

        JButton btnSalvar = new JButton("Salvar");

        add(lblNome);
        add(txtNome);

        add(new JLabel(""));
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText().trim();

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Digite o nome do produtor.");
                    return;
                }

                Produtor p = new Produtor();
                p.setNome(nome);

                gravadora.inserirProdutor(p);

                JOptionPane.showMessageDialog(this, "Produtor inserido com sucesso!");
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao inserir produtor.");
            }
        });
    }
}
