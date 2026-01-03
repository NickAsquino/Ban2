package etapa4.apresentacao.musica;

import etapa4.dados.Autor;
import etapa4.dados.Musica;
import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaEditarMusica extends JFrame {

    private JLabel lblSelected = new JLabel("Nenhuma música selecionada");
    private Integer selectedId = null;
    private JTextField campoTitulo = new JTextField();
    private JTextField campoDuracao = new JTextField();
    private JTextField campoAutores = new JTextField();

    private JButton botaoCarregar = new JButton("Carregar Música");
    private JButton botaoSalvar = new JButton("Salvar Alterações");
    private JButton botaoCancelar = new JButton("Cancelar");

    private TelaListarMusicas telaMusicas;

    public TelaEditarMusica(gravadora gravadora) {
        setTitle("Editar Música");
        setSize(700, 450);
        setLocation(50, 200);
        setLayout(new GridLayout(8, 2, 10, 10));

        telaMusicas = new TelaListarMusicas(gravadora);
        telaMusicas.setLocation(800, 200);
        telaMusicas.setVisible(true);

        JPanel selectPanel = new JPanel(new BorderLayout(5,5));
        JButton btnSelect = new JButton("Selecionar Música");
        selectPanel.add(lblSelected, BorderLayout.CENTER);
        selectPanel.add(btnSelect, BorderLayout.EAST);
        add(new JLabel("Música que deseja editar:"));
        add(selectPanel);

        add(new JLabel("Título:"));
        add(campoTitulo);

        add(new JLabel("Duração (segundos):"));
        add(campoDuracao);

        add(new JLabel("Autores (separados por vírgula):"));
        add(campoAutores);

        add(botaoCarregar);
        add(botaoSalvar);
        add(botaoCancelar);

        // BOTÃO SELECIONAR
        btnSelect.addActionListener(e -> {
            List<Musica> lista = gravadora.listarMusicas();
            Integer id = TelaUtils.selectId(
                    this,
                    "Selecione uma música",
                    lista,
                    Musica::getIdMusica,
                    mm -> mm.getIdMusica() + " | " + mm.getTitulo()
            );
            if (id != null) {
                selectedId = id;
                lblSelected.setText("ID: " + id);
            }
        });

        // BOTÃO CARREGAR
        botaoCarregar.addActionListener(e -> {
            try {
                if (selectedId == null) {
                    JOptionPane.showMessageDialog(this, "Selecione uma música antes de carregar.");
                    return;
                }

                Musica m = gravadora.buscarMusicaPorId(selectedId);
                if (m == null) {
                    JOptionPane.showMessageDialog(this, "Música não encontrada!");
                    return;
                }

                campoTitulo.setText(m.getTitulo());
                campoDuracao.setText(String.valueOf(m.getDuracao()));

                List<Autor> autores = gravadora.buscarAutoresDaMusica(selectedId);
                String nomes = String.join(", ", autores.stream().map(Autor::getNome).toList());
                campoAutores.setText(nomes);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao carregar música!");
            }
        });

        // BOTÃO SALVAR
        botaoSalvar.addActionListener(e -> {
            try {
                if (selectedId == null) {
                    JOptionPane.showMessageDialog(this, "Selecione uma música antes de salvar.");
                    return;
                }

                Musica m = gravadora.buscarMusicaPorId(selectedId);
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

                String textoAutores = campoAutores.getText().trim();
                if (textoAutores.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Insira pelo menos um autor.");
                    return;
                }

                gravadora.atualizarMusica(selectedId, titulo, duracao);

                gravadora.removerAutoresDaMusica(selectedId);

                String[] nomesAutores = textoAutores.split(",");

                for (String nomeA : nomesAutores) {
                    nomeA = nomeA.trim();
                    if (nomeA.isEmpty()) continue;

                    Autor a = gravadora.buscarAutorPorNome(nomeA);
                    if (a == null) {
                        a = new Autor(nomeA);
                        gravadora.inserirAutor(a);
                        a = gravadora.buscarAutorPorNome(nomeA);
                    }

                    gravadora.associarAutor(m, a);
                }

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
