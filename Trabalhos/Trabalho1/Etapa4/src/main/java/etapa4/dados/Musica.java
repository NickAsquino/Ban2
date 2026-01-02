package etapa4.dados;

public class Musica {
    private int idMusica;
    private String titulo;
    private int duracao;
    private String autores;

    public Musica() {}

    public Musica(String titulo) {
        this.titulo = titulo;
    }

    public Musica(int idMusica, String titulo, int duracao, String autores) {
        this.idMusica = idMusica;
        this.titulo = titulo;
        this.duracao = duracao;
        this.autores = autores;
    }

    public int getIdMusica() {
        return idMusica;
    }

    public void setIdMusica(int idMusica) {
        this.idMusica = idMusica;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "ID: " + idMusica + " | Título: " + titulo + " | Duração: " + duracao + " | Autores: " + autores;
    }

}
