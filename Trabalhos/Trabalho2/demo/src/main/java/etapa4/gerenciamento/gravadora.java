package etapa4.gerenciamento;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;

import static com.mongodb.client.model.Filters.eq;

import etapa4.dados.*;

public class gravadora {

    private final MongoDatabase mongo;

    public gravadora(MongoDatabase mongoDatabase) {
        if (mongoDatabase == null)
            throw new IllegalArgumentException("mongoDatabase must not be null");
        this.mongo = mongoDatabase;
    }

    // --- INSERTS ---
    public void inserirAutor(Autor a) {
        MongoCollection<Document> col = mongo.getCollection("Autor");
        int id = getNextSequence("Autor");
        a.setIdAutor(id);
        col.insertOne(new Document("idAutor", id).append("nome", a.getNome()));
    }

    public void inserirBanda(Banda b) {
        MongoCollection<Document> col = mongo.getCollection("Banda");
        int id = getNextSequence("Banda");
        b.setIdBanda(id);
        col.insertOne(new Document("idBanda", id).append("nome", b.getNome()));
    }

    public void inserirDisco(Disco d) throws SQLException {
        try {
            MongoCollection<Document> col = mongo.getCollection("Disco");
            int id = getNextSequence("Disco");
            d.setIdentificador(id);
            int num = d.getMusicas() != null ? d.getMusicas().size() : 0;
            d.setNumMusicas(num);

            Document doc = new Document("identificador", id)
                    .append("formato", d.getFormato())
                    .append("data", d.getData() != null ? new java.util.Date(d.getData().getTime()) : null)
                    .append("titulo", d.getTitulo())
                    .append("idProdutor", d.getProdutor() != null ? d.getProdutor().getIdProdutor() : null)
                    .append("idBanda", d.getBanda() != null ? d.getBanda().getIdBanda() : null)
                    .append("numMusicas", num);

            col.insertOne(doc);
        } catch (Exception ex) {
            throw new SQLException("Erro ao inserir disco (MongoDB): " + ex.getMessage(), ex);
        }
    }

    public void inserirMusica(Musica m) {
        MongoCollection<Document> col = mongo.getCollection("Musica");
        int id = getNextSequence("Musica");
        m.setIdMusica(id);
        col.insertOne(new Document("idMusica", id)
                .append("titulo", m.getTitulo())
                .append("duracao", m.getDuracao()));
    }

    public void inserirEndereco(Endereco e) {
        MongoCollection<Document> col = mongo.getCollection("Endereco");
        int id = getNextSequence("Endereco");
        e.setIdEndereco(id);
        col.insertOne(new Document("idEndereco", id)
                .append("cidade", e.getCidade())
                .append("rua", e.getRua())
                .append("nroCasa", e.getNroCasa())
                .append("telefone", e.getTelefone()));
    }

    public void inserirInstrumento(Instrumento i) {
        MongoCollection<Document> col = mongo.getCollection("Instrumento");
        int id = getNextSequence("Instrumento");
        i.setCodInterno(id);
        col.insertOne(new Document("codInterno", id).append("nome", i.getNome()));
    }

    public void inserirMusico(Musico m) {
        MongoCollection<Document> col = mongo.getCollection("Musico");
        int id = getNextSequence("Musico");
        m.setNroRegistro(id);
        Integer idEnd = m.getEndereco() != null ? m.getEndereco().getIdEndereco() : null;
        col.insertOne(new Document("nroRegistro", id).append("nome", m.getNome()).append("idEndereco", idEnd));
    }

    public void inserirProdutor(Produtor p) {
        MongoCollection<Document> col = mongo.getCollection("Produtor");
        int id = getNextSequence("Produtor");
        p.setIdProdutor(id);
        col.insertOne(new Document("idProdutor", id).append("nome", p.getNome()));
    }

