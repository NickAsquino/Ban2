package etapa4.apresentacao.produtor;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import etapa4.dados.Produtor;
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

        JLabel lblSelected = new JLabel("Nenhum produtor selecionado");
        JButton btnSelect = new JButton("Selecionar Produtor");
        JButton btnExcluir = new JButton("Excluir");

        add(new JLabel("Produtor a excluir:"));
        JPanel sel = new JPanel(new BorderLayout(5,5));
        sel.add(lblSelected, BorderLayout.CENTER);
        sel.add(btnSelect, BorderLayout.EAST);
        add(sel);
        add(new JLabel(""));
        add(btnExcluir);

        final Integer[] chosen = {null};
        btnSelect.addActionListener(e -> {
            java.util.List<Produtor> lista = gravadora.listarProdutores();
            Integer id = TelaUtils.selectId(this, "Selecione um produtor", lista, Produtor::getIdProdutor, p -> p.getIdProdutor() + " | " + p.getNome());
            if (id != null) {
                chosen[0] = id;
                lblSelected.setText("ID: " + id);
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                if (chosen[0] == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um produtor antes de excluir.");
                    return;
                }
                int id = chosen[0];
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
