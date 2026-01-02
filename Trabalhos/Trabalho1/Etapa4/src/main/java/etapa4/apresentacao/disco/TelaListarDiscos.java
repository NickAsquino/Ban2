package etapa4.apresentacao.disco;

import javax.swing.*;
import etapa4.gerenciamento.gravadora;
import etapa4.dados.Disco;
import java.util.List;

public class TelaListarDiscos extends JFrame {

    public TelaListarDiscos(gravadora gravadora) {
        setTitle("Discos Cadastrados");
        setSize(650, 400);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        add(scroll);

        List<Disco> discos = gravadora.listarDiscos();

        if (discos.isEmpty()) {
            area.setText("Nenhum disco cadastrado.");
        } else {
            for (Disco d : discos) {
                area.append(d.toString() + "\n");
            }
        }
    }
}
