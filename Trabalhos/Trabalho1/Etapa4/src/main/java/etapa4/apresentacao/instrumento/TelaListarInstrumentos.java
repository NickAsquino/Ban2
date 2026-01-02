package etapa4.apresentacao.instrumento;

import javax.swing.*;
import etapa4.gerenciamento.gravadora;
import etapa4.dados.Instrumento;
import java.util.List;

public class TelaListarInstrumentos extends JFrame {

    public TelaListarInstrumentos(gravadora gravadora) {
        setTitle("Instrumentos Cadastrados");
        setSize(500, 350);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        add(scroll);

        List<Instrumento> instrumentos = gravadora.listarInstrumentos();

        if (instrumentos.isEmpty()) {
            area.setText("Nenhum instrumento cadastrado.");
        } else {
            for (Instrumento i : instrumentos) {
                area.append(i.toString() + "\n");
            }
        }
    }
}
