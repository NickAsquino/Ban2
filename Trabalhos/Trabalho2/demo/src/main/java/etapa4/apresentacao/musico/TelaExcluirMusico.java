package etapa4.apresentacao.musico;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import etapa4.dados.Musico;

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

        JLabel lblSelected = new JLabel("Nenhum músico selecionado");
        JButton btnSelect = new JButton("Selecionar Músico");
        JButton btnExcluir = new JButton("Excluir");

        add(new JLabel("Músico a excluir:"));
        JPanel sel = new JPanel(new BorderLayout(5,5));
        sel.add(lblSelected, BorderLayout.CENTER);
        sel.add(btnSelect, BorderLayout.EAST);
        add(sel);
        add(new JLabel("")); add(btnExcluir);

        final Integer[] chosen = {null};
        btnSelect.addActionListener(e -> {
            java.util.List<Musico> lista = gravadora.listarMusicos();
            Integer id = TelaUtils.selectId(this, "Selecione um músico", lista, Musico::getNroRegistro, item -> item.getNroRegistro() + " | " + item.getNome());
            if (id != null) {
                chosen[0] = id;
                lblSelected.setText("ID: " + id);
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                if (chosen[0] == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um músico antes de excluir.");
                    return;
                }
                int id = chosen[0];
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