    // --- READS ---
    public Autor buscarAutorPorId(int id) {
        Document doc = mongo.getCollection("Autor").find(eq("idAutor", id)).first();
        if (doc == null)
            return null;
        Autor a = new Autor();
        Integer _id = doc.getInteger("idAutor");
        if (_id != null)
            a.setIdAutor(_id);
        a.setNome(doc.getString("nome"));
        return a;
    }

    public Autor buscarAutorPorNome(String nome) {
        Document doc = mongo.getCollection("Autor").find(eq("nome", nome)).first();
        if (doc == null)
            return null;
        Autor a = new Autor();
        Integer _id = doc.getInteger("idAutor");
        if (_id != null)
            a.setIdAutor(_id);
        a.setNome(doc.getString("nome"));
        return a;
    }

    public Disco buscarDiscoPorId(int id) {
        Document doc = mongo.getCollection("Disco").find(eq("identificador", id)).first();
        if (doc == null)
            return null;
        Integer idProd = doc.getInteger("idProdutor");
        Produtor produtor = idProd != null ? buscarProdutorPorId(idProd) : null;
        Integer idBanda = doc.getInteger("idBanda");
        Banda banda = idBanda != null ? buscarBandaPorId(idBanda) : null;
        List<Musica> musicas = buscarMusicasDoDisco(id);
        Disco d = new Disco(
                doc.getString("formato"),
                doc.getDate("data") != null ? new java.sql.Date(doc.getDate("data").getTime()) : null,
                doc.getString("titulo"),
                produtor,
                banda,
                musicas);
        Integer ident = doc.getInteger("identificador");
        if (ident != null)
            d.setIdentificador(ident);
        // Ensure numMusicas reflects actual associated records (DiscoMusica)
        d.setNumMusicas(musicas != null ? musicas.size() : 0);
        return d;
    }

    public Disco buscarDiscoPorTitulo(String titulo) {
        Document doc = mongo.getCollection("Disco").find(eq("titulo", titulo)).first();
        if (doc == null)
            return null;
        Disco d = new Disco(
                doc.getString("formato"),
                doc.getDate("data") != null ? new java.sql.Date(doc.getDate("data").getTime()) : null,
                doc.getString("titulo"),
                doc.getInteger("idProdutor") != null ? buscarProdutorPorId(doc.getInteger("idProdutor")) : null,
                doc.getInteger("idBanda") != null ? buscarBandaPorId(doc.getInteger("idBanda")) : null,
                new ArrayList<>());
        Integer ident = doc.getInteger("identificador");
        if (ident != null)
            d.setIdentificador(ident);
        // Ensure we load associated music and set an accurate count
        d.setMusicas(buscarMusicasDoDisco(d.getIdentificador()));
        d.setNumMusicas(d.getMusicas() != null ? d.getMusicas().size() : 0);
        return d;
    }

    public Endereco buscarEnderecoPorId(int id) {
        Document doc = mongo.getCollection("Endereco").find(eq("idEndereco", id)).first();
        if (doc == null)
            return null;
        Endereco e = new Endereco();
        Integer _id = doc.getInteger("idEndereco");
        if (_id != null)
            e.setIdEndereco(_id);
        e.setCidade(doc.getString("cidade"));
        e.setRua(doc.getString("rua"));
        Integer nro = doc.getInteger("nroCasa");
        if (nro != null)
            e.setNroCasa(nro);
        e.setTelefone(doc.getString("telefone"));
        return e;
    }

    public Musica buscarMusicaPorId(int id) {
        Document doc = mongo.getCollection("Musica").find(eq("idMusica", id)).first();
        if (doc == null)
            return null;
        Musica m = new Musica();
        Integer v = doc.getInteger("idMusica");
        if (v != null)
            m.setIdMusica(v);
        m.setTitulo(doc.getString("titulo"));
        Integer dur = doc.getInteger("duracao");
        if (dur != null)
            m.setDuracao(dur);
        List<Autor> autores = listarAutoresDaMusica(m.getIdMusica());
        m.setAutores(autores);
        return m;
    }

