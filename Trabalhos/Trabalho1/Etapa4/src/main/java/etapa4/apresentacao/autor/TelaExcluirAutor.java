package etapa4.apresentacao.autor;

import javax.swing.*;

import etapa4.gerenciamento.gravadora;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaExcluirAutor extends JFrame {

    public TelaExcluirAutor(gravadora gravadora) {
        setTitle("Excluir Autor");
        setSize(400, 200);
        setLocation(100, 200);
        setLayout(new GridLayout(5, 2, 5, 5));

        TelaListarAutores telaAutores = new TelaListarAutores(gravadora);
        telaAutores.setLocation(650, 200);
        telaAutores.setVisible(true);

        JLabel lblId = new JLabel("ID do autor:");
        JTextField txtId = new JTextField();
        JButton btnExcluir = new JButton("Excluir");

        add(lblId);
        add(txtId);
        add(new JLabel(""));
        add(btnExcluir);

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());

                int resultado = gravadora.excluirAutor(id);

                if (resultado == 1) {
                    JOptionPane.showMessageDialog(this, "Autor excluído com sucesso!");
                    telaAutores.dispose();
                    dispose();
                } else if(resultado == -1) {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir o autor. Verifique se o ID é válido ou se ele está vinculado a uma música.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir autor.");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaAutores != null) {
                    telaAutores.dispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaAutores != null) {
                    telaAutores.dispose();
                }
            }
        });
    }
}
