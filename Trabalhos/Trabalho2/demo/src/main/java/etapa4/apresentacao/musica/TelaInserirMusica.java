package etapa4.apresentacao.musica;

import javax.swing.*;

import etapa4.dados.Musica;
import etapa4.dados.Autor;
import etapa4.gerenciamento.gravadora;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaInserirMusica extends JFrame {
    public TelaInserirMusica(gravadora gravadora) {
        setTitle("Inserir Música");
        setSize(400, 300);
        setLocation(120, 200);
        setLayout(new GridLayout(6, 2, 5, 5));

        JLabel lblTitulo = new JLabel("Título da música:");
        JTextField txtTitulo = new JTextField();

        JLabel lblDuracao = new JLabel("Duração (em segundos):");
        JTextField txtDuracao = new JTextField();

        JLabel lblAutores = new JLabel("Autores (separados por vírgula):");
        JTextField txtAutores = new JTextField();

        JButton btnSalvar = new JButton("Salvar");

        add(lblTitulo); 
        add(txtTitulo);

        add(lblDuracao); 
        add(txtDuracao);

        add(lblAutores); 
        add(txtAutores);

        add(new JLabel("")); 
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                String titulo = txtTitulo.getText().trim();
                if (titulo.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Insira um título válido.");
                    return;
                }

                int duracao;
                try {
                    duracao = Integer.parseInt(txtDuracao.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Duração inválida.");
                    return;
                }

                String autores = txtAutores.getText().trim();
                if (autores.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Insira pelo menos um autor.");
                    return;
                }

                Musica musica = new Musica();
                musica.setTitulo(titulo);
                musica.setDuracao(duracao);

                gravadora.inserirMusica(musica);

                Musica musicaInserida = gravadora.buscarMusicaPorNome(titulo);
                String[] nomesAutores = txtAutores.getText().split(",");

                String textoAutores = txtAutores.getText().trim();
                if(textoAutores.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Insira pelo menos um autor.");
                    return;
                }
                for (String nomeAutor : nomesAutores) {
                    nomeAutor = nomeAutor.trim();
                    if (nomeAutor.isEmpty()) continue;

                    Autor a = gravadora.buscarAutorPorNome(nomeAutor);
                    if(a == null) {
                        a = new Autor(nomeAutor);
                        gravadora.inserirAutor(a);
                        a = gravadora.buscarAutorPorNome(nomeAutor);
                    }
                    gravadora.associarAutor(musicaInserida, a);
                }

                JOptionPane.showMessageDialog(this, "Música inserida com sucesso!");
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao inserir música.");
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