    public Musico buscarMusicoPorId(int id) {
        Document doc = mongo.getCollection("Musico").find(eq("nroRegistro", id)).first();
        if (doc == null)
            return null;
        Musico m = new Musico();
        Integer nr = doc.getInteger("nroRegistro");
        if (nr != null)
            m.setNroRegistro(nr);
        m.setNome(doc.getString("nome"));
        Integer idEnd = doc.getInteger("idEndereco");
        Endereco end = idEnd != null ? buscarEnderecoPorId(idEnd) : null;
        m.setEndereco(end);
        return m;
    }

    // --- LISTS ---
    public List<Autor> listarAutores() {
        List<Autor> lista = new ArrayList<>();
        FindIterable<Document> docs = mongo.getCollection("Autor").find();
        for (Document doc : docs) {
            Autor a = new Autor();
            Integer id = doc.getInteger("idAutor");
            if (id != null)
                a.setIdAutor(id);
            a.setNome(doc.getString("nome"));
            lista.add(a);
        }
        return lista;
    }

    public List<Banda> listarBandas() {
        List<Banda> lista = new ArrayList<>();
        FindIterable<Document> docs = mongo.getCollection("Banda").find();
        for (Document doc : docs) {
            Banda b = new Banda();
            Integer id = doc.getInteger("idBanda");
            if (id != null)
                b.setIdBanda(id);
            b.setNome(doc.getString("nome"));
            lista.add(b);
        }
        return lista;
    }

    public List<Endereco> listarEnderecos() {
        List<Endereco> lista = new ArrayList<>();
        FindIterable<Document> docs = mongo.getCollection("Endereco").find();
        for (Document doc : docs) {
            Endereco e = new Endereco();
            Integer id = doc.getInteger("idEndereco");
            if (id != null)
                e.setIdEndereco(id);
            e.setCidade(doc.getString("cidade"));
            e.setRua(doc.getString("rua"));
            Integer nro = doc.getInteger("nroCasa");
            if (nro != null)
                e.setNroCasa(nro);
            e.setTelefone(doc.getString("telefone"));
            lista.add(e);
        }
        return lista;
    }

    
    public List<Musica> listarMusicas() {
        List<Musica> lista = new ArrayList<>();
        FindIterable<Document> docs = mongo.getCollection("Musica").find();
        for (Document doc : docs) {
            Musica m = new Musica();
            Integer id = doc.getInteger("idMusica");
            if (id != null)
                m.setIdMusica(id);
            m.setTitulo(doc.getString("titulo"));
            Integer dur = doc.getInteger("duracao");
            if (dur != null)
                m.setDuracao(dur);
            List<Autor> autores = listarAutoresDaMusica(m.getIdMusica());
            m.setAutores(autores);
            lista.add(m);
        }
        return lista;
    }

    public List<Disco> listarDiscos() {
        List<Disco> lista = new ArrayList<>();
        FindIterable<Document> docs = mongo.getCollection("Disco").find().sort(new Document("identificador", 1));
        for (Document rs : docs) {
            Integer idProdutor = rs.getInteger("idProdutor");
            Produtor produtor = idProdutor != null ? buscarProdutorPorId(idProdutor) : null;
            Integer idBanda = rs.getInteger("idBanda");
            Banda banda = idBanda != null ? buscarBandaPorId(idBanda) : null;
            Integer idDisco = rs.getInteger("identificador");

                java.util.List<Musica> mus = buscarMusicasDoDisco(idDisco != null ? idDisco : 0);
                Disco d = new Disco(
                    rs.getString("formato"),
                    rs.getDate("data") != null ? new java.sql.Date(rs.getDate("data").getTime()) : null,
                    rs.getString("titulo"),
                    produtor,
                    banda,
                    mus);
            if (idDisco != null)
                d.setIdentificador(idDisco);
            // numMusicas should reflect actual number of associated music entries
            d.setNumMusicas(mus != null ? mus.size() : 0);
            lista.add(d);
        }
        return lista;
    }

