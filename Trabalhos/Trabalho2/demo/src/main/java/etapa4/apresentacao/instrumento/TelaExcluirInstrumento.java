package etapa4.apresentacao.instrumento;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import etapa4.dados.Instrumento;
import etapa4.gerenciamento.gravadora;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaExcluirInstrumento extends JFrame {
    public TelaExcluirInstrumento(gravadora gravadora) {
        setTitle("Excluir Instrumento");
        setSize(400, 200);
        setLocation(100, 200);
        setLayout(new GridLayout(5, 2, 5, 5));

        TelaListarInstrumentos telaInstrumentos = new TelaListarInstrumentos(gravadora);
        telaInstrumentos.setLocation(650, 200);
        telaInstrumentos.setVisible(true);

        JLabel lblSelected = new JLabel("Nenhum instrumento selecionado");
        JButton btnSelect = new JButton("Selecionar Instrumento");
        JButton btnExcluir = new JButton("Excluir");

        add(new JLabel("Instrumento a excluir:"));
        JPanel sel = new JPanel(new BorderLayout(5,5));
        sel.add(lblSelected, BorderLayout.CENTER);
        sel.add(btnSelect, BorderLayout.EAST);
        add(sel);
        add(new JLabel("")); 
        add(btnExcluir);

        final Integer[] chosen = {null};
        btnSelect.addActionListener(e -> {
            java.util.List<Instrumento> lista = gravadora.listarInstrumentos();
            Integer id = TelaUtils.selectId(this, "Selecione um instrumento", lista, Instrumento::getCodInterno);
            if (id != null) {
                chosen[0] = id;
                lblSelected.setText("ID: " + id);
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                if (chosen[0] == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um instrumento antes de excluir.");
                    return;
                }
                int id = chosen[0];
                if (gravadora.excluirInstrumento(id) == 1) {
                    JOptionPane.showMessageDialog(this, "Instrumento excluído com sucesso!");
                    telaInstrumentos.dispose();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir o instrumento. Verifique se o ID é válido ou se ele está vinculado a um músico.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro na exclusão.");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaInstrumentos != null) {
                    telaInstrumentos.dispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaInstrumentos != null) {
                    telaInstrumentos.dispose();
                }
            }
        });
    }
}
