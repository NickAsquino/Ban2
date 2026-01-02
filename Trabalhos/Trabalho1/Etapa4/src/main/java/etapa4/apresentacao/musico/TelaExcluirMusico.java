package etapa4.apresentacao.musico;

import javax.swing.*;

import etapa4.gerenciamento.gravadora;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaExcluirMusico extends JFrame {
    public TelaExcluirMusico(gravadora gravadora) {
        setTitle("Excluir Músico");
        setSize(400, 200);
        setLocation(100, 200);
        setLayout(new GridLayout(5, 2, 5, 5));

        TelaListarMusicos telaMusicos = new TelaListarMusicos(gravadora);
        telaMusicos.setLocation(650, 200);
        telaMusicos.setVisible(true);

        JLabel lblId = new JLabel("ID do músico:");
        JTextField txtId = new JTextField();
        JButton btnExcluir = new JButton("Excluir");

        add(lblId); add(txtId);
        add(new JLabel("")); add(btnExcluir);

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                if (gravadora.excluirMusico(id) == 1) {
                    JOptionPane.showMessageDialog(this, "Músico excluído com sucesso!");
                    telaMusicos.dispose();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir o músico. Verifique se ID é válido.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro na exclusão.");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaMusicos != null) {
                    telaMusicos.dispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaMusicos != null) {
                    telaMusicos.dispose();
                }
            }
        });
    }
}
