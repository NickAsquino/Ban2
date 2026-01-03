package etapa4.apresentacao.endereco;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import etapa4.dados.Endereco;

import etapa4.gerenciamento.gravadora;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaExcluirEndereco extends JFrame {
    public TelaExcluirEndereco(gravadora gravadora) {
        setTitle("Excluir Endereço");
        setSize(400, 200);
        setLocation(120, 200);
        setLayout(new GridLayout(5, 2, 5, 5));

        TelaListarEnderecos telaEnderecos = new TelaListarEnderecos(gravadora);
        telaEnderecos.setLocation(650, 200);
        telaEnderecos.setVisible(true);

        JLabel lblSelected = new JLabel("Nenhum endereço selecionado");
        JButton btnSelect = new JButton("Selecionar Endereço");
        JButton btnExcluir = new JButton("Excluir");

        add(new JLabel("Endereço a excluir:"));
        JPanel sel = new JPanel(new BorderLayout(5,5));
        sel.add(lblSelected, BorderLayout.CENTER);
        sel.add(btnSelect, BorderLayout.EAST);
        add(sel);
        add(new JLabel("")); add(btnExcluir);

        final Integer[] chosen = {null};
        btnSelect.addActionListener(e -> {
            java.util.List<Endereco> lista = gravadora.listarEnderecos();
            Integer id = TelaUtils.selectId(this, "Selecione um endereço", lista, Endereco::getIdEndereco, en -> en.getIdEndereco() + " - " + en.getCidade() + " / " + en.getRua());
            if (id != null) {
                chosen[0] = id;
                lblSelected.setText("ID: " + id);
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                if (chosen[0] == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um endereço antes de excluir.");
                    return;
                }
                int id = chosen[0];
                if (gravadora.excluirEndereco(id) == 1) {
                    JOptionPane.showMessageDialog(this, "Endereço excluído com sucesso!");
                    telaEnderecos.dispose();                
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir, verifique se o id está correto.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro na exclusão.");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaEnderecos != null) {
                    telaEnderecos.dispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaEnderecos != null) {
                    telaEnderecos.dispose();
                }
            }
        });
    }
}
