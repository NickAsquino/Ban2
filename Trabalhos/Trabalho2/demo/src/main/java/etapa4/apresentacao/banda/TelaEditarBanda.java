package etapa4.apresentacao.banda;

import etapa4.dados.Banda;
import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import java.awt.*;
import java.awt.event.*;

public class TelaEditarBanda extends JFrame {
    private JLabel lblSelected = new JLabel("Nenhuma banda selecionada");
    private Integer selectedId = null;
    private JTextField campoNome = new JTextField();
    private JButton botaoCarregar = new JButton("Carregar Banda");
    private JButton botaoSalvar = new JButton("Salvar Alterações");
    private JButton botaoCancelar = new JButton("Cancelar");

    private TelaListarBandas telaBandas;

    public TelaEditarBanda(gravadora gravadora) {
        setTitle("Editar Banda");
        setSize(600, 300);
        setLocation(100, 200);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Abrir tela de bandas ao lado
        telaBandas = new TelaListarBandas(gravadora);
        telaBandas.setLocation(750, 200);
        telaBandas.setVisible(true);

        JPanel selectPanel = new JPanel(new BorderLayout(5,5));
        JButton btnSelect = new JButton("Selecionar Banda");
        selectPanel.add(lblSelected, BorderLayout.CENTER);
        selectPanel.add(btnSelect, BorderLayout.EAST);
        add(new JLabel("Banda que deseja editar:"));
        add(selectPanel);

        add(new JLabel("Novo nome da banda:"));
        add(campoNome);

        add(botaoCarregar);
        add(botaoSalvar);
        add(new JLabel(""));
        add(botaoCancelar);

        // Botão carregar
        btnSelect.addActionListener(e -> {
            java.util.List<Banda> lista = gravadora.listarBandas();
            Integer id = TelaUtils.selectIdFromObjects(this, "Selecione uma banda", lista);
            if (id != null) {
                selectedId = id;
                lblSelected.setText("ID: " + id);
            }
        });

        botaoCarregar.addActionListener(e -> {
            if (selectedId == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma banda antes de carregar.");
                return;
            }
            Banda b = gravadora.buscarBandaPorId(selectedId);
            if (b == null) {
                JOptionPane.showMessageDialog(this, "Banda não encontrada!");
            } else {
                campoNome.setText(b.getNome());
            }
        });

        // Botão salvar
        botaoSalvar.addActionListener(e -> {
            try {
                if (selectedId == null) {
                    JOptionPane.showMessageDialog(this, "Selecione uma banda antes de salvar.");
                    return;
                }

                int id = selectedId.intValue();

                String nome = campoNome.getText().trim();

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O nome da banda não pode estar vazio.");
                    return;
                }

                Banda b = gravadora.buscarBandaPorId(id);
                if (b == null) {
                    JOptionPane.showMessageDialog(this, "Banda não encontrada!");
                    return;
                }

                gravadora.atualizarBanda(id, nome);

                JOptionPane.showMessageDialog(this, "Banda atualizada com sucesso!");
                telaBandas.dispose();
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao atualizar banda!");
            }
        });

        botaoCancelar.addActionListener(e -> {
            telaBandas.dispose();
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                telaBandas.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                telaBandas.dispose();
            }
        });
    }
}
