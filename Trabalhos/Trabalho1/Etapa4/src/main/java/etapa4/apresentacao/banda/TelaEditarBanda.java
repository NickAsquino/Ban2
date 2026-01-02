package etapa4.apresentacao.banda;

import etapa4.dados.Banda;
import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaEditarBanda extends JFrame {
    private JTextField campoId = new JTextField();
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

        add(new JLabel("ID da banda que deseja editar:"));
        add(campoId);

        add(new JLabel("Novo nome da banda:"));
        add(campoNome);

        add(botaoCarregar);
        add(botaoSalvar);
        add(new JLabel(""));
        add(botaoCancelar);

        // Botão carregar
        botaoCarregar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                Banda b = gravadora.buscarBandaPorId(id);
                if (b == null) {
                    JOptionPane.showMessageDialog(this, "Banda não encontrada!");
                } else {
                    campoNome.setText(b.getNome());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            }
        });

        // Botão salvar
        botaoSalvar.addActionListener(e -> {
            try {
                int id;
                try {
                    id = Integer.parseInt(campoId.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID inválido!");
                    return;
                }

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