    public List<Musico> listarMusicos() {
        List<Musico> lista = new ArrayList<>();
        FindIterable<Document> docs = mongo.getCollection("Musico").find();
        for (Document rs : docs) {
            Endereco e = null;
            Integer idEnd = rs.getInteger("idEndereco");
            if (idEnd != null)
                e = buscarEnderecoPorId(idEnd);
            Musico m = new Musico();
            Integer nr = rs.getInteger("nroRegistro");
            if (nr != null)
                m.setNroRegistro(nr);
            m.setNome(rs.getString("nome"));
            m.setEndereco(e);
            List<Instrumento> instrumentos = listarInstrumentosDoMusico(m.getNroRegistro());
            m.setInstrumentos(instrumentos);
            lista.add(m);
        }
        return lista;
    }

    public List<Instrumento> listarInstrumentos() {
        List<Instrumento> lista = new ArrayList<>();
        FindIterable<Document> docs = mongo.getCollection("Instrumento").find().sort(new Document("codInterno", 1));
        for (Document inst : docs) {
            Instrumento i = new Instrumento();
            Integer id = inst.getInteger("codInterno");
            if (id != null)
                i.setCodInterno(id);
            i.setNome(inst.getString("nome"));
            lista.add(i);
        }
        return lista;
    }

    public List<Produtor> listarProdutores() {
        List<Produtor> lista = new ArrayList<>();
        FindIterable<Document> docs = mongo.getCollection("Produtor").find();
        for (Document doc : docs) {
            Produtor p = new Produtor();
            Integer id = doc.getInteger("idProdutor");
            if (id != null)
                p.setIdProdutor(id);
            p.setNome(doc.getString("nome"));
            lista.add(p);
        }
        return lista;
    }

    public List<Instrumento> listarInstrumentosDoMusico(int nroRegistro) {
        List<Instrumento> lista = new ArrayList<>();
        FindIterable<Document> docs = mongo.getCollection("MusicoInstrumento").find(eq("nroRegistro", nroRegistro));
        for (Document doc : docs) {
            Integer cod = doc.getInteger("codInterno");
            if (cod != null) {
                Document inst = mongo.getCollection("Instrumento").find(eq("codInterno", cod)).first();
                if (inst != null) {
                    Instrumento i = new Instrumento();
                    Integer cid = inst.getInteger("codInterno");
                    if (cid != null)
                        i.setCodInterno(cid);
                    i.setNome(inst.getString("nome"));
                    lista.add(i);
                }
            }
        }
        return lista;
    }

    public List<Autor> listarAutoresDaMusica(int idMusica) {
        List<Autor> lista = new ArrayList<>();
        FindIterable<Document> docs = mongo.getCollection("MusicaAutor").find(eq("idMusica", idMusica));
        for (Document doc : docs) {
            Integer idAutor = doc.getInteger("idAutor");
            if (idAutor != null) {
                Autor a = buscarAutorPorId(idAutor);
                if (a != null) {
                    lista.add(a);
                }
            }
        }
        return lista;
    }

    public Banda buscarBandaPorId(int id) {
        Document doc = mongo.getCollection("Banda").find(eq("idBanda", id)).first();
        if (doc == null)
            return null;
        Banda b = new Banda();
        Integer v = doc.getInteger("idBanda");
        if (v != null)
            b.setIdBanda(v);
        b.setNome(doc.getString("nome"));
        return b;
    }

    public Musica buscarMusicaPorNome(String titulo) {
        Document doc = mongo.getCollection("Musica").find(eq("titulo", titulo)).first();
        if (doc == null)
            return null;
        Musica m = new Musica();
        Integer id = doc.getInteger("idMusica");
        if (id != null)
            m.setIdMusica(id);
        m.setTitulo(doc.getString("titulo"));
        Integer dur = doc.getInteger("duracao");
        if (dur != null)
            m.setDuracao(dur);
        List<Autor> autores = listarAutoresDaMusica(m.getIdMusica());
        m.setAutores(autores);
        return m;
    }

