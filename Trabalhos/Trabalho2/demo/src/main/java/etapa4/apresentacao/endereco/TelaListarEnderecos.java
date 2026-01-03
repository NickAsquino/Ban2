package etapa4.apresentacao.endereco;

import javax.swing.*;
import etapa4.gerenciamento.gravadora;
import etapa4.dados.Endereco;
import java.util.List;

public class TelaListarEnderecos extends JFrame {
    public TelaListarEnderecos(gravadora gravadora) {
        setTitle("Endereços Cadastrados");
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        add(scroll);

        List<Endereco> enderecos = gravadora.listarEnderecos();
        if (enderecos.isEmpty()) {
            area.setText("Nenhum endereço cadastrado.");
        } else {
            for (Endereco e : enderecos) {
                area.append(e.toString() + "\n");
            }
        }
    }
}
