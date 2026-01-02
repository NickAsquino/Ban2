package etapa4.apresentacao.musica;

import etapa4.dados.Musica;
import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaEditarMusica extends JFrame {

    private JTextField campoId = new JTextField();
    private JTextField campoTitulo = new JTextField();
    private JTextField campoDuracao = new JTextField();
    private JTextField campoAutores = new JTextField();

    private JButton botaoCarregar = new JButton("Carregar Música");
    private JButton botaoSalvar = new JButton("Salvar Alterações");
    private JButton botaoCancelar = new JButton("Cancelar");

    private TelaListarMusicas telaMusicas;

    public TelaEditarMusica(gravadora gravadora) {
        setTitle("Editar Música");
        setSize(600, 400);
        setLocation(50, 200);
        setLayout(new GridLayout(6, 2, 10, 10));

        telaMusicas = new TelaListarMusicas(gravadora);
        telaMusicas.setLocation(700, 200);
        telaMusicas.setVisible(true);

        add(new JLabel("ID da música que deseja editar:"));
        add(campoId);

        add(new JLabel("Novo título:"));
        add(campoTitulo);

        add(new JLabel("Duração (segundos):"));
        add(campoDuracao);

        add(new JLabel("Autores (separados por vírgula):"));
        add(campoAutores);

        add(botaoCarregar);
        add(botaoSalvar);
        add(botaoCancelar);

        botaoCarregar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                Musica m = gravadora.buscarMusicaPorId(id);
                if (m == null) {
                    JOptionPane.showMessageDialog(this, "Música não encontrada!");
                } else {
                    campoTitulo.setText(m.getTitulo());
                    campoDuracao.setText(String.valueOf(m.getDuracao()));
                    campoAutores.setText(m.getAutores());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            }
        });

        botaoSalvar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                Musica m = gravadora.buscarMusicaPorId(id);
                if (m == null) {
                    JOptionPane.showMessageDialog(this, "Música não encontrada!");
                    return;
                }

                String titulo = campoTitulo.getText().trim();
                int duracao;
                try {
                    duracao = Integer.parseInt(campoDuracao.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Duração inválida!");
                    return;
                }
                String autores = campoAutores.getText().trim();

                gravadora.atualizarMusica(id, titulo, duracao, autores);
                JOptionPane.showMessageDialog(this, "Música atualizada com sucesso!");
                telaMusicas.dispose();
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao atualizar música!");
            }
        });

        botaoCancelar.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaMusicas != null) telaMusicas.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaMusicas != null) telaMusicas.dispose();
            }
        });
    }
}
