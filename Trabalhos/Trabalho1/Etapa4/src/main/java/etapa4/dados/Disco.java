package etapa4.dados;

import java.sql.Date;
import java.util.List;

public class Disco {
    private int identificador;
    private String formato;
    private Date data;
    private String titulo;
    private Produtor produtor;
    private Banda banda;
    private List<Musica> musicas;
    private int numMusicas;

    public Disco(String formato, Date data, String titulo, Produtor produtor, Banda banda, List<Musica> musicas) {
        this.formato = formato;
        this.data = data;
        this.titulo = titulo;
        this.produtor = produtor;
        this.banda = banda;
        this.musicas = musicas;
        this.numMusicas = (musicas != null) ? musicas.size() : 0;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Produtor getProdutor() {
        return produtor;
    }

    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
    }

    public Banda getBanda() {
        return banda;
    }

    public void setBanda(Banda banda) {
        this.banda = banda;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    public int getNumMusicas() {
        return numMusicas;
    }

    public void setNumMusicas(int numMusicas) {
        this.numMusicas = numMusicas;
    }

    @Override
    public String toString() {
        String nomeBanda = (banda != null ? banda.getNome() : "Sem Banda");
        String nomeProdutor = (produtor != null ? produtor.getNome() : "Sem Produtor");

        String listaMusicas;
        if (musicas == null || musicas.isEmpty()) {
            listaMusicas = "Nenhuma música cadastrada";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for (Musica m : musicas) {
                sb.append("   - ").append(m.getTitulo()).append("\n");
            }
            listaMusicas = sb.toString();
        }

        return  
                "*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n" +
                " Disco: " + titulo + " (ID: " + identificador + ")\n" +
                " Formato: " + formato + "\n" +
                " Data: " + (data != null ? data.toString() : "Sem data") + "\n" +
                " Banda: " + nomeBanda + "\n" +
                " Produtor: " + nomeProdutor + "\n" +
                " Músicas: (" + numMusicas + ")" + listaMusicas;
    }
}
