package etapa4.dados;

import java.util.ArrayList;
import java.util.List;

public class Musica {
    private int idMusica;
    private String titulo;
    private int duracao;
    private List<Autor> autores;

    public Musica() {
        autores = new ArrayList<>();
    }

    public Musica(String titulo) {
        this.titulo = titulo;
    }

    public Musica(int idMusica, String titulo, int duracao) {
        this.idMusica = idMusica;
        this.titulo = titulo;
        this.duracao = duracao;
        this.autores = new ArrayList<>();
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

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        String autoresStr = autores.isEmpty() ? "Sem autores" : "";
        for (Autor a : autores) {
            autoresStr += a.getNome() + ", ";
        }
        if(!autoresStr.isEmpty()) {
            autoresStr = autoresStr.substring(0, autoresStr.length() - 2);
        }
        return "ID: " + idMusica + " | Título: " + titulo + " | Duração: " + duracao + " | Autores: " + autoresStr;
    }

}