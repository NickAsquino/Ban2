package etapa4.apresentacao.autor;

import etapa4.gerenciamento.gravadora;
import etapa4.dados.Autor;

import java.awt.*;
import javax.swing.*;

public class TelaInserirAutor extends JFrame {
    private gravadora gravadora;
    private JTextField txtNome;

    public TelaInserirAutor(gravadora gravadora) {
        this.gravadora = gravadora;

        setTitle("Incluir Autor");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        JPanel painelCampos = new JPanel(new GridLayout(1, 2, 10, 10));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        painelCampos.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelCampos.add(txtNome);
        add(painelCampos, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        Dimension tamanho = new Dimension(100, 35);
        btnSalvar.setPreferredSize(tamanho);
        btnCancelar.setPreferredSize(tamanho);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvarAutor());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void salvarAutor() {
        String nome = txtNome.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Autor a = new Autor(nome);
            gravadora.inserirAutor(a);
            JOptionPane.showMessageDialog(this, "Autor cadastrado com sucesso!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar o autor: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