    public Musico buscarMusicoPorNome(String nome) {
        Document rs = mongo.getCollection("Musico").find(eq("nome", nome)).first();
        if (rs == null)
            return null;
        Endereco e = null;
        Integer idEnd = rs.getInteger("idEndereco");
        if (idEnd != null)
            e = buscarEnderecoPorId(idEnd);
        Musico m = new Musico();
        Integer nr = rs.getInteger("nroRegistro");
        if (nr != null)
            m.setNroRegistro(nr);
        m.setNome(rs.getString("nome"));
        m.setEndereco(e);
        return m;
    }

    public Instrumento buscarInstrumentoPorNome(String nome) {
        Document doc = mongo.getCollection("Instrumento").find(eq("nome", nome)).first();
        if (doc == null)
            return null;
        Instrumento i = new Instrumento();
        Integer id = doc.getInteger("codInterno");
        if (id != null)
            i.setCodInterno(id);
        i.setNome(doc.getString("nome"));
        return i;
    }

    public Instrumento buscarInstrumentoPorId(int id) {
        Document doc = mongo.getCollection("Instrumento").find(eq("codInterno", id)).first();
        if (doc == null)
            return null;
        Instrumento i = new Instrumento();
        Integer cid = doc.getInteger("codInterno");
        if (cid != null)
            i.setCodInterno(cid);
        i.setNome(doc.getString("nome"));
        return i;
    }

    public List<Musica> buscarMusicasDoDisco(int idDisco) {
        List<Musica> musicas = new ArrayList<>();
        FindIterable<Document> docs = mongo.getCollection("DiscoMusica").find(eq("identificador", idDisco));
        for (Document doc : docs) {
            Integer idMus = doc.getInteger("idMusica");
            if (idMus != null) {
                Musica m = buscarMusicaPorId(idMus);
                if (m != null)
                    musicas.add(m);
            }
        }
        return musicas;
    }

    public List<Autor> buscarAutoresDaMusica(int idMusica) {
        List<Autor> autores = new ArrayList<>();
        FindIterable<Document> docs = mongo.getCollection("MusicaAutor").find(eq("idMusica", idMusica));
        for (Document doc : docs) {
            Integer idAutor = doc.getInteger("idAutor");
            if (idAutor != null) {
                Autor a = buscarAutorPorId(idAutor);
                if (a != null) {
                    autores.add(a);
                }
            }
        }
        return autores;
    }
    
    public List<Instrumento> buscarInstrumentosDoMusico(int idMusico) {
        List<Instrumento> instrumentos = new ArrayList<>();
        FindIterable<Document> docs = mongo.getCollection("MusicoInstrumento").find(eq("nroRegistro", idMusico));
        for (Document doc : docs) {
            Integer cod = doc.getInteger("codInterno");
            if (cod != null) {
                Instrumento i = buscarInstrumentoPorId(cod);
                if (i != null)
                    instrumentos.add(i);
            }
        }
        return instrumentos;
    }

    public Produtor buscarProdutorPorId(int id) {
        Document doc = mongo.getCollection("Produtor").find(eq("idProdutor", id)).first();
        if (doc == null)
            return null;
        Produtor p = new Produtor();
        Integer ip = doc.getInteger("idProdutor");
        if (ip != null)
            p.setIdProdutor(ip);
        p.setNome(doc.getString("nome"));
        return p;
    }

    // --- UPDATES ---
    public void atualizarAutor(int id, String nome) {
        mongo.getCollection("Autor").updateOne(eq("idAutor", id), new Document("$set", new Document("nome", nome)));
    }

    public void atualizarBanda(int id, String nome) {
        mongo.getCollection("Banda").updateOne(eq("idBanda", id), new Document("$set", new Document("nome", nome)));
    }

