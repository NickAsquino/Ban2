package etapa4.apresentacao.musico;

import javax.swing.*;

import etapa4.apresentacao.endereco.TelaListarEnderecos;
import etapa4.dados.*;
import etapa4.gerenciamento.gravadora;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaInserirMusico extends JFrame {
    public TelaInserirMusico(gravadora gravadora) {
        setTitle("Inserir Músico");
        setSize(400, 400);
        setLocation(120, 200);
        setLayout(new GridLayout(10, 2, 5, 5));

        TelaListarEnderecos telaEnderecos = new TelaListarEnderecos(gravadora);
        telaEnderecos.setLocation(650, 200);
        telaEnderecos.setVisible(true);

        JLabel lblNome = new JLabel("Nome do músico:");
        JTextField txtNome = new JTextField();

        JLabel lblEnd = new JLabel("ID do endereço:");
        JTextField txtEnd = new JTextField();

        JLabel lblInst = new JLabel("Instrumentos (separe por vírgulas):");
        JTextField txtInst = new JTextField();

        JButton btnSalvar = new JButton("Salvar");

        add(lblNome); 
        add(txtNome);

        add(lblEnd); 
        add(txtEnd);

        add(lblInst); 
        add(txtInst);

        add(new JLabel("")); 
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                Endereco end = new Endereco();
                end.setIdEndereco(Integer.parseInt(txtEnd.getText()));
                Musico musico = new Musico(txtNome.getText(), end);

                gravadora.inserirMusico(musico);

                Musico musicoInserido = gravadora.buscarMusicoPorNome(txtNome.getText());

                String[] nomesInstrumentos = txtInst.getText().split(",");

                String textoInstrumentos = txtInst.getText().trim();
                if(textoInstrumentos.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Insira pelo menos um instrumento.");
                    return;
                } 
                for (String nomeInst : nomesInstrumentos) {
                    nomeInst = nomeInst.trim();
                    if (nomeInst.isEmpty()) continue;

                    Instrumento i = gravadora.buscarInstrumentoPorNome(nomeInst);
                    if (i == null) {
                        i = new Instrumento(nomeInst);
                        gravadora.inserirInstrumento(i);
                        i = gravadora.buscarInstrumentoPorNome(nomeInst);
                    }
                    gravadora.associarInstrumento(musicoInserido, i);
                }
                JOptionPane.showMessageDialog(this, "Músico inserido com sucesso!");
                telaEnderecos.dispose();
                dispose();  
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao inserir músico, verifique se o endereço é válido");
            }
        });


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (telaEnderecos != null) {
                    telaEnderecos.dispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (telaEnderecos != null) {
                    telaEnderecos.dispose();
                }
            }
        });
    }
}
