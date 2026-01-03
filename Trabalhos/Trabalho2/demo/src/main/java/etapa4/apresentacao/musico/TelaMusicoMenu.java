package etapa4.apresentacao.musico;

import javax.swing.*;
import java.awt.*;
import etapa4.gerenciamento.gravadora;

public class TelaMusicoMenu extends JFrame {
    
private JPanel button(JButton btnInserir) {
        btnInserir.setPreferredSize(new Dimension(200, 50));

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painel.add(btnInserir);
        return painel;
    }

    public TelaMusicoMenu(JFrame parent, gravadora gravadora) {
        
        parent.setVisible(false);

        setTitle("Menu de Musicos");
        setSize(1500, 800);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 3, 10, 10));

        JLabel classe = new JLabel("Menu do Musico", SwingConstants.CENTER);
        classe.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel text = new JLabel("Escolha uma opcão abaixo:", SwingConstants.CENTER);
        text.setFont(new Font("Arial", Font.PLAIN, 20));

        JButton btnInserir = new JButton("Inserir");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnEditar = new JButton("Editar");
        JButton btnListar = new JButton("Listar");
        JButton btnVoltar = new JButton("Voltar");

        add(classe);
        add(text);
        add(button(btnInserir));
        add(button(btnEditar));
        add(button(btnExcluir));
        add(button(btnListar));
        add(button(btnVoltar));

        btnInserir.addActionListener(e -> {
            if(gravadora.listarEnderecos().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não há endereços cadastrados. Crie um endereço primeiro para seguir com o cadastro de músicos");
            } else {
                new TelaInserirMusico(gravadora).setVisible(true);
            }
        });
        btnEditar.addActionListener(e -> {
            if(gravadora.listarMusicos().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não é possível editar porque não há músicos cadastrados.");
            } else {
                new TelaEditarMusico(gravadora).setVisible(true);
            }
        });
        btnExcluir.addActionListener(e -> {
            if(gravadora.listarMusicos().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não é possível excluir porque não há músicos cadastrados.");
            } else {
                new TelaExcluirMusico(gravadora).setVisible(true);
            }
        });
        btnListar.addActionListener(e -> new TelaListarMusicos(gravadora).setVisible(true));

        btnVoltar.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
