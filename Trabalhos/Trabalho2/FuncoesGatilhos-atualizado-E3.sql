-- Gatilho 1 – Disco pertence a banda OU músico, nunca ambos

CREATE OR REPLACE FUNCTION verificar_dono_disco()
RETURNS trigger AS $$
BEGIN
    IF (NEW.idBanda IS NOT NULL AND NEW.nroRegistro IS NOT NULL) THEN
        RAISE EXCEPTION 'O disco deve pertencer a UMA banda OU a UM musico, nunca aos dois.';
    ELSIF (NEW.idBanda IS NULL AND NEW.nroRegistro IS NULL) THEN
        RAISE EXCEPTION 'O disco deve pertencer a uma banda OU a um musico.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_dono_disco
BEFORE INSERT OR UPDATE ON Disco
FOR EACH ROW
EXECUTE FUNCTION verificar_dono_disco();

---------------------------------------------------------------------------------------------------------------------
-- Gatilho 2 – Verificar se o formato do disco é válido

CREATE OR REPLACE FUNCTION validar_formato_disco()
RETURNS trigger AS $$
BEGIN
    IF UPPER(NEW.formato) NOT IN ('CD', 'MC', 'K7') THEN
        RAISE EXCEPTION 'Formato de disco invalido. Use CD, MC ou K7.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_validar_formato
BEFORE INSERT OR UPDATE ON Disco
FOR EACH ROW
EXECUTE FUNCTION validar_formato_disco();

---------------------------------------------------------------------------------------------------------------------
-- Gatilho 3 – Evitar discos com zero músicas

CREATE OR REPLACE FUNCTION impedir_disco_sem_musicas()
RETURNS trigger AS $$
DECLARE
    qtd INT;
BEGIN
    SELECT COUNT(*) INTO qtd FROM DiscoMusica WHERE identificador = OLD.identificador;
    IF qtd = 1 THEN
        RAISE EXCEPTION 'O disco nao pode ficar sem musicas.';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_disco_sem_musicas
BEFORE DELETE ON DiscoMusica
FOR EACH ROW
EXECUTE FUNCTION impedir_disco_sem_musicas();

---------------------------------------------------------------------------------------------------------------------
-- Gatilho 4 – Atualizar número de músicas por disco automaticamente

CREATE OR REPLACE FUNCTION atualizar_num_musicas()
RETURNS trigger AS $$
DECLARE
    qtd INT;
BEGIN
    SELECT COUNT(*) INTO qtd FROM DiscoMusica WHERE identificador = COALESCE(NEW.identificador, OLD.identificador);
    UPDATE Disco SET num_musicas = qtd WHERE identificador = COALESCE(NEW.identificador, OLD.identificador);
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_num_musicas_insert
AFTER INSERT ON DiscoMusica
FOR EACH ROW
EXECUTE FUNCTION atualizar_num_musicas();

CREATE TRIGGER trigger_num_musicas_delete
AFTER DELETE ON DiscoMusica
FOR EACH ROW
EXECUTE FUNCTION atualizar_num_musicas();

---------------------------------------------------------------------------------------------------------------------
-- Gatilho 5 – Impedir exclusão de instrumento em uso

CREATE OR REPLACE FUNCTION impedir_exclusao_instrumento_em_uso()
RETURNS trigger AS $$
DECLARE
    qtd INT;
BEGIN
    SELECT COUNT(*) INTO qtd FROM MusicoInstrumento WHERE codInterno = OLD.codInterno;
    IF qtd > 0 THEN
        RAISE EXCEPTION 'Nao eh possivel excluir o instrumento: ainda esta sendo usado por musicos.';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_instrumento_em_uso
BEFORE DELETE ON Instrumento
FOR EACH ROW
EXECUTE FUNCTION impedir_exclusao_instrumento_em_uso();

-- Funcoes consultas

-- Função 1 – Listar todos os discos em que um músico participou

CREATE OR REPLACE FUNCTION discos_por_musico(p_nroRegistro INT)
RETURNS TABLE(titulo TEXT, data DATE) AS $$
BEGIN
    RETURN QUERY
    SELECT DISTINCT d.titulo::TEXT, d.data
    FROM Disco d
    JOIN DiscoMusica dm ON dm.identificador = d.identificador
    JOIN Participacao p ON p.idMusica = dm.idMusica
    WHERE p.nroRegistro = p_nroRegistro
       OR d.nroRegistro = p_nroRegistro;
END;
$$ LANGUAGE plpgsql;


SELECT * FROM discos_por_musico(1);

---------------------------------------------------------------------------------------------------------------------
-- Função 2 – Retornar os instrumentos que um músico sabe tocar

CREATE OR REPLACE FUNCTION instrumentos_por_musico(p_nroRegistro INT)
RETURNS TABLE(nome_instrumento TEXT) AS $$
BEGIN
    RETURN QUERY
    SELECT i.nome::TEXT
    FROM Instrumento i
    JOIN MusicoInstrumento mi ON mi.codInterno = i.codInterno
    WHERE mi.nroRegistro = p_nroRegistro;
END;
$$ LANGUAGE plpgsql;

SELECT * FROM instrumentos_por_musico(1);

---------------------------------------------------------------------------------------------------------------------
-- Função 3 – Calcular a duração total de um disco

CREATE OR REPLACE FUNCTION duracao_total_disco(p_identificador INT)
RETURNS INT AS $$
DECLARE
    total INT;
BEGIN
    SELECT SUM(m.duracao) INTO total
    FROM Musica m
    JOIN DiscoMusica dm ON dm.idMusica = m.idMusica
    WHERE dm.identificador = p_identificador;

    RETURN COALESCE(total, 0);
END;
$$ LANGUAGE plpgsql;

SELECT duracao_total_disco(5);