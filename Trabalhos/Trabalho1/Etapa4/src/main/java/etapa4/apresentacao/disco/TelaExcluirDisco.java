package etapa4.apresentacao.disco;

import javax.swing.*;
import java.awt.*;
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

        JLabel lblId = new JLabel("ID do disco:");
        JTextField txtId = new JTextField();
        JButton btnExcluir = new JButton("Excluir");

        add(lblId);
        add(txtId);
        add(new JLabel(""));
        add(btnExcluir);

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
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
