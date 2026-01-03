package etapa4.apresentacao.disco;

import javax.swing.*;
import etapa4.apresentacao.TelaUtils;
import java.awt.*;
import etapa4.dados.Disco;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import etapa4.gerenciamento.gravadora;

public class TelaExcluirDisco extends JFrame {

    public TelaExcluirDisco(gravadora gravadora) {
        setTitle("Excluir Disco");
        setSize(400, 200);
        setLocation(100, 200);
        setLayout(new GridLayout(5, 2, 5, 5));

        TelaListarDiscos telaDiscos = new TelaListarDiscos(gravadora);
        telaDiscos.setLocation(650, 200);
        telaDiscos.setVisible(true);

        JLabel lblSelected = new JLabel("Nenhum disco selecionado");
        JButton btnSelect = new JButton("Selecionar Disco");
        JButton btnExcluir = new JButton("Excluir");

        add(new JLabel("Disco a excluir:"));
        JPanel sel = new JPanel(new BorderLayout(5,5));
        sel.add(lblSelected, BorderLayout.CENTER);
        sel.add(btnSelect, BorderLayout.EAST);
        add(sel);
        add(new JLabel(""));
        add(btnExcluir);

        final Integer[] chosen = {null};
        btnSelect.addActionListener(e -> {
            java.util.List<Disco> lista = gravadora.listarDiscos();
            Integer id = TelaUtils.selectId(this, "Selecione um disco", lista, Disco::getIdentificador, d -> d.getIdentificador() + " | " + d.getTitulo());
            if (id != null) {
                chosen[0] = id;
                lblSelected.setText("ID: " + id);
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                if (chosen[0] == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um disco antes de excluir.");
                    return;
                }
                int id = chosen[0];
                if (gravadora.excluirDisco(id) == 1) {
                    JOptionPane.showMessageDialog(this, "Disco excluído com sucesso!");
                    telaDiscos.dispose();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir o disco. Verifique se o ID é válido.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro na exclusão.");
                ex.printStackTrace();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaDiscos != null) telaDiscos.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaDiscos != null) telaDiscos.dispose();
            }
        });
    }
}
