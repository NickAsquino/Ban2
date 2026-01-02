package etapa4.apresentacao;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import etapa4.apresentacao.autor.TelaAutorMenu;
import etapa4.apresentacao.banda.TelaBandaMenu;
import etapa4.apresentacao.disco.TelaDiscoMenu;
import etapa4.apresentacao.endereco.TelaEnderecoMenu;
import etapa4.apresentacao.instrumento.TelaInstrumentoMenu;
import etapa4.apresentacao.musica.TelaMusicaMenu;
import etapa4.apresentacao.musico.TelaMusicoMenu;
import etapa4.apresentacao.produtor.TelaProdutorMenu;

import etapa4.gerenciamento.gravadora;

public class TelaPrincipal extends JFrame {

    private JPanel painel = new JPanel();
    private JLabel menu = new JLabel("Escolha uma opcão abaixo:");
        
    private JButton autor = new JButton("Autor");
    private JButton banda = new JButton("Banda");
    private JButton disco = new JButton("Disco");
    private JButton endereco = new JButton("Endereco");
    private JButton instrumento = new JButton("Instrumento");
    private JButton musica = new JButton("Musica");
    private JButton musico = new JButton("Musico");
    private JButton produtor = new JButton("Produtor");
    private JButton sair = new JButton("Sair");

    /*private JButton botaoInserirEndereco = new JButton("Inserir endereco");
    private JButton botaoListarEndereco = new JButton("Listar enderecos cadastrados");
    private JButton botaoInserirMusico = new JButton("Inserir musico");
    private JButton botaoListarMusico = new JButton("Listar musicos");
    private JButton botaoExcluirEndereco = new JButton("Excluir endereco");
    private JButton botaoExcluirMusico = new JButton("Excluir musico");
    private JButton botaoEditarMusico = new JButton("Editar informacoes de um musico");
    private JButton botaoSair = new JButton("Sair");*/

    //private CorreioEletronico correioEletronico = new CorreioEletronico();

