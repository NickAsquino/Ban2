package etapa4.apresentacao.banda;

import javax.swing.*;

import etapa4.dados.Banda;
import etapa4.gerenciamento.gravadora;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaInserirBanda extends JFrame {
    public TelaInserirBanda(gravadora gravadora) {
        setTitle("Inserir Banda");
        setSize(400, 200);
        setLocation(120, 200);
        setLayout(new GridLayout(3, 2, 5, 5));

        JLabel lblNome = new JLabel("Nome da banda:");
        JTextField txtNome = new JTextField();

        JButton btnSalvar = new JButton("Salvar");

        add(lblNome);
        add(txtNome);

        add(new JLabel("")); 
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText().trim();

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O nome da banda não pode estar vazio.");
                    return;
                }

                Banda banda = new Banda(nome);
                gravadora.inserirBanda(banda);

                JOptionPane.showMessageDialog(this, "Banda inserida com sucesso!");
                dispose();  
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao inserir banda");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                dispose();
            }
        });
    }
}
