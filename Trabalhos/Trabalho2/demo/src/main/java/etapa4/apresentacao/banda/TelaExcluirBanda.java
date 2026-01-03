package etapa4.apresentacao.banda;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import etapa4.gerenciamento.gravadora;

public class TelaExcluirBanda extends JFrame {
    public TelaExcluirBanda(gravadora gravadora) {
        setTitle("Excluir Banda");
        setSize(400, 200);
        setLocation(50, 200);
        setLayout(new GridLayout(5, 2, 5, 5));

        TelaListarBandas telaBandas = new TelaListarBandas(gravadora);
        telaBandas.setLocation(650, 200);
        telaBandas.setVisible(true);

        JLabel lblSelected = new JLabel("Nenhuma banda selecionada");
        JButton btnSelect = new JButton("Selecionar Banda");
        JButton btnExcluir = new JButton("Excluir");

        add(new JLabel("Banda a excluir:"));
        JPanel sel = new JPanel(new BorderLayout(5,5));
        sel.add(lblSelected, BorderLayout.CENTER);
        sel.add(btnSelect, BorderLayout.EAST);
        add(sel);
        add(new JLabel("")); 
        add(btnExcluir);

        final Integer[] chosen = {null};
        btnSelect.addActionListener(e -> {
            java.util.List<etapa4.dados.Banda> lista = gravadora.listarBandas();
            Integer id = TelaUtils.selectIdFromObjects(this, "Selecione uma banda", lista);
            if (id != null) {
                chosen[0] = id;
                lblSelected.setText("ID: " + id);
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                if (chosen[0] == null) {
                    JOptionPane.showMessageDialog(this, "Selecione uma banda antes de excluir.");
                    return;
                }
                int id = chosen[0];
                if (gravadora.excluirBanda(id) == 1) {
                    JOptionPane.showMessageDialog(this, "Banda excluída com sucesso!");
                    telaBandas.dispose();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir a banda. Verifique se o ID é válido.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro na exclusão.");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaBandas != null) {
                    telaBandas.dispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaBandas != null) {
                    telaBandas.dispose();
                }
            }
        });
    }
}
