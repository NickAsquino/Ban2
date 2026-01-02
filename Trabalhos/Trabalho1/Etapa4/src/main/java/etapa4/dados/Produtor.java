package etapa4.dados;

public class Produtor {
    private int idProdutor;
    private String nome;

    public Produtor() {}

    public Produtor(String nome) {
        this.nome = nome;
    }

    public Produtor(int idProdutor, String nome) {
        this.idProdutor = idProdutor;
        this.nome = nome;
    }

    public int getIdProdutor() {
        return idProdutor;
    }

    public void setIdProdutor(int idProdutor) {
        this.idProdutor = idProdutor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "ID: " + idProdutor + "     |   " + nome;
    }
}
