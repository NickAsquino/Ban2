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

        JLabel lblEnd = new JLabel("Endereço:");
        JLabel lblSelectedEnd = new JLabel("Nenhum endereço selecionado");
        JButton btnSelectEnd = new JButton("Selecionar Endereço");
        final Integer[] chosenEnd = {null};

        JLabel lblInst = new JLabel("Instrumentos (separe por vírgulas):");
        JTextField txtInst = new JTextField();

        JButton btnSalvar = new JButton("Salvar");

        add(lblNome); 
        add(txtNome);

        JPanel sel = new JPanel(new BorderLayout(5,5)); sel.add(lblSelectedEnd, BorderLayout.CENTER); sel.add(btnSelectEnd, BorderLayout.EAST);
        add(lblEnd); add(sel);

        add(lblInst); 
        add(txtInst);

        add(new JLabel("")); 
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                Endereco end = null;
                if (chosenEnd[0] != null)
                    end = gravadora.buscarEnderecoPorId(chosenEnd[0]);
                if (end == null) {
                    JOptionPane.showMessageDialog(this, "Endereço inválido ou não selecionado.");
                    return;
                }
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

        btnSelectEnd.addActionListener(e -> {
            java.util.List<Endereco> lista = gravadora.listarEnderecos();
            Integer id = etapa4.apresentacao.TelaUtils.selectId(this, "Selecione um endereço", lista, Endereco::getIdEndereco, addr -> addr.getIdEndereco() + " | " + addr.getCidade() + " / " + addr.getRua());
            if (id != null) {
                chosenEnd[0] = id;
                lblSelectedEnd.setText("ID: " + id);
            }
        });
    }
}
