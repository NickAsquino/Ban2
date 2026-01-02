package etapa4.apresentacao.disco;

import etapa4.gerenciamento.gravadora;
import etapa4.dados.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class TelaEditarDisco extends JFrame {

    private JTextField campoId = new JTextField();
    private JTextField campoFormato = new JTextField();
    private JTextField campoData = new JTextField("YYYY-MM-DD");
    private JTextField campoTitulo = new JTextField();
    private JComboBox<Produtor> comboProdutor = new JComboBox<>();
    private JComboBox<Banda> comboBanda = new JComboBox<>();
    private JTextField campoMusicas = new JTextField();

    private JButton botaoCarregar = new JButton("Carregar Disco");
    private JButton botaoSalvar = new JButton("Salvar Alterações");
    private JButton botaoCancelar = new JButton("Cancelar");

    private TelaListarDiscos telaDiscos;

    public TelaEditarDisco(gravadora gravadora) {
        setTitle("Editar Disco");
        setSize(800, 500);
        setLocation(50, 200);
        setLayout(new GridLayout(10, 2, 10, 10));

        telaDiscos = new TelaListarDiscos(gravadora);
        telaDiscos.setLocation(850, 200);
        telaDiscos.setVisible(true);

        add(new JLabel("ID do disco que deseja editar:"));
        add(campoId);

        add(new JLabel("Formato:"));
        add(campoFormato);

        add(new JLabel("Data (YYYY-MM-DD):"));
        add(campoData);

        add(new JLabel("Título:"));
        add(campoTitulo);

        add(new JLabel("Produtor:"));
        add(comboProdutor);

        add(new JLabel("Banda:"));
        add(comboBanda);

        add(new JLabel("Músicas (separadas por vírgula):"));
        add(campoMusicas);

        add(botaoCarregar);
        add(botaoSalvar);
        add(botaoCancelar);

        // Carregar combos
        gravadora.listarProdutores().forEach(comboProdutor::addItem);
        gravadora.listarBandas().forEach(comboBanda::addItem);

        botaoCarregar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                Disco d = gravadora.buscarDiscoPorId(id);

                if (d == null) {
                    JOptionPane.showMessageDialog(this, "Disco não encontrado!");
                    return;
                }

                campoFormato.setText(d.getFormato());
                campoData.setText(d.getData() != null ? d.getData().toString() : "");
                campoTitulo.setText(d.getTitulo());

                comboProdutor.setSelectedItem(d.getProdutor());
                comboBanda.setSelectedItem(d.getBanda());

                // Carregar músicas já associadas
                String musicas = gravadora.buscarMusicasDoDisco(id)
                        .stream()
                        .map(Musica::getTitulo)
                        .collect(Collectors.joining(", "));
                campoMusicas.setText(musicas);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar disco!");
            }
        });

        botaoSalvar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText());

                Disco d = gravadora.buscarDiscoPorId(id);
                if (d == null) {
                    JOptionPane.showMessageDialog(this, "Disco não encontrado!");
                    return;
                }

                String formato = campoFormato.getText().trim();
                String titulo = campoTitulo.getText().trim();

                if (titulo.isEmpty() || formato.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Título e formato são obrigatórios!");
                    return;
                }

                // Data opcional
                Date data = null;
                String txtData = campoData.getText().trim();
                if (!txtData.isEmpty() && !txtData.equals("YYYY-MM-DD")) {
                    data = Date.valueOf(LocalDate.parse(txtData));
                }

                Produtor prod = (Produtor) comboProdutor.getSelectedItem();
                Banda banda = (Banda) comboBanda.getSelectedItem();

                gravadora.atualizarDisco(id, formato, data, titulo, prod, banda);

                // Atualizar músicas
                gravadora.removerMusicasDoDisco(id);

                String[] lista = campoMusicas.getText().split(",");
                for (String nomeMusica : lista) {
                    nomeMusica = nomeMusica.trim();
                    if (nomeMusica.isEmpty()) continue;

                    Musica m = gravadora.buscarMusicaPorNome(nomeMusica);
                    if (m == null) {
                        m = new Musica(nomeMusica);
                        gravadora.inserirMusica(m);
                        m = gravadora.buscarMusicaPorNome(nomeMusica);
                    }

                    gravadora.associarMusica(d, m);
                }

                JOptionPane.showMessageDialog(this, "Disco atualizado com sucesso!");
                telaDiscos.dispose();
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao atualizar disco!");
            }
        });

        botaoCancelar.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { telaDiscos.dispose(); }
            @Override
            public void windowClosed(WindowEvent e) { telaDiscos.dispose(); }
        });
    }
}
