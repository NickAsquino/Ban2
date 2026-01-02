package etapa4.apresentacao.instrumento;

import javax.swing.*;
import java.awt.*;
import etapa4.gerenciamento.gravadora;

public class TelaInstrumentoMenu extends JFrame {
    
    private JPanel button(JButton btnInserir) {
        btnInserir.setPreferredSize(new Dimension(200, 50));

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painel.add(btnInserir);
        return painel;
    }

    public TelaInstrumentoMenu(JFrame parent, gravadora gravadora) {
        
        parent.setVisible(false);

        setTitle("Menu de Instrumentos");
        setSize(1500, 800);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 3, 10, 10));

        JLabel classe = new JLabel("Menu do Instrumento", SwingConstants.CENTER);
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

        btnInserir.addActionListener(e -> new TelaInserirInstrumento(gravadora).setVisible(true));
        btnListar.addActionListener(e -> new TelaListarInstrumentos(gravadora).setVisible(true));
        btnEditar.addActionListener(e -> {
            if(gravadora.listarInstrumentos().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não é possível editar porque não há instrumentos cadastrados.");
            } else {
                new TelaEditarInstrumento(gravadora).setVisible(true);
            }
        });
        btnExcluir.addActionListener(e -> {
            if(gravadora.listarInstrumentos().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não é possível excluir porque não há instrumentos cadastrados.");
            } else {
                new TelaExcluirInstrumento(gravadora).setVisible(true);
            }
        });

        btnVoltar.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
