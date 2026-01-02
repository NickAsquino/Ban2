package etapa4.apresentacao.autor;

import etapa4.dados.Autor;
import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaEditarAutor extends JFrame {
    
    private JTextField campoId = new JTextField();
    private JTextField campoNome = new JTextField();

    private JButton botaoCarregar = new JButton("Carregar Autor");
    private JButton botaoSalvar = new JButton("Salvar Alterações");
    private JButton botaoCancelar = new JButton("Cancelar");

    private TelaListarAutores telaAutores;

    public TelaEditarAutor(gravadora gravadora) {
        setTitle("Editar Autor");
        setSize(800, 500);
        setLocation(50, 200);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Abre lista ao lado
        telaAutores = new TelaListarAutores(gravadora);
        telaAutores.setLocation(850, 200);
        telaAutores.setVisible(true);

        add(new JLabel("ID do autor que deseja editar:"));
        add(campoId);

        add(new JLabel("Novo nome do autor:"));
        add(campoNome);

        add(botaoCarregar);
        add(botaoSalvar);
        add(new JLabel(""));
        add(botaoCancelar);

        botaoCarregar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                Autor a = gravadora.buscarAutorPorId(id);
                
                if (a == null) {
                    JOptionPane.showMessageDialog(this, "Autor não encontrado!");
                } else {
                    campoNome.setText(a.getNome());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            }
        });

        botaoSalvar.addActionListener(e -> {
            try {
                int id;
                try {
                    id = Integer.parseInt(campoId.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID inválido!");
                    return;
                }

                Autor a = gravadora.buscarAutorPorId(id);
                if (a == null) {
                    JOptionPane.showMessageDialog(this, "Autor não encontrado!");
                    return;
                }

                String nome = campoNome.getText().trim();
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Preencha o nome!");
                    return;
                }

                gravadora.atualizarAutor(id, nome);

                JOptionPane.showMessageDialog(this, "Autor atualizado com sucesso!");

                telaAutores.dispose();
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao atualizar autor!");
            }
        });

        botaoCancelar.addActionListener(e -> {
            if (telaAutores != null) telaAutores.dispose();
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaAutores != null) telaAutores.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaAutores != null) telaAutores.dispose();
            }
        });
    }
}
