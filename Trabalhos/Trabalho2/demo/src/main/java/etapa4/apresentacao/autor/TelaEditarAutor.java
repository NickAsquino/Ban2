package etapa4.apresentacao.autor;

import etapa4.dados.Autor;
import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import java.awt.*;
import java.awt.event.*;

public class TelaEditarAutor extends JFrame {
    
    private JLabel lblSelected = new JLabel("Nenhum autor selecionado");
    private Integer selectedId = null;
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

        JPanel selectPanel = new JPanel(new BorderLayout(5,5));
        JButton btnSelect = new JButton("Selecionar Autor");
        selectPanel.add(lblSelected, BorderLayout.CENTER);
        selectPanel.add(btnSelect, BorderLayout.EAST);
        add(new JLabel("Autor que deseja editar:"));
        add(selectPanel);

        add(new JLabel("Novo nome do autor:"));
        add(campoNome);

        add(botaoCarregar);
        add(botaoSalvar);
        add(new JLabel(""));
        add(botaoCancelar);

        btnSelect.addActionListener(e -> {
            java.util.List<Autor> lista = gravadora.listarAutores();
            Integer id = TelaUtils.selectIdFromObjects(this, "Selecione um autor", lista);
            if (id != null) {
                selectedId = id;
                lblSelected.setText("ID: " + id);
            }
        });

        botaoCarregar.addActionListener(e -> {
            if (selectedId == null) {
                JOptionPane.showMessageDialog(this, "Selecione um autor antes de carregar.");
                return;
            }
            Autor a = gravadora.buscarAutorPorId(selectedId);
            if (a == null) {
                JOptionPane.showMessageDialog(this, "Autor não encontrado!");
            } else {
                campoNome.setText(a.getNome());
            }
        });

        botaoSalvar.addActionListener(e -> {
            try {
                if (selectedId == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um autor antes de salvar.");
                    return;
                }

                int id = selectedId.intValue();

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
