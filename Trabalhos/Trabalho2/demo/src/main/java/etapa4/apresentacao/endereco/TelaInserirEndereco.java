package etapa4.apresentacao.endereco;

import javax.swing.*;
import etapa4.dados.Endereco;
import etapa4.gerenciamento.gravadora;
import java.awt.*;

public class TelaInserirEndereco extends JFrame {
    public TelaInserirEndereco(gravadora gravadora) {
        setTitle("Inserir Endereço");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(10, 2, 5, 5));

        JLabel lblCidade = new JLabel("Cidade:");
        JTextField txtCidade = new JTextField();

        JLabel lblRua = new JLabel("Rua:");
        JTextField txtRua = new JTextField();

        JLabel lblNro = new JLabel("Número:");
        JTextField txtNro = new JTextField();

        JLabel lblTel = new JLabel("Telefone:");
        JTextField txtTel = new JTextField();

        JButton btnSalvar = new JButton("Salvar");

        add(lblCidade); add(txtCidade);
        add(lblRua); add(txtRua);
        add(lblNro); add(txtNro);
        add(lblTel); add(txtTel);
        add(new JLabel("")); add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                Endereco end = new Endereco(
                    txtCidade.getText(),
                    txtRua.getText(),
                    Integer.parseInt(txtNro.getText()),
                    txtTel.getText()
                );
                gravadora.inserirEndereco(end);
                JOptionPane.showMessageDialog(this, "Endereço inserido com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao inserir endereço.");
            }
        });
    }
}
