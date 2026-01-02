package etapa4.apresentacao.musico;

import etapa4.dados.Endereco;
import etapa4.dados.Instrumento;
import etapa4.dados.Musico;
import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaEditarMusico extends JFrame {
    private JTextField campoId = new JTextField();
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

        add(new JLabel("ID do músico que deseja editar:"));
        add(campoId);

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

        botaoCarregar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText());
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
                int id;
                try {
                    id = Integer.parseInt(campoId.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID inválido!");
                    return;
                }
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
