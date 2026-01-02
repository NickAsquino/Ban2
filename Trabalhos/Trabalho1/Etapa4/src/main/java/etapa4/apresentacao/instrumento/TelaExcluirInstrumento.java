package etapa4.apresentacao.instrumento;

import javax.swing.*;
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

        JLabel lblId = new JLabel("ID do instrumento:");
        JTextField txtId = new JTextField();
        JButton btnExcluir = new JButton("Excluir");

        add(lblId); 
        add(txtId);
        add(new JLabel("")); 
        add(btnExcluir);

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
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
