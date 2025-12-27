CREATE TABLE Setor (
    cods SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE Cliente (
    codc SERIAL PRIMARY KEY,
    cpf CHAR(11) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    idade INT CHECK (idade >= 0),
    endereco VARCHAR(150),
    cidade VARCHAR(100)
);

CREATE TABLE Mecanico (
    codm SERIAL PRIMARY KEY,
    cpf CHAR(11) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    idade INT CHECK (idade >= 0),
    endereco VARCHAR(150),
    cidade VARCHAR(100),
    funcao VARCHAR(50),
    cods INT NOT NULL,
    CONSTRAINT fk_mecanico_setor FOREIGN KEY (cods) REFERENCES Setor (cods)
);

CREATE TABLE Veiculo (
    codv SERIAL PRIMARY KEY,
    renavam CHAR(11) UNIQUE NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    marca VARCHAR(50),
    ano INT CHECK (ano >= 1886),
    quilometragem NUMERIC(10,1) DEFAULT 0,
    codc INT NOT NULL,
    CONSTRAINT fk_veiculo_cliente FOREIGN KEY (codc) REFERENCES Cliente (codc)
);

CREATE TABLE Conserto (
    codm INT NOT NULL,
    codv INT NOT NULL,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    PRIMARY KEY (codm, codv, data, hora),
    CONSTRAINT fk_conserto_mecanico FOREIGN KEY (codm) REFERENCES Mecanico (codm),
    CONSTRAINT fk_conserto_veiculo FOREIGN KEY (codv) REFERENCES Veiculo (codv)
);
