package etapa4.apresentacao.endereco;

import etapa4.dados.Endereco;
import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import java.awt.*;
import java.awt.event.*;

public class TelaEditarEndereco extends JFrame {

    private JLabel lblSelected = new JLabel("Nenhum endereço selecionado");
    private Integer selectedId = null;
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

        JPanel selectPanel = new JPanel(new BorderLayout(5,5));
        JButton btnSelect = new JButton("Selecionar Endereço");
        selectPanel.add(lblSelected, BorderLayout.CENTER);
        selectPanel.add(btnSelect, BorderLayout.EAST);
        add(new JLabel("Endereço que deseja editar:"));
        add(selectPanel);

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
        btnSelect.addActionListener(e -> {
            java.util.List<Endereco> lista = gravadora.listarEnderecos();
            Integer id = TelaUtils.selectId(this, "Selecione um endereço", lista, Endereco::getIdEndereco, en -> en.getIdEndereco() + " - " + en.getCidade() + " / " + en.getRua());
            if (id != null) {
                selectedId = id;
                lblSelected.setText("ID: " + id);
            }
        });

        botaoCarregar.addActionListener(e -> {
            try {
                if (selectedId == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um endereço antes de carregar.");
                    return;
                }
                int id = selectedId.intValue();
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
                if (selectedId == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um endereço antes de salvar.");
                    return;
                }

                int id = selectedId.intValue();

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
