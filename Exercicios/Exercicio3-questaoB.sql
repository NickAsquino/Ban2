CREATE TABLE curso (
    sigla_curso CHAR(10) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    titulacao VARCHAR(100)
);

CREATE TABLE disciplina (
    sigla_disc CHAR(10) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    carga_horaria INTEGER CHECK (carga_horaria > 0)
);

CREATE TABLE requisito (
    sigla_curso_req CHAR(10) NOT NULL,
    sigla_disc_req CHAR(10) NOT NULL,
    sigla_curso CHAR(10) NOT NULL,
    sigla_disc CHAR(10) NOT NULL,
    PRIMARY KEY (sigla_curso_req, sigla_disc_req, sigla_curso, sigla_disc),
    FOREIGN KEY (sigla_curso_req) REFERENCES curso(sigla_curso),
    FOREIGN KEY (sigla_disc_req) REFERENCES disciplina(sigla_disc),
    FOREIGN KEY (sigla_curso) REFERENCES curso(sigla_curso),
    FOREIGN KEY (sigla_disc) REFERENCES disciplina(sigla_disc)
);

CREATE TABLE grade (
    sigla_curso CHAR(10) NOT NULL,
    sigla_disc CHAR(10) NOT NULL,
    PRIMARY KEY (sigla_curso, sigla_disc),
    FOREIGN KEY (sigla_curso) REFERENCES curso(sigla_curso),
    FOREIGN KEY (sigla_disc) REFERENCES disciplina(sigla_disc)
);

CREATE TABLE aluno (
    cpf CHAR(11) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    rua VARCHAR(255),
    numero INTEGER,
	cidade VARCHAR(100),
    estado CHAR(2),
    cep INTEGER
);

CREATE TABLE inscricao (
    matricula INTEGER PRIMARY KEY,
    sigla_curso CHAR(10) NOT NULL,
    cpf CHAR(11) NOT NULL,
    FOREIGN KEY (sigla_curso) REFERENCES curso(sigla_curso),
    FOREIGN KEY (cpf) REFERENCES aluno(cpf)
);

CREATE TABLE matricula (
    ano DATE NOT NULL,
    matricula INTEGER NOT NULL,
    sigla_disc CHAR(10) NOT NULL,
    PRIMARY KEY (ano, matricula, sigla_disc),
    FOREIGN KEY (matricula) REFERENCES inscricao(matricula),
    FOREIGN KEY (sigla_disc) REFERENCES disciplina(sigla_disc)
);

CREATE TABLE professor (
    reg_mec INTEGER PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    rua VARCHAR(255),
    numero INTEGER,
    cidade VARCHAR(100),
    estado CHAR(2),
    cep INTEGER
);

CREATE TABLE area (
    cod_area INTEGER PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
);

CREATE TABLE titulo (
    reg_mec INTEGER NOT NULL,
    cod_area INTEGER NOT NULL,
    data_de_titulacao DATE NOT NULL,
    PRIMARY KEY (reg_mec, cod_area),
    FOREIGN KEY (reg_mec) REFERENCES professor(reg_mec),
    FOREIGN KEY (cod_area) REFERENCES area(cod_area)
);

CREATE TABLE responsavel (
    reg_mec INTEGER NOT NULL,
    cod_area INTEGER NOT NULL,
    data_da_titulacao DATE NOT NULL,
    PRIMARY KEY (reg_mec, cod_area),
    FOREIGN KEY (reg_mec) REFERENCES professor(reg_mec),
    FOREIGN KEY (cod_area) REFERENCES area(cod_area)
);

CREATE TABLE habilitacao (
    sigla_disc CHAR(10) NOT NULL,
    reg_mec INTEGER NOT NULL,
    PRIMARY KEY (sigla_disc, reg_mec),
    FOREIGN KEY (sigla_disc) REFERENCES disciplina(sigla_disc),
    FOREIGN KEY (reg_mec) REFERENCES professor(reg_mec)
);

CREATE TABLE leciona (
    sigla_disc CHAR(10) NOT NULL,
    reg_mec INTEGER NOT NULL,
    ano DATE NOT NULL,
    semestre INTEGER CHECK (semestre IN (1, 2)),
    PRIMARY KEY (sigla_disc, reg_mec, ano, semestre),
    FOREIGN KEY (sigla_disc) REFERENCES disciplina(sigla_disc),
    FOREIGN KEY (reg_mec) REFERENCES professor(reg_mec)
);
