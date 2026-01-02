package etapa4.apresentacao.instrumento;

import etapa4.dados.Instrumento;
import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaEditarInstrumento extends JFrame {
    private JTextField campoId = new JTextField();
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

        add(new JLabel("ID do instrumento que deseja editar:"));
        add(campoId);

        add(new JLabel("Novo nome do instrumento:"));
        add(campoNome);

        add(botaoCarregar);
        add(botaoSalvar);
        add(new JLabel(""));
        add(botaoCancelar);

        botaoCarregar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText());
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
                int id = Integer.parseInt(campoId.getText());
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
