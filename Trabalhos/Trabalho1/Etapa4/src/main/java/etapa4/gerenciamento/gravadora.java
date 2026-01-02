package etapa4.gerenciamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import etapa4.dados.*;

public class gravadora {

    private Connection conexao;

    public gravadora(Connection conexao) {
        this.conexao = conexao;
    }

//*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-INSERCOES-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

    public void inserirAutor(Autor a) {
        String sql = "INSERT INTO Autor (nome) VALUES (?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, a.getNome());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void inserirBanda(Banda b) {
        String sql = "INSERT INTO Banda (nome) VALUES (?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, b.getNome());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void inserirDisco(Disco d) throws SQLException {
        String sql = "INSERT INTO Disco (formato, data, titulo, idProdutor, idBanda, numMusicas) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, d.getFormato());
            ps.setDate(2, d.getData());
            ps.setString(3, d.getTitulo());

            if (d.getProdutor() != null)
                ps.setInt(4, d.getProdutor().getIdProdutor());
            else
                ps.setNull(4, java.sql.Types.INTEGER);

            if (d.getBanda() != null)
                ps.setInt(5, d.getBanda().getIdBanda());
            else
                ps.setNull(5, java.sql.Types.INTEGER);

            ps.setInt(6, d.getMusicas() != null ? d.getMusicas().size() : 0);

            ps.executeUpdate(); 
        }
    }


    public void inserirMusica(Musica m) {
        String sql = "INSERT INTO Musica (titulo, duracao, autores) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, m.getTitulo());
            ps.setInt(2, m.getDuracao());
            ps.setString(3, m.getAutores()); // <- isso é importante
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void inserirEndereco(Endereco e) {
        String sql = "INSERT INTO Endereco (cidade, rua, nroCasa, telefone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, e.getCidade());
            ps.setString(2, e.getRua());
            ps.setInt(3, e.getNroCasa());
            ps.setString(4, e.getTelefone());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void inserirInstrumento(Instrumento i) { 
        String sql = "INSERT INTO Instrumento (nome) VALUES (?)"; 
        try (PreparedStatement ps = conexao.prepareStatement(sql)) { 
            ps.setString(1, i.getNome()); 
            ps.executeUpdate(); 
        } catch (SQLException ex) { 
            ex.printStackTrace(); 
        } 
    }

    public void inserirMusico(Musico m) {
        String sql = "INSERT INTO Musico (nome, idEndereco) VALUES (?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getNome());
            ps.setInt(2, m.getEndereco().getIdEndereco());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) {
                    m.setNroRegistro(rs.getInt(1));
                }
            }

            System.out.println("Músico inserido com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void inserirProdutor(Produtor p) {
        String sql = "INSERT INTO Produtor (nome) VALUES (?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

//*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-buscas-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
    public Autor buscarAutorPorId(int id) {
        String sql = "SELECT * FROM Autor WHERE idAutor = ?";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Autor a = new Autor();
                    a.setIdAutor(rs.getInt("idAutor"));
                    a.setNome(rs.getString("nome"));
                    return a;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public Disco buscarDiscoPorId(int id) {
        String sql = "SELECT * FROM Disco WHERE identificador = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Integer idProd = (Integer) rs.getObject("idProdutor");
                    Produtor produtor = idProd != null ? buscarProdutorPorId(idProd) : null;

                    Integer idBanda = (Integer) rs.getObject("idBanda");
                    Banda banda = idBanda != null ? buscarBandaPorId(idBanda) : null;

                    List<Musica> musicas = new ArrayList<>();

                    Disco d = new Disco(
                        rs.getString("formato"),
                        rs.getDate("data"),
                        rs.getString("titulo"),
                        produtor,
                        banda,
                        musicas
                    );
                    d.setIdentificador(rs.getInt("identificador"));
                    d.setNumMusicas(rs.getInt("numMusicas"));

                    return d;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Disco buscarDiscoPorTitulo(String titulo) {
        String sql = "SELECT * FROM Disco WHERE titulo = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, titulo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Disco d = new Disco(
                        rs.getString("formato"),
                        rs.getDate("data"),
                        rs.getString("titulo"),
                        buscarProdutorPorId(rs.getInt("idProdutor")),
                        buscarBandaPorId(rs.getInt("idBanda")),
                        new ArrayList<>()
                    );

                    d.setIdentificador(rs.getInt("identificador"));
                    d.setNumMusicas(rs.getInt("numMusicas"));

                    // Opcional: já buscar músicas associadas
                    List<Musica> musicas = buscarMusicasDoDisco(d.getIdentificador());
                    d.setMusicas(musicas);

                    return d;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public Endereco buscarEnderecoPorId(int id) {
        String sql = "SELECT * FROM Endereco WHERE idEndereco = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Endereco e = new Endereco();
                    e.setIdEndereco(rs.getInt("idEndereco"));
                    e.setCidade(rs.getString("cidade"));
                    e.setRua(rs.getString("rua"));
                    e.setNroCasa(rs.getInt("nroCasa"));
                    e.setTelefone(rs.getString("telefone"));
                    return e;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Musica buscarMusicaPorId(int id) {
        String sql = "SELECT * FROM Musica WHERE idMusica = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Musica m = new Musica();
                    m.setIdMusica(rs.getInt("idMusica"));
                    m.setTitulo(rs.getString("titulo"));
                    m.setDuracao(rs.getInt("duracao"));
                    m.setAutores(rs.getString("autores"));
                    return m;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Musico buscarMusicoPorId(int id) {
        String sql = "SELECT * FROM Musico WHERE nroRegistro = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Musico m = new Musico();
                    m.setNroRegistro(rs.getInt("nroRegistro"));
                    m.setNome(rs.getString("nome"));

                    int idEnd = rs.getInt("idEndereco");
                    Endereco end = buscarEnderecoPorId(idEnd);
                    m.setEndereco(end);

                    return m;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-listas-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
    public List<Autor> listarAutores() {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Autor";

        try (PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Autor a = new Autor();
                a.setIdAutor(rs.getInt("idAutor"));
                a.setNome(rs.getString("nome"));
                lista.add(a);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Banda> listarBandas() {
        List<Banda> lista = new ArrayList<>();

        String sql = "SELECT * from Banda";
        try (PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Banda b = new Banda();
                b.setIdBanda(rs.getInt("idBanda"));
                b.setNome(rs.getString("nome"));
                lista.add(b);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Endereco> listarEnderecos() {
        List<Endereco> lista = new ArrayList<>();
        String sql = "SELECT * FROM Endereco";

        try (PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Endereco e = new Endereco();
                e.setIdEndereco(rs.getInt("idEndereco"));
                e.setCidade(rs.getString("cidade"));
                e.setRua(rs.getString("rua"));
                e.setNroCasa(rs.getInt("nroCasa"));
                e.setTelefone(rs.getString("telefone"));
                lista.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Musica> listarMusicas() {
        List<Musica> lista = new ArrayList<>();
        String sql = "SELECT idMusica, titulo, duracao, autores FROM Musica";

        try (PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Musica m = new Musica();
                m.setIdMusica(rs.getInt("idMusica"));
                m.setTitulo(rs.getString("titulo"));
                m.setDuracao(rs.getInt("duracao"));
                m.setAutores(rs.getString("autores"));

                lista.add(m);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Disco> listarDiscos() {
        List<Disco> lista = new ArrayList<>();

        String sql = """
            SELECT d.identificador, d.formato, d.data, d.titulo,
                d.idProdutor, d.idBanda, d.numMusicas
            FROM Disco d
            ORDER BY d.identificador
        """;


        try (PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Integer idProdutor = (Integer) rs.getObject("idProdutor");
                Produtor produtor = idProdutor != null ? buscarProdutorPorId(idProdutor) : null;

                Integer idBanda = (Integer) rs.getObject("idBanda");
                Banda banda = idBanda != null ? buscarBandaPorId(idBanda) : null;

                int idDisco = rs.getInt("identificador");

                Disco d = new Disco(
                    rs.getString("formato"),
                    rs.getDate("data"),
                    rs.getString("titulo"),
                    produtor,
                    banda,
                    buscarMusicasDoDisco(idDisco)
                );
                d.setIdentificador(rs.getInt("identificador"));
                d.setNumMusicas(rs.getInt("numMusicas"));

                lista.add(d);
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Musico> listarMusicos() {
        List<Musico> lista = new ArrayList<>();
        String sql = """
            SELECT m.nroRegistro, m.nome,
                e.idEndereco, e.cidade, e.rua, e.nroCasa, e.telefone
            FROM Musico m
            LEFT JOIN Endereco e ON m.idEndereco = e.idEndereco
        """;

        try (PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Endereco e = new Endereco();
                e.setIdEndereco(rs.getInt("idEndereco"));
                e.setCidade(rs.getString("cidade"));
                e.setRua(rs.getString("rua"));
                e.setNroCasa(rs.getInt("nroCasa"));
                e.setTelefone(rs.getString("telefone"));

                Musico m = new Musico();
                m.setNroRegistro(rs.getInt("nroRegistro"));
                m.setNome(rs.getString("nome"));
                m.setEndereco(e);

                List<Instrumento> instrumentos = listarInstrumentosDoMusico(m.getNroRegistro());
                m.setInstrumentos(instrumentos);

                lista.add(m);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Instrumento> listarInstrumentos() {
        List<Instrumento> lista = new ArrayList<>();
        String sql = "SELECT codInterno, nome FROM Instrumento ORDER BY codInterno";

        try (PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Instrumento i = new Instrumento();
                i.setCodInterno(rs.getInt("codInterno"));
                i.setNome(rs.getString("nome"));
                lista.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Produtor> listarProdutores() {
        List<Produtor> lista = new ArrayList<>();
        String sql = "SELECT idProdutor, nome FROM Produtor";

        try (PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Produtor p = new Produtor();
                p.setIdProdutor(rs.getInt("idProdutor"));
                p.setNome(rs.getString("nome"));
                lista.add(p);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    public List<Instrumento> listarInstrumentosDoMusico(int nroRegistro) {
        List<Instrumento> lista = new ArrayList<>();
        String sql = """
            SELECT i.codInterno, i.nome
            FROM Instrumento i
            JOIN MusicoInstrumento mi ON i.codInterno = mi.codInterno
            WHERE mi.nroRegistro = ?
        """;

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, nroRegistro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Instrumento i = new Instrumento();
                    i.setCodInterno(rs.getInt("codInterno"));
                    i.setNome(rs.getString("nome"));
                    lista.add(i);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public Banda buscarBandaPorId(int id) {
        String sql = "SELECT * FROM Banda WHERE idBanda = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Banda b = new Banda();
                    b.setIdBanda(rs.getInt("idBanda"));
                    b.setNome(rs.getString("nome"));
                    return b;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Musica buscarMusicaPorNome(String titulo) {
        String sql = "SELECT * FROM Musica WHERE titulo = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, titulo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Musica m = new Musica();
                    m.setIdMusica(rs.getInt("idMusica"));
                    m.setTitulo(rs.getString("titulo"));
                    m.setDuracao(rs.getInt("duracao"));
                    m.setAutores(rs.getString("autores"));
                    return m;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public Musico buscarMusicoPorNome(String nome) {
        String sql = """
            SELECT m.nroRegistro, m.nome,
                e.idEndereco, e.cidade, e.rua, e.nroCasa, e.telefone
            FROM Musico m
            LEFT JOIN Endereco e ON m.idEndereco = e.idEndereco
            WHERE m.nome = ?
        """;

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Endereco e = new Endereco();
                    e.setIdEndereco(rs.getInt("idEndereco"));
                    e.setCidade(rs.getString("cidade"));
                    e.setRua(rs.getString("rua"));
                    e.setNroCasa(rs.getInt("nroCasa"));
                    e.setTelefone(rs.getString("telefone"));

                    Musico m = new Musico();
                    m.setNroRegistro(rs.getInt("nroRegistro"));
                    m.setNome(rs.getString("nome"));
                    m.setEndereco(e);
                    return m;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Instrumento buscarInstrumentoPorNome(String nome) {
        String sql = "SELECT * FROM Instrumento WHERE nome = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Instrumento i = new Instrumento();
                    i.setCodInterno(rs.getInt("codInterno"));
                    i.setNome(rs.getString("nome"));
                    return i;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Instrumento buscarInstrumentoPorId(int id) {
        String sql = "SELECT * FROM Instrumento WHERE codInterno = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Instrumento i = new Instrumento();
                    i.setCodInterno(rs.getInt("codInterno"));
                    i.setNome(rs.getString("nome"));
                    return i;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Musica> buscarMusicasDoDisco(int idDisco) {
        List<Musica> musicas = new ArrayList<>();

        String sql = """
            SELECT m.idMusica, m.titulo, m.duracao, m.autores
            FROM DiscoMusica dm
            JOIN Musica m ON dm.idMusica = m.idMusica
            WHERE dm.identificador = ?
        """;

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idDisco);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Musica musica = new Musica(
                        rs.getInt("idMusica"),
                        rs.getString("titulo"),
                        rs.getInt("duracao"),
                        rs.getString("autores")
                    );
                    musicas.add(musica);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return musicas;
    }

    public List<Instrumento> buscarInstrumentosDoMusico(int idMusico) {
        List<Instrumento> instrumentos = new ArrayList<>();
        String sql = """
            SELECT i.codInterno, i.nome
            FROM Instrumento i
            JOIN Musico_Instrumento mi ON i.codInterno = mi.codInterno
            WHERE mi.nroRegistro = ?
        """;

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idMusico);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Instrumento i = new Instrumento();
                    i.setCodInterno(rs.getInt("codInterno"));
                    i.setNome(rs.getString("nome"));
                    instrumentos.add(i);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instrumentos;
    }
    
    public Produtor buscarProdutorPorId(int id) {
        String sql = "SELECT * FROM Produtor WHERE idProdutor = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produtor p = new Produtor();
                    p.setIdProdutor(rs.getInt("idProdutor"));
                    p.setNome(rs.getString("nome"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-atualizacoes-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
    public void atualizarAutor(int id, String nome) {
        String sql = "UPDATE Autor SET nome = ? WHERE idAutor = ?";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarBanda(int id, String nome) {
        String sql = "UPDATE Banda SET nome = ? WHERE idBanda = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarDisco(int id, String formato, Date data, String titulo, Produtor prod, Banda banda) {
        String sql = """
            UPDATE Disco 
            SET formato = ?, data = ?, titulo = ?, 
                idProdutor = ?, idBanda = ?
            WHERE identificador = ?
        """;

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, formato);
            ps.setDate(2, data);
            ps.setString(3, titulo);

            if (prod != null) ps.setInt(4, prod.getIdProdutor());
            else ps.setNull(4, java.sql.Types.INTEGER);

            if (banda != null) ps.setInt(5, banda.getIdBanda());
            else ps.setNull(5, java.sql.Types.INTEGER);

            ps.setInt(6, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void atualizarEndereco(int id, String cidade, String rua, int nroCasa, String telefone) {
        String sql = "UPDATE Endereco SET cidade = ?, rua = ?, nroCasa = ?, telefone = ? WHERE idEndereco = ?";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, cidade);
            ps.setString(2, rua);
            ps.setInt(3, nroCasa);
            ps.setString(4, telefone);
            ps.setInt(5, id);
            ps.executeUpdate();
            System.out.println("Endereço atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar endereço.");
        }
    }

    public void atualizarInstrumento(int id, String nome) {
        String sql = "UPDATE Instrumento SET nome = ? WHERE codInterno = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarMusica(int id, String titulo, int duracao, String autores) {
        String sql = "UPDATE Musica SET titulo = ?, duracao = ?, autores = ? WHERE idMusica = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setInt(2, duracao);
            ps.setString(3, autores);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void atualizarMusico(int id, String nome, Endereco end) {
        String sql = "UPDATE Musico SET nome = ?, idEndereco = ? WHERE nroRegistro = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, nome);
            if (end != null) {
                ps.setInt(2, end.getIdEndereco());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarProdutor(int id, String nome) {
        String sql = "UPDATE Produtor SET nome = ? WHERE idProdutor = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-exclusoes-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
    public int excluirInstrumento(int id) {
        String checkSql = "SELECT 1 FROM MusicoInstrumento WHERE codInterno = ? LIMIT 1";
        try (PreparedStatement ps = conexao.prepareStatement(checkSql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

        List<Instrumento> instrumentos = listarInstrumentos();
        if (instrumentos.isEmpty()) {
            return 0;
        }

        String deleteSql = "DELETE FROM Instrumento WHERE codInterno = ?";
        for (Instrumento i : instrumentos) {
            if (i.getCodInterno() == id) {
                try (PreparedStatement ps2 = conexao.prepareStatement(deleteSql)) {
                    ps2.setInt(1, id);
                    ps2.executeUpdate();
                    return 1;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return 0;
                }
            }
        }
        return 0;
    }

    public int excluirDisco(int id) {
        String sql = "DELETE FROM Disco WHERE identificador = ?";

        if (listarDiscos().isEmpty()) {
            return 0;
        }

        for (Disco d : listarDiscos()) {
            if (d.getIdentificador() == id) {
                try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    return 1;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return 0;
                }
            }
        }

        System.out.println("Disco não encontrado.");
        return 0;
    }

    public int excluirEndereco(int id) { 
        String sql = "DELETE FROM Endereco WHERE idEndereco = ?"; 
        for(Musico m : listarMusicos()) { 
            if(m.getEndereco().getIdEndereco() == id) { 
                return 0; 
            } 
        }

        if(listarEnderecos().isEmpty()) { 
            return 0; 
        }

        for(Endereco e : listarEnderecos()) { 
            if(e.getIdEndereco() == id) { 
                try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                    ps.setInt(1, id); ps.executeUpdate(); 
                    return 1; 
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return 0;
                }
            } 
        }
        return 0; 
    }

    public int excluirBanda(int id) {
        String sql = "DELETE FROM Banda WHERE idBanda = ?";

        if (listarBandas().isEmpty()) {
            System.out.println("Nenhuma banda cadastrada.");
            return 0;
        }

        for (Banda b : listarBandas()) {
            if (b.getIdBanda() == id) {
                try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    return 1;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return 0;
                }
            }
        }
        return 0;
    }

    public int excluirMusica(int id) {
        String sql = "DELETE FROM Musica WHERE idMusica = ?";

        if (listarMusicas().isEmpty()) {
            System.out.println("Nenhuma música cadastrada.");
            return 0;
        }

        for (Musica m : listarMusicas()) {
            if (m.getIdMusica() == id) {
                try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    System.out.println("Música excluída com sucesso!");
                    return 1;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return 0;
                }
            }
        }

        System.out.println("Música não encontrada.");
        return 0;
    }


    public int excluirMusico(int id) {
        String sql = "DELETE FROM Musico WHERE nroRegistro = ?";

        if (listarMusicos().isEmpty()) {
            return 0;
        }

        for (Musico m : listarMusicos()) {
            if (m.getNroRegistro() == id) {
                try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    return 1;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return 0;
                }
            }
        }
        return 0;
    }

    public int excluirProdutor(int id) {
        String sql = "DELETE FROM Produtor WHERE idProdutor = ?";

        if (listarProdutores().isEmpty()) {
            System.out.println("Nenhum produtor cadastrado.");
            return 0;
        }

        for (Produtor p : listarProdutores()) {
            if (p.getIdProdutor() == id) {
                try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    System.out.println("Produtor excluído com sucesso!");
                    return 1;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return 0;
                }
            }
        }

        System.out.println("Produtor não encontrado.");
        return 0;
    }


    public int verificarEndereco(int idEnd) {
        String sql = "SELECT * FROM Endereco WHERE idEndereco = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idEnd);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Endereco nao encontrado. Insira um ID valido.");
                    return 0;
                }
                return 1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int excluirAutor(int id) {
        String sql = "DELETE FROM Autor WHERE idAutor = ?";

        for(Autor a : listarAutores()) {
            if(a.getIdAutor() == id) {
                try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    return 1;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
        return -1;
    }


    public void associarMusicoInstrumento(int idMusico, int idInstrumento) {
        String sql = "INSERT INTO Musico_Instrumento (nroRegistro, codInterno) VALUES (?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idMusico);
            ps.setInt(2, idInstrumento);
            ps.executeUpdate();
        } catch (SQLException e) {
            if (!e.getMessage().contains("duplicate key")) {
                e.printStackTrace();
            }
        }
    }

    public void associarMusica(Disco d, Musica m) {
        String sql = "INSERT INTO DiscoMusica (identificador, idMusica) VALUES (?, ?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, d.getIdentificador());
            ps.setInt(2, m.getIdMusica());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void associarInstrumento(Musico m, Instrumento i) {
        String sql = "INSERT INTO MusicoInstrumento (nroRegistro, codInterno) VALUES (?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, m.getNroRegistro());
            ps.setInt(2, i.getCodInterno());
            ps.executeUpdate();
            //System.out.println("Instrumento associado ao musico!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removerInstrumentosDoMusico(int idMusico) {
        String sql = "DELETE FROM Musico_Instrumento WHERE nroRegistro = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idMusico);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void removerMusicasDoDisco(int idDisco) {
        String sql = "DELETE FROM DiscoMusica WHERE identificador = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idDisco);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}