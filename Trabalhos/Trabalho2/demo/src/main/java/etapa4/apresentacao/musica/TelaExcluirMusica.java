package etapa4.apresentacao.musica;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import etapa4.dados.Musica;
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

        JLabel lblSelected = new JLabel("Nenhuma música selecionada");
        JButton btnSelect = new JButton("Selecionar Música");
        JButton btnExcluir = new JButton("Excluir");

        add(new JLabel("Música a excluir:"));
        JPanel sel = new JPanel(new BorderLayout(5,5));
        sel.add(lblSelected, BorderLayout.CENTER);
        sel.add(btnSelect, BorderLayout.EAST);
        add(sel);
        add(new JLabel("")); add(btnExcluir);

        final Integer[] chosen = {null};
        btnSelect.addActionListener(e -> {
            java.util.List<Musica> lista = gravadora.listarMusicas();
            Integer id = TelaUtils.selectId(this, "Selecione uma música", lista, Musica::getIdMusica, mm -> mm.getIdMusica() + " | " + mm.getTitulo());
            if (id != null) {
                chosen[0] = id;
                lblSelected.setText("ID: " + id);
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                if (chosen[0] == null) {
                    JOptionPane.showMessageDialog(this, "Selecione uma música antes de excluir.");
                    return;
                }
                int id = chosen[0];
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
