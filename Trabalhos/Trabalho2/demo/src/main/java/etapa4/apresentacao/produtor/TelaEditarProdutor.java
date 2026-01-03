package etapa4.apresentacao.produtor;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import java.awt.*;
import java.awt.event.*;
import etapa4.dados.Produtor;
import etapa4.gerenciamento.gravadora;

public class TelaEditarProdutor extends JFrame {
    private JLabel lblSelected = new JLabel("Nenhum produtor selecionado");
    private Integer selectedId = null;
    private JTextField campoNome = new JTextField();
    private JButton botaoCarregar = new JButton("Carregar Produtor");
    private JButton botaoSalvar = new JButton("Salvar Alterações");
    private JButton botaoCancelar = new JButton("Cancelar");

    private TelaListarProdutores telaProdutores;

    public TelaEditarProdutor(gravadora gravadora) {
        setTitle("Editar Produtor");
        setSize(500, 250);
        setLocation(100, 200);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Tela de listagem de produtores
        telaProdutores = new TelaListarProdutores(gravadora);
        telaProdutores.setLocation(650, 200);
        telaProdutores.setVisible(true);

        JPanel selectPanel = new JPanel(new BorderLayout(5,5));
        JButton btnSelect = new JButton("Selecionar Produtor");
        selectPanel.add(lblSelected, BorderLayout.CENTER);
        selectPanel.add(btnSelect, BorderLayout.EAST);
        add(new JLabel("Produtor que deseja editar:"));
        add(selectPanel);

        add(new JLabel("Nome do produtor:"));
        add(campoNome);

        add(botaoCarregar);
        add(botaoSalvar);
        add(botaoCancelar);

        // Botão carregar
        btnSelect.addActionListener(e -> {
            java.util.List<Produtor> lista = gravadora.listarProdutores();
            Integer id = TelaUtils.selectId(this, "Selecione um produtor", lista, Produtor::getIdProdutor, p -> p.getIdProdutor() + " | " + p.getNome());
            if (id != null) {
                selectedId = id;
                lblSelected.setText("ID: " + id);
            }
        });

        botaoCarregar.addActionListener(e -> {
            try {
                if (selectedId == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um produtor antes de carregar.");
                    return;
                }
                int id = selectedId.intValue();
                Produtor p = gravadora.buscarProdutorPorId(id);
                if (p == null) {
                    JOptionPane.showMessageDialog(this, "Produtor não encontrado!");
                } else {
                    campoNome.setText(p.getNome());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            }
        });

        // Botão salvar
        botaoSalvar.addActionListener(e -> {
            try {
                if (selectedId == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um produtor antes de salvar.");
                    return;
                }
                int id = selectedId.intValue();
                Produtor p = gravadora.buscarProdutorPorId(id);
                if (p == null) {
                    JOptionPane.showMessageDialog(this, "Produtor não encontrado!");
                    return;
                }

                String nome = campoNome.getText().trim();
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe o nome do produtor.");
                    return;
                }

                gravadora.atualizarProdutor(id, nome);
                JOptionPane.showMessageDialog(this, "Produtor atualizado com sucesso!");
                telaProdutores.dispose();
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao atualizar produtor!");
            }
        });

        botaoCancelar.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaProdutores != null) telaProdutores.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaProdutores != null) telaProdutores.dispose();
            }
        });
    }
}
