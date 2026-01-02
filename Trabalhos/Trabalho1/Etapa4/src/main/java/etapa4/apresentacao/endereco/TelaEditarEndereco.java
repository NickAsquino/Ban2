package etapa4.apresentacao.endereco;

import etapa4.dados.Endereco;
import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaEditarEndereco extends JFrame {

    private JTextField campoId = new JTextField();
    private JTextField campoCidade = new JTextField();
    private JTextField campoRua = new JTextField();
    private JTextField campoNumero = new JTextField();
    private JTextField campoTelefone = new JTextField();

    private JButton botaoCarregar = new JButton("Carregar Endereço");
    private JButton botaoSalvar = new JButton("Salvar Alterações");
    private JButton botaoCancelar = new JButton("Cancelar");

    private TelaListarEnderecos telaEnderecos;

    public TelaEditarEndereco(gravadora gravadora) {
        setTitle("Editar Endereço");
        setSize(500, 300);
        setLocation(100, 200);
        setLayout(new GridLayout(8,2,5,5));

        telaEnderecos = new TelaListarEnderecos(gravadora);
        telaEnderecos.setLocation(650, 200);
        telaEnderecos.setVisible(true);

        add(new JLabel("ID do endereço:"));
        add(campoId);

        add(new JLabel("Cidade:"));
        add(campoCidade);

        add(new JLabel("Rua:"));
        add(campoRua);

        add(new JLabel("Número da Casa:"));
        add(campoNumero);

        add(new JLabel("Telefone:"));
        add(campoTelefone);

        add(botaoCarregar);
        add(botaoSalvar);
        add(new JLabel(""));
        add(botaoCancelar);

        // ---- BOTÃO CARREGAR ----
        botaoCarregar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                Endereco end = gravadora.buscarEnderecoPorId(id);

                if (end == null) {
                    JOptionPane.showMessageDialog(this, "Endereço não encontrado!");
                } else {
                    campoCidade.setText(end.getCidade());
                    campoRua.setText(end.getRua());
                    campoNumero.setText(String.valueOf(end.getNroCasa()));
                    campoTelefone.setText(end.getTelefone());
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            }
        });

        // ---- BOTÃO SALVAR ----
        botaoSalvar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText());

                try {
                    id = Integer.parseInt(campoId.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID inválido!");
                    return;
                }

                Endereco end = gravadora.buscarEnderecoPorId(id);
                if (end == null) {
                    JOptionPane.showMessageDialog(this, "Endereço não encontrado!");
                    return;
                }
                String cidade = campoCidade.getText().trim();
                String rua = campoRua.getText().trim();
                int numero = Integer.parseInt(campoNumero.getText());
                String telefone = campoTelefone.getText().trim();

                gravadora.atualizarEndereco(id, cidade, rua, numero, telefone);

                JOptionPane.showMessageDialog(this, "Endereço atualizado com sucesso!");
                telaEnderecos.dispose();
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar endereço!");
            }
        });

        botaoCancelar.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaEnderecos != null) telaEnderecos.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaEnderecos != null) telaEnderecos.dispose();
            }
        });
    }
}
