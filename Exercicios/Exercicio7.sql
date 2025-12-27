-- 1
create or replace function insere_mecanico(
	pcpf char(11),
	pnome char(50),
	pidade int,
	pendereco char(50),
	pcidade char(500),
	pfuncao char(50), 
	pcods int
)

returns void as
$$ 
begin
	insert into mecanico (cpf, nome, idade, endereco, cidade, funcao, cods)
    values (pcpf, pnome, pidade, pendereco, pcidade, pfuncao, pcods);
end;
$$
language plpgsql;

select * from mecanico;

-- 2
create or replace function exclui_mecanico(pcodm int)

returns void as
$$ 
begin
	delete from mecanico where codm = pcodm;
end;
$$
language plpgsql;

-- select exclui_mecanico(1);

select * from mecanico;
	
-- 3
drop function atualiza_cliente(
	op char(1),
	pcodc int,
	pcpf char(11),
	pnome varchar(50),
	pidade int,
	pendereco varchar(500),
	pcidade
)
$$
begin
	if(upper(op) = 'I') then
	insert into cliente values(pcodc, pcpf, pnome, pidade, pendereco);
	end if;
	elsif (upper(op) = 'U') then
		update cliente
		set 
			cpf = pcpf,
			nome = pnome,
			idade = pidade,
			endereco - pendereco,
			cidade = pcidade
		where
			codc = pcodc
	elsif (upper(op) = 'D') then
		delete from cliente where codc = pcodc;
	else
		raise exception 'Comando nao encontrado';
	end if;
end;
$$
language plpgsql;

-- select atualiza_cliente('I', 10, '4232423', 'Teste 1', 20, 'sim', 'jlle');
-- select atualiza_cliente('U', 10, '4232423', 'Teste 1', 20, 'sim', 'jlle');
-- select atualiza_cliente('D', 10, null, null, null, null, null);

select * from cliente;

-- 4
create or replace function insere_setor(pcods, int, pnome varchar) returns void as
$$
begin
	if (select count(cods) from setor) < 10 then
		insert into setor values (pcods, pnome);
	else
		raise exception 'Maximo de setores cadastrados'
	end if;
end;
$$
language plpgsql;

-- 5
create or replace function insere_conserto(
	pcodm int, 
	pcodv int, 
	pdata date, 
	phora time) return void as
$$
declare
	quant int default 0;	
begin
	select count(codm) from conserto
	where codm = pcodm and data = pdata into quant;
	raise notice 'Quantidade de consetos: %', quant;
	if (quant < 3) the
		insert into values (pcodm, pcodv, pdata, phora);
	else raise exception 'Consertos para o mecanico % foi excedida', pcodm;
end;
$$
language plpgsql;

--select insere_conserto(1, 1, '10/09/2025', '11:00');

select * from conserto;

-- 6
create or replace function media_idade()
return numeric as
$$
declare
	resultado numeric;
begin
    select avg(idade)::numeric
    into resultado
    from (
        select idade from mecanico
        union all
        select idade from cliente
    ) as todas_idades;

    return resultado;
end;
$$ language plpgsql;

SELECT media_geral_idade();

-- 7
create or replace function excluir_algo(
	op char(1),
	cod int
)
returns void as
$$
begin
	if(upper(op) = 'S') then
		delete from setor where cods = cod;
	elsif (upper(op) = 'M') then
		delete from mecanico where codm = cod;
	elsif (upper(op) = 'C') then
		delete from cliente where codc = cod;
	elsif (upper(op) = 'V') then
		delete from veiculo where codv = cod;
	else
		raise exception 'Comando nao encontrado';
	end if;
end;
$$
language plpgsql;

-- 8
create or replace function remover_clientes_duplicados()
returns void as
$$
begin
	delete from cliente
    where codc not in (
        select min(codc)
        from cliente
        group by cpf
    );
end;
$$
language plpgsql;

-- 9
create or replace function cpf_valido(pcpf char(11))
returns boolean as
$$
declare
    soma1 int := 0;
    soma2 int := 0;
    i int;
    dv1 int;
    dv2 int;
begin
    for i in 1..9 loop
        soma1 := soma1 + cast(substring(pcpf, i, 1) as int) * (11 - i);
    end loop;
    if (soma1 % 11) < 2 then
        dv1 := 0;
    else
        dv1 := 11 - (soma1 % 11);
    end if;
    
    for i in 1..9 loop
        soma2 := soma2 + cast(substring(pcpf, i, 1) as int) * (12 - i);
    end loop;
    soma2 := soma2 + dv1 * 2;

    if (soma2 % 11) < 2 then
        dv2 := 0;
    else
        dv2 := 11 - (soma2 % 11);
    end if;
  
    if dv1 = cast(substring(pcpf, 10, 1) as int) and dv2 = cast(substring(pcpf, 11, 1) as int) the
        return true;
    else
        return false;
    end if;
end;
$$
language plpgsql;

--SELECT cpf_valido('22233344405'); -- TRUE
--SELECT cpf_valido('12345678909'); -- FALSE (exemplo inválido)

-- 10
create or replace function horas_extras(pcodm int, pmes int, pano int)
returns int as
$$
declare
    total_horas int;
    extras int;
begin
    select count(*) 
    into total_horas
    from conserto
    where codm = pcodm
      and extract(month from data) = pmes
      and extract(year from data) = pano;
    if total_horas > 160 then
        extras := total_horas - 160;
    else
        extras := 0;
    end if;

    return extras;
end;
$$
language plpgsql;