    public TelaPrincipal(gravadora gravadora) {

        setTitle("Gravadora de musica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(5, 50, 1500, 800);

        setContentPane(painel);
        painel.setLayout(null);

        menu.setBounds(600, 50, 400, 30);
        menu.setFont(new Font("Arial", Font.PLAIN, 20));
        painel.add(menu);

        autor.setBounds(500, 100, 350, 40);
        autor.setFont(new Font("Arial", Font.PLAIN, 17));
        painel.add(autor);

        banda.setBounds(500, 150, 350, 40);
        banda.setFont(new Font("Arial", Font.PLAIN, 17));
        painel.add(banda);

        disco.setBounds(500, 200, 350, 40);
        disco.setFont(new Font("Arial", Font.PLAIN, 17));
        painel.add(disco);

        endereco.setBounds(500, 250, 350, 40);
        endereco.setFont(new Font("Arial", Font.PLAIN, 17));
        painel.add(endereco);

        instrumento.setBounds(500, 300, 350, 40);
        instrumento.setFont(new Font("Arial", Font.PLAIN, 17));
        painel.add(instrumento);

        musica.setBounds(500, 350, 350, 40);
        musica.setFont(new Font("Arial", Font.PLAIN, 17));
        painel.add(musica);

        musico.setBounds(500, 400, 350, 40);
        musico.setFont(new Font("Arial", Font.PLAIN, 17));
        painel.add(musico);

        produtor.setBounds(500, 450, 350, 40);
        produtor.setFont(new Font("Arial", Font.PLAIN, 17));
        painel.add(produtor);

        sair.setBounds(500, 550, 350, 40);
        sair.setFont(new Font("Arial", Font.PLAIN, 17));
        painel.add(sair);

        autor.addActionListener(e -> new TelaAutorMenu(this, gravadora).setVisible(true));
        banda.addActionListener(e -> new TelaBandaMenu(this, gravadora).setVisible(true));
        disco.addActionListener(e -> new TelaDiscoMenu(this, gravadora).setVisible(true));
        endereco.addActionListener(e -> new TelaEnderecoMenu(this, gravadora).setVisible(true));
        instrumento.addActionListener(e -> new TelaInstrumentoMenu(this, gravadora).setVisible(true));
        musica.addActionListener(e -> new TelaMusicaMenu(this, gravadora).setVisible(true));
        musico.addActionListener(e -> new TelaMusicoMenu(this, gravadora).setVisible(true));
        produtor.addActionListener(e -> new TelaProdutorMenu(this, gravadora).setVisible(true));

        sair.addActionListener(e -> System.exit(0));

        /*
        menu.setBounds(600, 50, 400, 30);
        menu.setFont(new Font("Arial", Font.PLAIN, 20));
        painel.add(menu);

        botaoInserirEndereco.setBounds(500, 150, 400, 50);
        menu.setFont(new Font("Arial", Font.PLAIN, 20));
        painel.add(botaoInserirEndereco);

        botaoListarEndereco.setBounds(500, 225, 400, 50);
        menu.setFont(new Font("Arial", Font.PLAIN, 20));
        painel.add(botaoListarEndereco);

        botaoInserirMusico.setBounds(500, 300, 400, 50);
        menu.setFont(new Font("Arial", Font.PLAIN, 20));
        painel.add(botaoInserirMusico);

        botaoListarMusico.setBounds(500, 375, 400, 50);
        menu.setFont(new Font("Arial", Font.PLAIN, 20));
        painel.add(botaoListarMusico);

        botaoExcluirEndereco.setBounds(500, 450, 400, 50);
        menu.setFont(new Font("Arial", Font.PLAIN, 20));
        painel.add(botaoExcluirEndereco);   

        botaoExcluirMusico.setBounds(500, 525, 400, 50);
        menu.setFont(new Font("Arial", Font.PLAIN, 20));
        painel.add(botaoExcluirMusico);

        botaoEditarMusico.setBounds(500, 600, 400, 50);
        menu.setFont(new Font("Arial", Font.PLAIN, 20));
        painel.add(botaoEditarMusico);

        botaoSair.setBounds(500, 675, 400, 50);
        menu.setFont(new Font("Arial", Font.PLAIN, 20));
        painel.add(botaoSair);

        botaoInserirEndereco.addActionListener(e -> new TelaInserirEndereco(gravadora).setVisible(true));
        botaoListarEndereco.addActionListener(e -> new TelaListarEnderecos(gravadora).setVisible(true));
        botaoListarMusico.addActionListener(e -> new TelaListarMusicos(gravadora).setVisible(true));

        botaoInserirMusico.addActionListener(e -> {
            if(gravadora.listarEnderecos().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não há endereços cadastrados. Crie um endereço primeiro para seguir com o cadastro de músicos");
            } else {
                new TelaInserirMusico(gravadora).setVisible(true);
            }
        });
        botaoExcluirEndereco.addActionListener(e -> {
            if(gravadora.listarEnderecos().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não é possível excluir porque não há endereços cadastrados.");
            } else {
                new TelaExcluirEndereco(gravadora).setVisible(true);
            }
        });
        botaoExcluirMusico.addActionListener(e -> {
            if(gravadora.listarMusicos().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não é possível excluir porque não há músicos cadastrados.");
            } else {
                new TelaExcluirMusico(gravadora).setVisible(true);
            }
        });
        botaoEditarMusico.addActionListener(e -> {
            if(gravadora.listarMusicos().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não é possível editar porque não há músicos cadastrados.");
            } else {
                new TelaEditarMusico(gravadora).setVisible(true);
            }
        });

        /*
        botaoCadastro.addActionListener(e -> {
            new TelaCadastroUsuario(correioEletronico).setVisible(true);
        });

        botaoEntrar.addActionListener(e -> {
            String email = caixaTextoEmail.getText();
            String senha = caixaTextoSenha.getText();

            Usuario usuario = correioEletronico.verificarEmail(email);
            if (usuario != null && usuario.getSenha().equals(senha)) {
                new TelaEmails(correioEletronico, usuario).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Email ou senha inválidos!");
            }
        });*/
        
    }
}