    public void atualizarDisco(int id, String formato, java.sql.Date data, String titulo, Produtor prod, Banda banda) {
        Document set = new Document("formato", formato)
                .append("data", data != null ? new java.util.Date(data.getTime()) : null).append("titulo", titulo)
                .append("idProdutor", prod != null ? prod.getIdProdutor() : null)
                .append("idBanda", banda != null ? banda.getIdBanda() : null);
        mongo.getCollection("Disco").updateOne(eq("identificador", id), new Document("$set", set));
    }

    public void atualizarEndereco(int id, String cidade, String rua, int nroCasa, String telefone) {
        Document set = new Document("cidade", cidade).append("rua", rua).append("nroCasa", nroCasa).append("telefone",
                telefone);
        mongo.getCollection("Endereco").updateOne(eq("idEndereco", id), new Document("$set", set));
    }

    public void atualizarInstrumento(int id, String nome) {
        mongo.getCollection("Instrumento").updateOne(eq("codInterno", id),
                new Document("$set", new Document("nome", nome)));
    }

    public void atualizarMusica(int id, String titulo, int duracao) {
        mongo.getCollection("Musica").updateOne(eq("idMusica", id), new Document("$set",
                new Document("titulo", titulo).append("duracao", duracao)));
    }

    public void atualizarMusico(int id, String nome, Endereco end) {
        Integer idEnd = end != null ? end.getIdEndereco() : null;
        mongo.getCollection("Musico").updateOne(eq("nroRegistro", id),
                new Document("$set", new Document("nome", nome).append("idEndereco", idEnd)));
    }

    public void atualizarProdutor(int id, String nome) {
        mongo.getCollection("Produtor").updateOne(eq("idProdutor", id),
                new Document("$set", new Document("nome", nome)));
    }

    // --- DELETES ---
    public int excluirInstrumento(int id) {
        Document found = mongo.getCollection("MusicoInstrumento").find(eq("codInterno", id)).first();
        if (found != null)
            return 0;
        Document toDelete = mongo.getCollection("Instrumento").find(eq("codInterno", id)).first();
        if (toDelete != null) {
            mongo.getCollection("Instrumento").deleteOne(eq("codInterno", id));
            return 1;
        }
        return 0;
    }

    public int excluirDisco(int id) {
        for (Disco d : listarDiscos())
            if (d.getIdentificador() == id) {
                mongo.getCollection("Disco").deleteOne(eq("identificador", id));
                mongo.getCollection("DiscoMusica").deleteMany(eq("identificador", id));
                return 1;
            }
        return 0;
    }

    public int excluirEndereco(int id) {
        for (Musico m : listarMusicos())
            if (m.getEndereco() != null && m.getEndereco().getIdEndereco() == id)
                return 0;
        for (Endereco e : listarEnderecos()) {
            if (e.getIdEndereco() == id) {
                mongo.getCollection("Endereco").deleteOne(eq("idEndereco", id));
                return 1;
            }
        }
        return 0;
    }

    public int excluirBanda(int id) {
        for (Banda b : listarBandas())
            if (b.getIdBanda() == id) {
                mongo.getCollection("Banda").deleteOne(eq("idBanda", id));
                return 1;
            }
        return 0;
    }

    public int excluirMusica(int id) {
        for (Musica m : listarMusicas())
            if (m.getIdMusica() == id) {
                // find affected discos so we can decrement their counters
                List<Integer> discosAfetados = new ArrayList<>();
                FindIterable<Document> refs = mongo.getCollection("DiscoMusica").find(eq("idMusica", id));
                for (Document r : refs) {
                    Integer discId = r.getInteger("identificador");
                    if (discId != null && !discosAfetados.contains(discId)) discosAfetados.add(discId);
                }

                mongo.getCollection("Musica").deleteOne(eq("idMusica", id));
                mongo.getCollection("DiscoMusica").deleteMany(eq("idMusica", id));

                // decrement numMusicas for each affected disco (guard to not go negative)
                for (Integer discId : discosAfetados) {
                    mongo.getCollection("Disco").updateOne(eq("identificador", discId), new Document("$inc", new Document("numMusicas", -1)));
                }

                return 1;
            }
        return 0;
    }

