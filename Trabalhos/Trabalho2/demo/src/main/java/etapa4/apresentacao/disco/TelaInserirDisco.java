package etapa4.apresentacao.disco;

import etapa4.dados.*;
import etapa4.gerenciamento.gravadora;
import etapa4.apresentacao.produtor.TelaListarProdutores;
import etapa4.apresentacao.musica.TelaListarMusicas;

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
        setSize(800, 500);
        setLocation(50, 200);
        setLayout(new GridLayout(9, 2, 5, 5));

        TelaListarProdutores telaProdutores = new TelaListarProdutores(gravadora);
        telaProdutores.setLocation(850, 5);
        telaProdutores.setVisible(true);

        TelaListarMusicas telaMusicas = new TelaListarMusicas(gravadora);
        telaMusicas.setLocation(850, 400);
        telaMusicas.setVisible(true);

        JLabel lblTitulo = new JLabel("Título do disco:");
        JTextField txtTitulo = new JTextField();

        JLabel lblFormato = new JLabel("Formato:");
        JTextField txtFormato = new JTextField();

        JLabel lblData = new JLabel("Data (yyyy-mm-dd) (opcional):");
        JTextField txtData = new JTextField();

        JLabel lblProdutor = new JLabel("Produtor:");
        JLabel lblSelectedProdutor = new JLabel("Nenhum produtor selecionado");
        JButton btnSelectProdutor = new JButton("Selecionar Produtor");
        final Integer[] chosenProdutor = {null};

        JLabel lblBanda = new JLabel("Banda (opcional):");
        JLabel lblSelectedBanda = new JLabel("Nenhuma banda selecionada");
        JButton btnSelectBanda = new JButton("Selecionar Banda");
        final Integer[] chosenBanda = {null};

        JLabel lblMusicas = new JLabel("Músicas (separe por vírgulas):");
        JTextField txtMusicas = new JTextField();

        JButton btnSalvar = new JButton("Salvar");

        add(lblTitulo); add(txtTitulo);
        add(lblFormato); add(txtFormato);
        add(lblData); add(txtData);
        JPanel selProd = new JPanel(new BorderLayout(5,5)); selProd.add(lblSelectedProdutor, BorderLayout.CENTER); selProd.add(btnSelectProdutor, BorderLayout.EAST);
        add(lblProdutor); add(selProd);
        JPanel selBanda = new JPanel(new BorderLayout(5,5)); selBanda.add(lblSelectedBanda, BorderLayout.CENTER); selBanda.add(btnSelectBanda, BorderLayout.EAST);
        add(lblBanda); add(selBanda);
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

                if (chosenProdutor[0] == null) {
                    JOptionPane.showMessageDialog(this, "Insira um produtor!");
                    return;
                }
                Produtor produtor = gravadora.buscarProdutorPorId(chosenProdutor[0]);
                if (produtor == null) {
                    JOptionPane.showMessageDialog(this, "Produtor não encontrado!");
                    return;
                }
                Banda banda = null;
                
                if (chosenBanda[0] != null) {
                    banda = gravadora.buscarBandaPorId(chosenBanda[0]);
                    if (banda == null) {
                        JOptionPane.showMessageDialog(this, "Banda não encontrada!");
                        return;
                    }
                }

                if (titulo.isEmpty() || formato.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Título e formato são obrigatórios!");
                    return;
                } else if (!formato.equals("CD") && !formato.equals("MC") && !formato.equals("K7") && !formato.equals("Vinil")) {
                    JOptionPane.showMessageDialog(this, "Formato inválido! Use CD, MC, K7 ou Vinil.");
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
                            m.setAutores(new ArrayList<>());

                            gravadora.inserirMusica(m);
                            m = gravadora.buscarMusicaPorNome(nomeMusica);
                        }

                        gravadora.associarMusica(discoInserido, m);
                    }
                }
                JOptionPane.showMessageDialog(this, "Disco inserido com sucesso!");

                telaProdutores.dispose();
                telaMusicas.dispose();
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
                telaMusicas.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                telaProdutores.dispose();
                telaMusicas.dispose();
            }
        });

        // selection buttons
        btnSelectProdutor.addActionListener(e -> {
            java.util.List<Produtor> lista = gravadora.listarProdutores();
            Integer id = etapa4.apresentacao.TelaUtils.selectId(this, "Selecione um produtor", lista, Produtor::getIdProdutor, p -> p.getIdProdutor() + " | " + p.getNome());
            if (id != null) {
                chosenProdutor[0] = id;
                lblSelectedProdutor.setText("ID: " + id);
            }
        });

        btnSelectBanda.addActionListener(e -> {
            java.util.List<Banda> lista = gravadora.listarBandas();
            Integer id = etapa4.apresentacao.TelaUtils.selectId(this, "Selecione uma banda", lista, Banda::getIdBanda, b -> b.getIdBanda() + " | " + b.getNome());
            if (id != null) {
                chosenBanda[0] = id;
                lblSelectedBanda.setText("ID: " + id);
            }
        });
    }
}
