package etapa4.apresentacao.banda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import etapa4.gerenciamento.gravadora;

public class TelaExcluirBanda extends JFrame {
    public TelaExcluirBanda(gravadora gravadora) {
        setTitle("Excluir Banda");
        setSize(400, 200);
        setLocation(50, 200);
        setLayout(new GridLayout(5, 2, 5, 5));

        TelaListarBandas telaBandas = new TelaListarBandas(gravadora);
        telaBandas.setLocation(650, 200);
        telaBandas.setVisible(true);

        JLabel lblId = new JLabel("ID da banda:");
        JTextField txtId = new JTextField();
        JButton btnExcluir = new JButton("Excluir");

        add(lblId); 
        add(txtId);
        add(new JLabel("")); 
        add(btnExcluir);

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                if (gravadora.excluirBanda(id) == 1) {
                    JOptionPane.showMessageDialog(this, "Banda excluída com sucesso!");
                    telaBandas.dispose();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir a banda. Verifique se o ID é válido.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro na exclusão.");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaBandas != null) {
                    telaBandas.dispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaBandas != null) {
                    telaBandas.dispose();
                }
            }
        });
    }
}
