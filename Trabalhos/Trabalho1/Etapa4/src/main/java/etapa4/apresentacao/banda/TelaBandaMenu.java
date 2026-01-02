package etapa4.apresentacao.banda;

import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import java.awt.*;

public class TelaBandaMenu extends JFrame {
    
    private JPanel button(JButton btnInserir) {
        btnInserir.setPreferredSize(new Dimension(200, 50));

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painel.add(btnInserir);
        return painel;
    }

    public TelaBandaMenu(JFrame parent, gravadora gravadora) {
        parent.setVisible(false);

        setTitle("Menu de Bandas");
        setSize(1500, 800);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 3, 10, 10));

        JLabel classe = new JLabel("Menu da Banda", SwingConstants.CENTER);
        classe.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel text = new JLabel("Escolha uma opcão abaixo:", SwingConstants.CENTER);
        text.setFont(new Font("Arial", Font.PLAIN, 20));

        JButton btnInserir = new JButton("Inserir");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnListar = new JButton("Listar");
        JButton btnVoltar = new JButton("Voltar");

        add(classe);
        add(text);
        add(button(btnInserir));
        add(button(btnEditar));
        add(button(btnExcluir));
        add(button(btnListar));
        add(button(btnVoltar));

        btnInserir.addActionListener(e -> new TelaInserirBanda(gravadora).setVisible(true));
        btnListar.addActionListener(e -> new TelaListarBandas(gravadora).setVisible(true));
        btnEditar.addActionListener(e -> {
            if(gravadora.listarBandas().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não é possível editar porque não há bandas cadastrados.");
            } else {
                new TelaEditarBanda(gravadora).setVisible(true);
            }
        });
        btnExcluir.addActionListener(e -> { 
            if(gravadora.listarBandas().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não é possível excluir porque não há bandas cadastrados.");
            } else {
                new TelaExcluirBanda(gravadora).setVisible(true);
            }
        });

        btnVoltar.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
