package etapa4.dados;

public class Banda {
    private int idBanda;
    private String nome;

    public Banda() {}

    public Banda(String nome) {
        this.nome = nome;
    }

    public Banda(int idBanda, String nome) {
        this.idBanda = idBanda;
        this.nome = nome;
    }

    public int getIdBanda() {
        return idBanda;
    }

    public void setIdBanda(int idBanda) {
        this.idBanda = idBanda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "ID: " + idBanda + "     |   " + nome;
    }
}
