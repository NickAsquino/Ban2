package etapa4.apresentacao.musica;

import javax.swing.*;
import etapa4.gerenciamento.gravadora;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaExcluirMusica extends JFrame {
    public TelaExcluirMusica(gravadora gravadora) {
        setTitle("Excluir Música");
        setSize(400, 200);
        setLocation(100, 200);
        setLayout(new GridLayout(5, 2, 5, 5));

        TelaListarMusicas telaMusicas = new TelaListarMusicas(gravadora);
        telaMusicas.setLocation(650, 200);
        telaMusicas.setVisible(true);

        JLabel lblId = new JLabel("ID da música:");
        JTextField txtId = new JTextField();
        JButton btnExcluir = new JButton("Excluir");

        add(lblId); add(txtId);
        add(new JLabel("")); add(btnExcluir);

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                if (gravadora.excluirMusica(id) == 1) {
                    JOptionPane.showMessageDialog(this, "Música excluída com sucesso!");
                    telaMusicas.dispose();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir a música. Verifique se ID é válido.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro na exclusão.");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaMusicas != null) {
                    telaMusicas.dispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaMusicas != null) {
                    telaMusicas.dispose();
                }
            }
        });
    }
}
