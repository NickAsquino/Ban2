package etapa4.apresentacao.musico;

import etapa4.dados.Endereco;
import etapa4.dados.Instrumento;
import etapa4.dados.Musico;
import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaEditarMusico extends JFrame {
    private JLabel lblSelected = new JLabel("Nenhum músico selecionado");
    private Integer selectedId = null;
    private JTextField campoNome = new JTextField();
    private JLabel lblNovo = new JLabel("Insira os novos dados do músico:");
    private JComboBox<Endereco> comboEndereco = new JComboBox<>();
    private JTextField campoInstrumentos = new JTextField();
    private JButton botaoCarregar = new JButton("Carregar Músico");
    private JButton botaoSalvar = new JButton("Salvar Alterações");
    private JButton botaoCancelar = new JButton("Cancelar");

    private TelaListarMusicos telaMusicos;

    public TelaEditarMusico(gravadora gravadora) {
        setTitle("Editar Músico");
        setSize(800, 500);
        setLocation(50, 200);
        setLayout(new GridLayout(9, 2, 10, 10));

        telaMusicos = new TelaListarMusicos(gravadora);
        telaMusicos.setLocation(850, 200);
        telaMusicos.setVisible(true);

        JPanel selectPanel = new JPanel(new BorderLayout(5,5));
        JButton btnSelect = new JButton("Selecionar Músico");
        selectPanel.add(lblSelected, BorderLayout.CENTER);
        selectPanel.add(btnSelect, BorderLayout.EAST);
        add(new JLabel("Músico que deseja editar:"));
        add(selectPanel);

        add(lblNovo);
        add(new JLabel(""));

        add(new JLabel("Nome:"));
        add(campoNome);

        add(new JLabel("Endereço:"));
        add(comboEndereco);

        add(new JLabel("Instrumentos (separados por vírgula):"));
        add(campoInstrumentos);

        add(botaoCarregar);
        add(botaoSalvar);
        add(botaoCancelar);

        List<Endereco> enderecos = gravadora.listarEnderecos();
        for (Endereco e : enderecos) {
            comboEndereco.addItem(e);
        }

        btnSelect.addActionListener(e -> {
            java.util.List<Musico> lista = gravadora.listarMusicos();
            Integer id = TelaUtils.selectId(this, "Selecione um músico", lista, Musico::getNroRegistro, item -> item.getNroRegistro() + " | " + item.getNome());
            if (id != null) {
                selectedId = id;
                lblSelected.setText("ID: " + id);
            }
        });

        botaoCarregar.addActionListener(e -> {
            try {
                if (selectedId == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um músico antes de carregar.");
                    return;
                }
                int id = selectedId.intValue();
                Musico m = gravadora.buscarMusicoPorId(id);
                if (m == null) {
                    JOptionPane.showMessageDialog(this, "Músico não encontrado!");
                } else {
                    campoNome.setText(m.getNome());
                    comboEndereco.setSelectedItem(m.getEndereco());
                    campoInstrumentos.setText(String.join(", ",
                            gravadora.buscarInstrumentosDoMusico(id)
                                    .stream()
                                    .map(Instrumento::getNome)
                                    .toList()));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            }
        });

        botaoSalvar.addActionListener(e -> {
            try {
                if (selectedId == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um músico antes de salvar.");
                    return;
                }

                int id = selectedId.intValue();
                Musico m = gravadora.buscarMusicoPorId(id);
                if (m == null) {
                    JOptionPane.showMessageDialog(this, "Músico não encontrado!");
                    return;
                }
                String nome = campoNome.getText().trim();
                Endereco end = (Endereco) comboEndereco.getSelectedItem();

                gravadora.atualizarMusico(id, nome, end);

                gravadora.removerInstrumentosDoMusico(id);

                String[] nomesInstrumentos = campoInstrumentos.getText().split(",");

                String textoInstrumentos = campoInstrumentos.getText().trim();
                if(textoInstrumentos.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Insira pelo menos um instrumento.");
                    return;
                } 
                for (String nomeInst : nomesInstrumentos) {
                    nomeInst = nomeInst.trim();
                    if (nomeInst.isEmpty()) continue;

                    Instrumento i = gravadora.buscarInstrumentoPorNome(nomeInst);
                    if (i == null) {
                        i = new Instrumento(nomeInst);
                        gravadora.inserirInstrumento(i);
                        i = gravadora.buscarInstrumentoPorNome(nomeInst);
                    }
                    gravadora.associarInstrumento(m, i);
                }
                JOptionPane.showMessageDialog(this, "Músico atualizado com sucesso!");
                telaMusicos.dispose();
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao atualizar músico!");
            }
        });


        botaoCancelar.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaMusicos != null) telaMusicos.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaMusicos != null) telaMusicos.dispose();
            }
        });
    }
}
