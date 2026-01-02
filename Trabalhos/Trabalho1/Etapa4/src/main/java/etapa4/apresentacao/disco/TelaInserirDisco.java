package etapa4.apresentacao.disco;

import etapa4.dados.*;
import etapa4.gerenciamento.gravadora;
import etapa4.apresentacao.produtor.TelaListarProdutores;
import etapa4.apresentacao.banda.TelaListarBandas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelaInserirDisco extends JFrame {

    public TelaInserirDisco(gravadora gravadora) {
        setTitle("Inserir Disco");
        setSize(450, 400);
        setLocation(120, 200);
        setLayout(new GridLayout(9, 2, 5, 5));

        TelaListarProdutores telaProdutores = new TelaListarProdutores(gravadora);
        telaProdutores.setLocation(650, 5);
        telaProdutores.setVisible(true);

        TelaListarBandas telaBandas = new TelaListarBandas(gravadora);
        telaBandas.setLocation(650, 400);
        telaBandas.setVisible(true);

        JLabel lblTitulo = new JLabel("Título do disco:");
        JTextField txtTitulo = new JTextField();

        JLabel lblFormato = new JLabel("Formato:");
        JTextField txtFormato = new JTextField();

        JLabel lblData = new JLabel("Data (yyyy-mm-dd) (opcional):");
        JTextField txtData = new JTextField();

        JLabel lblProdutor = new JLabel("ID do produtor:");
        JTextField txtProdutor = new JTextField();

        JLabel lblBanda = new JLabel("ID da banda (opcional):");
        JTextField txtBanda = new JTextField();

        JLabel lblMusicas = new JLabel("Músicas (separe por vírgulas):");
        JTextField txtMusicas = new JTextField();

        JButton btnSalvar = new JButton("Salvar");

        add(lblTitulo); add(txtTitulo);
        add(lblFormato); add(txtFormato);
        add(lblData); add(txtData);
        add(lblProdutor); add(txtProdutor);
        add(lblBanda); add(txtBanda);
        add(lblMusicas); add(txtMusicas);
        add(new JLabel("")); add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                String titulo = txtTitulo.getText().trim();
                String formato = txtFormato.getText().trim();

                Date data = null;
                String txt = txtData.getText().trim();
                if (!txt.isEmpty()) {
                    data = Date.valueOf(txt);
                }

                String idProdutorStr = txtProdutor.getText().trim();
                if (idProdutorStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Insira um produtor!");
                    return;
                }
                Produtor produtor = gravadora.buscarProdutorPorId(Integer.parseInt(idProdutorStr));
                if (produtor == null) {
                    JOptionPane.showMessageDialog(this, "Produtor não encontrado!");
                    return;
                }
                Banda banda = null;
                
                if (!txtBanda.getText().trim().isEmpty()) {
                    banda = gravadora.buscarBandaPorId(Integer.parseInt(txtBanda.getText()));
                    if (banda == null) {
                        JOptionPane.showMessageDialog(this, "Banda não encontrada!");
                        return;
                    }
                }

                if (titulo.isEmpty() || formato.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Título e formato são obrigatórios!");
                    return;
                }

                Disco disco = new Disco(formato, data, titulo, produtor, banda, new ArrayList<>());

                gravadora.inserirDisco(disco);
                Disco discoInserido = gravadora.buscarDiscoPorTitulo(titulo);

                String textoMusicas = txtMusicas.getText().trim();
                if (!textoMusicas.isEmpty()) {
                    String[] nomesMusicas = textoMusicas.split(",");

                    for (String nomeMusica : nomesMusicas) {
                        nomeMusica = nomeMusica.trim();
                        if (nomeMusica.isEmpty()) continue;

                        Musica m = gravadora.buscarMusicaPorNome(nomeMusica);

                        if (m == null) {
                            // Criar música básica sem autores nem duração (se quiser permitir input depois)
                            m = new Musica();
                            m.setTitulo(nomeMusica);
                            m.setDuracao(0);
                            m.setAutores("Desconhecido");

                            gravadora.inserirMusica(m);
                            m = gravadora.buscarMusicaPorNome(nomeMusica);
                        }

                        gravadora.associarMusica(discoInserido, m);
                    }
                }
                JOptionPane.showMessageDialog(this, "Disco inserido com sucesso!");

                telaProdutores.dispose();
                telaBandas.dispose();
                dispose();
             } catch (SQLException ex) {
                String msg = ex.getMessage();

                if (msg.contains("formato")) {
                    JOptionPane.showMessageDialog(this, "Formato inválido! Use CD, MC ou K7.");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao inserir disco: " + msg);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage());
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                telaProdutores.dispose();
                telaBandas.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                telaProdutores.dispose();
                telaBandas.dispose();
            }
        });
    }
}
