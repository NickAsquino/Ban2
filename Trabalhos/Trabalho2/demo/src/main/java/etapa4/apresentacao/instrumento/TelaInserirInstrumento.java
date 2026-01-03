package etapa4.apresentacao.instrumento;

import javax.swing.*;
import etapa4.dados.Instrumento;
import etapa4.gerenciamento.gravadora;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaInserirInstrumento extends JFrame {

    public TelaInserirInstrumento(gravadora gravadora) {
        setTitle("Inserir Instrumento");
        setSize(350, 200);
        setLocation(150, 250);
        setLayout(new GridLayout(3, 2, 5, 5));

        TelaListarInstrumentos telaInstrumentos = new TelaListarInstrumentos(gravadora);
        telaInstrumentos.setLocation(650, 200);
        telaInstrumentos.setVisible(true);

        JLabel lblNome = new JLabel("Nome do instrumento:");
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
                    JOptionPane.showMessageDialog(this, "Digite o nome do instrumento");
                    return;
                }

                Instrumento instrumentobusca = gravadora.buscarInstrumentoPorNome(nome);
                if (instrumentobusca != null) {
                    JOptionPane.showMessageDialog(this, "Instrumento já existe.");
                    return;
                }

                Instrumento inst = new Instrumento(nome);
                gravadora.inserirInstrumento(inst);

                JOptionPane.showMessageDialog(this, "Instrumento inserido com sucesso!");
                telaInstrumentos.dispose();
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao inserir instrumento");
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
