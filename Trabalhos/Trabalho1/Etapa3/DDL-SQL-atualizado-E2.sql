CREATE TABLE Endereco (
    idEndereco SERIAL,
    cidade VARCHAR(100) NOT NULL,
    rua VARCHAR(100) NOT NULL,
    nroCasa INT NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    CONSTRAINT endereco_pkey PRIMARY KEY (idEndereco)
);

CREATE TABLE Musico (
    nroRegistro SERIAL,
    nome VARCHAR(200) NOT NULL,
    idEndereco INT,
    CONSTRAINT musico_pkey PRIMARY KEY (nroRegistro),
    CONSTRAINT musico_endereco_fkey FOREIGN KEY (idEndereco)
        REFERENCES Endereco (idEndereco)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

CREATE TABLE Autor (
	idAutor SERIAL,
	nome VARCHAR(200),
	CONSTRAINT autor_pkey PRIMARY KEY (idAutor)
);

CREATE TABLE Instrumento (
    codInterno SERIAL,
    nome VARCHAR(200) NOT NULL,
    CONSTRAINT instrumento_pkey PRIMARY KEY (codInterno)
);

CREATE TABLE Banda (
    idBanda SERIAL,
    nome VARCHAR(200) NOT NULL,
    CONSTRAINT banda_pkey PRIMARY KEY (idBanda)
);

CREATE TABLE Musica (
    idMusica SERIAL,
    titulo VARCHAR(200) NOT NULL,
    duracao INT NOT NULL,
    -- autores poderiam estar em tabela separada,
    -- mas se for string simples:
    autores VARCHAR(500),

    CONSTRAINT musica_pkey PRIMARY KEY (idMusica)
);

CREATE TABLE Produtor (
    idProdutor SERIAL,
    nome VARCHAR(200) NOT NULL,
    CONSTRAINT produtor_pkey PRIMARY KEY (idProdutor)
);

CREATE TABLE Disco (
    identificador SERIAL,
    formato VARCHAR(50) NOT NULL,
    data DATE NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    idProdutor INT,
    nroRegistro INT,
    idBanda INT,
    numMusicas INT DEFAULT 0,
    CONSTRAINT disco_pkey PRIMARY KEY (identificador),
    CONSTRAINT disco_produtor_fkey FOREIGN KEY (idProdutor)
        REFERENCES Produtor (idProdutor)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    CONSTRAINT disco_musico_fkey FOREIGN KEY (nroRegistro)
        REFERENCES Musico (nroRegistro)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    CONSTRAINT disco_banda_fkey FOREIGN KEY (idBanda)
        REFERENCES Banda (idBanda)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

CREATE TABLE MusicoInstrumento (
    nroRegistro INT NOT NULL,
    codInterno INT NOT NULL,
    CONSTRAINT musico_instrumento_pkey PRIMARY KEY (nroRegistro, codInterno),
    CONSTRAINT mi_musico_fkey FOREIGN KEY (nroRegistro)
        REFERENCES Musico (nroRegistro)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT mi_instrumento_fkey FOREIGN KEY (codInterno)
        REFERENCES Instrumento (codInterno)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Relacoes :)

CREATE TABLE MusicoBanda (
    nroRegistro INT NOT NULL,
    idBanda INT NOT NULL,
    CONSTRAINT musico_banda_pkey PRIMARY KEY (nroRegistro, idBanda),
    CONSTRAINT mb_musico_fkey FOREIGN KEY (nroRegistro)
        REFERENCES Musico (nroRegistro)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT mb_banda_fkey FOREIGN KEY (idBanda)
        REFERENCES Banda (idBanda)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE DiscoMusica (
    identificador INT NOT NULL,
    idMusica INT NOT NULL,
    CONSTRAINT disco_musica_pkey PRIMARY KEY (identificador, idMusica),
    CONSTRAINT dm_disco_fkey FOREIGN KEY (identificador)
        REFERENCES Disco (identificador)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT dm_musica_fkey FOREIGN KEY (idMusica)
        REFERENCES Musica (idMusica)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE Participacao (
    idMusica INT NOT NULL,
    nroRegistro INT,
    idBanda INT,
    CONSTRAINT participacao_pkey PRIMARY KEY (idMusica, nroRegistro, idBanda),
    CONSTRAINT part_musica_fkey FOREIGN KEY (idMusica)
        REFERENCES Musica (idMusica)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT part_musico_fkey FOREIGN KEY (nroRegistro)
        REFERENCES Musico (nroRegistro)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    CONSTRAINT part_banda_fkey FOREIGN KEY (idBanda)
        REFERENCES Banda (idBanda)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

CREATE TABLE MusicaAutor (
    idMusica INT NOT NULL,
    idAutor INT NOT NULL,
    CONSTRAINT musica_autor_pkey PRIMARY KEY (idMusica, idAutor),
    CONSTRAINT ma_musica_fkey FOREIGN KEY (idMusica)
        REFERENCES Musica (idMusica)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT ma_autor_fkey FOREIGN KEY (idAutor)
        REFERENCES Autor (idAutor)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);