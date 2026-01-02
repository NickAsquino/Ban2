package etapa4.apresentacao.endereco;

import javax.swing.*;
import java.awt.*;
import etapa4.gerenciamento.gravadora;

public class TelaEnderecoMenu extends JFrame {
    
    private JPanel button(JButton btnInserir) {
        btnInserir.setPreferredSize(new Dimension(200, 50));

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painel.add(btnInserir);
        return painel;
    }

    public TelaEnderecoMenu(JFrame parent, gravadora gravadora) {
        
        parent.setVisible(false);

        setTitle("Menu de Enderecos");
        setSize(1500, 800);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 3, 10, 10));

        JLabel classe = new JLabel("Menu do Endereco", SwingConstants.CENTER);
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

        btnInserir.addActionListener(e -> new TelaInserirEndereco(gravadora).setVisible(true));
        btnExcluir.addActionListener(e -> new TelaExcluirEndereco(gravadora).setVisible(true));
        btnEditar.addActionListener(e -> new TelaEditarEndereco(gravadora).setVisible(true));
        btnListar.addActionListener(e -> new TelaListarEnderecos(gravadora).setVisible(true));

        btnVoltar.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