    public int excluirMusico(int id) {
        for (Musico m : listarMusicos())
            if (m.getNroRegistro() == id) {
                mongo.getCollection("Musico").deleteOne(eq("nroRegistro", id));
                mongo.getCollection("MusicoInstrumento").deleteMany(eq("nroRegistro", id));
                return 1;
            }
        return 0;
    }

    public int excluirProdutor(int id) {
        for (Produtor p : listarProdutores())
            if (p.getIdProdutor() == id) {
                mongo.getCollection("Produtor").deleteOne(eq("idProdutor", id));
                return 1;
            }
        return 0;
    }

    public int verificarEndereco(int idEnd) {
        Document d = mongo.getCollection("Endereco").find(eq("idEndereco", idEnd)).first();
        return d == null ? 0 : 1;
    }

    public int excluirAutor(int id) {
        Document doc = mongo.getCollection("Autor").find(eq("idAutor", id)).first();
        if (doc != null) {
            mongo.getCollection("Autor").deleteOne(eq("idAutor", id));
            return 1;
        }
        return -1;
    }

    // --- ASSOCIATIONS ---

    public void associarMusica(Disco d, Musica m) {
        Document exists = mongo.getCollection("DiscoMusica")
                .find(new Document("identificador", d.getIdentificador()).append("idMusica", m.getIdMusica())).first();
        if (exists == null)
            mongo.getCollection("DiscoMusica")
                .insertOne(new Document("identificador", d.getIdentificador()).append("idMusica", m.getIdMusica()));
            // keep the Disco.numMusicas counter in sync
            mongo.getCollection("Disco").updateOne(eq("identificador", d.getIdentificador()), new Document("$inc", new Document("numMusicas", 1)));
    }

    public void associarInstrumento(Musico m, Instrumento i) {
        Document exists = mongo.getCollection("MusicoInstrumento")
                .find(new Document("nroRegistro", m.getNroRegistro()).append("codInterno", i.getCodInterno())).first();
        if (exists == null)
            mongo.getCollection("MusicoInstrumento")
                    .insertOne(new Document("nroRegistro", m.getNroRegistro()).append("codInterno", i.getCodInterno()));
    }

    public void associarAutor(Musica m, Autor a) {
        Document exists = mongo.getCollection("MusicaAutor")
                .find(new Document("idMusica", m.getIdMusica()).append("idAutor", a.getIdAutor())).first();
        if (exists == null)
            mongo.getCollection("MusicaAutor")
                .insertOne(new Document("idMusica", m.getIdMusica()).append("idAutor", a.getIdAutor()));
    }

    public void removerAutoresDaMusica(int idMusica) {
        mongo.getCollection("MusicaAutor").deleteMany(eq("idMusica", idMusica));
    }

    public void removerInstrumentosDoMusico(int idMusico) {
        mongo.getCollection("MusicoInstrumento").deleteMany(eq("nroRegistro", idMusico));
    }

    public void removerMusicasDoDisco(int idDisco) {
        mongo.getCollection("DiscoMusica").deleteMany(eq("identificador", idDisco));
        // reset the counter in the Disco document to 0 to reflect removal
        mongo.getCollection("Disco").updateOne(eq("identificador", idDisco), new Document("$set", new Document("numMusicas", 0)));
    }

    // helper counters
    private int getNextSequence(String name) {
        MongoCollection<Document> counters = mongo.getCollection("counters");
        FindOneAndUpdateOptions opts = new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER);
        Document res = counters.findOneAndUpdate(new Document("_id", name),
                new Document("$inc", new Document("seq", 1)), opts);
        if (res != null) {
            Object seqObj = res.get("seq");
            if (seqObj instanceof Number)
                return ((Number) seqObj).intValue();
        }
        return 1;
    }

}

