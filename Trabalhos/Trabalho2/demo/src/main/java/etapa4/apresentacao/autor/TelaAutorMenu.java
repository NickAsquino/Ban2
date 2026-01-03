package etapa4.apresentacao.autor;

import etapa4.gerenciamento.gravadora;

import javax.swing.*;
import java.awt.*;

public class TelaAutorMenu extends JFrame {

    private JPanel button(JButton btnInserir) {
        btnInserir.setPreferredSize(new Dimension(200, 50));

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painel.add(btnInserir);
        return painel;
    }

    public TelaAutorMenu(JFrame parent, gravadora gravadora) {
        
        parent.setVisible(false);

        setTitle("Menu de Autores");
        setSize(1500, 800);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 3, 10, 10));

        JLabel classe = new JLabel("Menu do Autor", SwingConstants.CENTER);
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

        btnInserir.addActionListener(e -> new TelaInserirAutor(gravadora).setVisible(true));
        btnEditar.addActionListener(e -> {
            if(gravadora.listarAutores().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não é possível editar porque não há autor cadastrado.");
            } else {
                new TelaEditarAutor(gravadora).setVisible(true);
            }
        });
        btnExcluir.addActionListener(e -> {
            if(gravadora.listarAutores().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não é possível editar porque não há autor cadastrado.");
            } else {
                new TelaExcluirAutor(gravadora).setVisible(true);
            }
        });
        btnListar.addActionListener(e -> new TelaListarAutores(gravadora).setVisible(true));

        btnVoltar.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
