package etapa4.dados;

import java.util.ArrayList;
import java.util.List;

public class Musico {
    private int nroRegistro;
    private String nome;
    private Endereco endereco;
    private List<Instrumento> instrumentos;

    public Musico() {
        instrumentos = new ArrayList<>();
    }

    public Musico(String nome, Endereco endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.instrumentos = new ArrayList<>();
    }

    public int getNroRegistro() { 
        return nroRegistro; 
    }
    public void setNroRegistro(int nroRegistro) { 
        this.nroRegistro = nroRegistro;
    }
    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }
    public Endereco getEndereco() { 
        return endereco; 
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

     public List<Instrumento> getInstrumentos() {
        return instrumentos; 
    }
    public void setInstrumentos(List<Instrumento> instrumentos) {
        this.instrumentos = instrumentos; 
    }

    @Override
    public String toString() {
        String instrumentosStr = instrumentos.isEmpty() ? "Sem instrumentos" : "";
        for (Instrumento i : instrumentos) {
            instrumentosStr += i.getNome() + ", ";
        }
        if (!instrumentosStr.isEmpty()) {
            instrumentosStr = instrumentosStr.substring(0, instrumentosStr.length() - 2);
        }

        return nroRegistro + " - " + nome + "\n         Endereco " + (endereco != null ? endereco.toString() : "Sem endereço")
                + "\n         Instrumentos: " + instrumentosStr;
    }

}
