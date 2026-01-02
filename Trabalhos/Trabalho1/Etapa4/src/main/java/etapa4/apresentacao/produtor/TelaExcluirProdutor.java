package etapa4.apresentacao.produtor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import etapa4.gerenciamento.gravadora;

public class TelaExcluirProdutor extends JFrame {
    public TelaExcluirProdutor(gravadora gravadora) {
        setTitle("Excluir Produtor");
        setSize(400, 200);
        setLocation(100, 200);
        setLayout(new GridLayout(5, 2, 5, 5));

        TelaListarProdutores telaProdutores = new TelaListarProdutores(gravadora);
        telaProdutores.setLocation(650, 200);
        telaProdutores.setVisible(true);

        JLabel lblId = new JLabel("ID do produtor:");
        JTextField txtId = new JTextField();
        JButton btnExcluir = new JButton("Excluir");

        add(lblId);
        add(txtId);
        add(new JLabel(""));
        add(btnExcluir);

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                if (gravadora.excluirProdutor(id) == 1) {
                    JOptionPane.showMessageDialog(this, "Produtor excluído com sucesso!");
                    telaProdutores.dispose();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir o produtor. Verifique se o ID é válido.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro na exclusão.");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaProdutores != null) {
                    telaProdutores.dispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaProdutores != null) {
                    telaProdutores.dispose();
                }
            }
        });
    }
}
