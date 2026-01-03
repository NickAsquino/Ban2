package etapa4.apresentacao.instrumento;

import etapa4.dados.Instrumento;
import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaEditarInstrumento extends JFrame {
    private JLabel lblSelected = new JLabel("Nenhum instrumento selecionado");
    private Integer selectedId = null;
    private JTextField campoNome = new JTextField();
    private JButton botaoCarregar = new JButton("Carregar Instrumento");
    private JButton botaoSalvar = new JButton("Salvar Alterações");
    private JButton botaoCancelar = new JButton("Cancelar");

    private TelaListarInstrumentos telaInstrumentos;

    public TelaEditarInstrumento(gravadora gravadora) {
        setTitle("Editar Instrumento");
        setSize(500, 250);
        setLocation(200, 250);
        setLayout(new GridLayout(5, 2, 10, 10));

        // abre lista dos instrumentos ao lado
        telaInstrumentos = new TelaListarInstrumentos(gravadora);
        telaInstrumentos.setLocation(750, 250);
        telaInstrumentos.setVisible(true);

        JPanel selectPanel = new JPanel(new BorderLayout(5,5));
        JButton btnSelect = new JButton("Selecionar Instrumento");
        selectPanel.add(lblSelected, BorderLayout.CENTER);
        selectPanel.add(btnSelect, BorderLayout.EAST);
        add(new JLabel("Instrumento que deseja editar:"));
        add(selectPanel);

        add(new JLabel("Novo nome do instrumento:"));
        add(campoNome);

        add(botaoCarregar);
        add(botaoSalvar);
        add(new JLabel(""));
        add(botaoCancelar);

        btnSelect.addActionListener(e -> {
            java.util.List<Instrumento> lista = gravadora.listarInstrumentos();
            Integer id = TelaUtils.selectId(this, "Selecione um instrumento", lista, Instrumento::getCodInterno);
            if (id != null) {
                selectedId = id;
                lblSelected.setText("ID: " + id);
            }
        });

        botaoCarregar.addActionListener(e -> {
            try {
                if (selectedId == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um instrumento antes de carregar.");
                    return;
                }
                int id = selectedId.intValue();
                Instrumento inst = gravadora.buscarInstrumentoPorId(id);

                if (inst == null) {
                    JOptionPane.showMessageDialog(this, "Instrumento não encontrado!");
                } else {
                    campoNome.setText(inst.getNome());
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            }
        });

        botaoSalvar.addActionListener(e -> {
            try {
                if (selectedId == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um instrumento antes de salvar.");
                    return;
                }
                int id = selectedId.intValue();
                String nome = campoNome.getText().trim();

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Digite um nome válido!");
                    return;
                }

                Instrumento inst = gravadora.buscarInstrumentoPorId(id);
                if (inst == null) {
                    JOptionPane.showMessageDialog(this, "Instrumento não encontrado!");
                    return;
                }

                gravadora.atualizarInstrumento(id, nome);

                JOptionPane.showMessageDialog(this, "Instrumento atualizado com sucesso!");
                telaInstrumentos.dispose();
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar instrumento!");
                ex.printStackTrace();
            }
        });

        botaoCancelar.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaInstrumentos != null) telaInstrumentos.dispose();
            }
            @Override
            public void windowClosed(WindowEvent e) {
                if (telaInstrumentos != null) telaInstrumentos.dispose();
            }
        });
    }
}
