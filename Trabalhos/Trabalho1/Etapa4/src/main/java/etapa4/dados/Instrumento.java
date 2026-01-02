package etapa4.dados;

public class Instrumento {
    private int codInterno;
    private String nome;
    
    public Instrumento() {}
    
    public Instrumento(String nome) {
        this.nome = nome;
    }
    public int getCodInterno() {
        return codInterno;
    }
    public void setCodInterno(int codInterno) {
        this.codInterno = codInterno;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String toString() {
        return "ID: " + codInterno + "     |   " + nome;
    }
}