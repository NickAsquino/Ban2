package etapa4.apresentacao.endereco;

import javax.swing.*;

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

        JLabel lblId = new JLabel("ID do endereço:");
        JTextField txtId = new JTextField();
        JButton btnExcluir = new JButton("Excluir");

        add(lblId); add(txtId);
        add(new JLabel("")); add(btnExcluir);

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
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
