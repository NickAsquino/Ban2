package etapa4.apresentacao.autor;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;

import etapa4.gerenciamento.gravadora;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaExcluirAutor extends JFrame {

    public TelaExcluirAutor(gravadora gravadora) {
        setTitle("Excluir Autor");
        setSize(400, 200);
        setLocation(100, 200);
        setLayout(new GridLayout(5, 2, 5, 5));

        TelaListarAutores telaAutores = new TelaListarAutores(gravadora);
        telaAutores.setLocation(650, 200);
        telaAutores.setVisible(true);

        JLabel lblSelected = new JLabel("Nenhum autor selecionado");
        JButton btnSelect = new JButton("Selecionar Autor");
        JButton btnExcluir = new JButton("Excluir");

        add(new JLabel("Autor a excluir:"));
        JPanel sel = new JPanel(new BorderLayout(5,5));
        sel.add(lblSelected, BorderLayout.CENTER);
        sel.add(btnSelect, BorderLayout.EAST);
        add(sel);
        add(new JLabel(""));
        add(btnExcluir);

        final Integer[] chosen = {null};
        btnSelect.addActionListener(e -> {
            java.util.List<etapa4.dados.Autor> lista = gravadora.listarAutores();
            Integer id = TelaUtils.selectIdFromObjects(this, "Selecione um autor", lista);
            if (id != null) {
                chosen[0] = id;
                lblSelected.setText("ID: " + id);
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                if (chosen[0] == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um autor antes de excluir.");
                    return;
                }
                int id = chosen[0];

                int resultado = gravadora.excluirAutor(id);

                if (resultado == 1) {
                    JOptionPane.showMessageDialog(this, "Autor excluído com sucesso!");
                    telaAutores.dispose();
                    dispose();
                } else if(resultado == -1) {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir o autor. Verifique se o ID é válido ou se ele está vinculado a uma música.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir autor.");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaAutores != null) {
                    telaAutores.dispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaAutores != null) {
                    telaAutores.dispose();
                }
            }
        });
    }
}
