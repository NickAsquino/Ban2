-- Aluno: Nicolle Beatrice Asquino

-- 1.a)  Função que informado o número do departamento como parâmetro retorne o somatório dos salários dos
-- seus empregados, sendo que o salário de cada empregado deve ser acrescido de 1% para cada dependente.

create or replace function soma_salarios(d_dnumero integer)
returns numeric as $$
declare
    total_soma numeric := 0;
    salario_base int;
    qtd_dependentes int;
    emp record;
begin
    for emp in
        select ssn, salario from empregado 
        where dno = d_dnumero
    loop
        select count(*) into qtd_dependentes from dependente
        where essn = emp.ssn;

        salario_base := emp.salario * (1 + (qtd_dependentes * 0.01));
        total_soma := total_soma + salario_base;
    end loop;
    return total_soma;
end;
$$ language plpgsql;

-- 1.b)Função que incremente em 2% o salário dos empregados para cada projeto que eles estiverem alocados
create or replace function salario_projetos() return void as 
$$
declare
    empreg record;
    qtd_proj int;
    novo_salario numeric;
begin
    for empreg in select ssn, salario from empregado
    loop
        select count(*) into qtd_proj from trabalha_em
        where essn = emp.ssn;

        if qtd_proj > 0 then
            novo_salario := empreg.salario * (1 + (qtd_proj * 0.02));

            update empregado
            set salario = novo_salario
            where ssn = emp.ssn;
        end if;
    end loop;
end;
$$
language plpgsql;

-- 2.a) Gatilho para impedir que um empregado já cadastrado seja cadastrado como dependente. A comparação deve ser feita pelo nome de ambos
create or replace function empregado_dependente() return trigger as 
$$
begin
    if exists (select 1 from empregado
			   where concat(pnome, ' ', unome) = new.nome_dependente
    ) then
        raise exception 'O dependente já eh um empregado cadastrado';
    end if;
    return new;
end;
$$ language plpgsql;

	  
-- 2.b) Gatilho para impedir que um empregado seja gerente de mais de um departamento.
create or replace function gerente_departamento return trigger as
$$
begin
	if exists (
        select 1
        from departamento
        where gerssn = NEW.gerssn
          and dnumero <> NEW.dnumero
    ) then
        raise exception 'Este empregado ja eh gerente de outro departamento.';
	end if;
	return new;
$$
language plpgsql;

create or replace function verifica_horas_projeto() 
returns trigger as
$$
declare
    total_horas numeric;
begin
    select sum(horas)
    into total_horas
    from trabalha_em
    where pnumero = new.pnumero;

    if total_horas + new.horas > 30 then
        raise exception 'a soma das horas trabalhadas neste projeto ultrapassa 30 horas';
    end if;

    return new;
end;
$$
language plpgsql;

create trigger trg_verifica_horas_projeto
before insert or update on trabalha_em
for each row
execute function verifica_horas_projeto();

create or replace function impedir_gerente_supervisor()
returns trigger as
$$
begin
    if new.superssn in (select gerssn from departamento) then
        raise exception 'um gerente não pode ser supervisor de outro empregado';
    end if;

    return new;
end;
$$
language plpgsql;

create trigger trg_impedir_gerente_supervisor
before insert or update on empregado
for each row
execute function impedir_gerente_supervisor();


-- 2. e) Gatilho para impedir que um empregado supervisione um empregado de outro departamento. 
create or replace function impedir_supervisao_departamento_diferente()
returns trigger as
$$
declare
    depto_empregado integer;
    depto_supervisor integer;
begin
    select dno into depto_empregado
    from empregado
    where ssn = new.ssn;
    select dno into depto_supervisor
    from empregado
    where ssn = new.superssn;
    if depto_supervisor is not null and depto_empregado is not null 
       and depto_empregado <> depto_supervisor then
        raise exception 'um empregado noo pode supervisionar alguem de outro departamento';
    end if;

    return new;
end;
$$
language plpgsql;

create trigger trg_impedir_supervisao_departamento_diferente
before insert or update on empregado
for each row
execute function impedir_supervisao_departamento_diferente();

-- 2. f) Gatilho para incrementar o salário do empregado a cada novo dependente dele cadastrado. 
create or replace function incrementar_salario_dependente()
returns trigger as
$$
begin
    update empregado
    set salario = salario * 1.02
    where ssn = new.essn;

    return new;
end;
$$
language plpgsql;

create trigger trg_incrementar_salario_dependente
after insert on dependente
for each row
execute function incrementar_salario_dependente();

-- 3. a) Visão que mostre o nome de todos os empresados, de seus respectivos departamentos, e para aqueles que são 
-- supervisores, mostre também o nome dos empregados supervisionados. 
create or replace view empregados_com_supervisionados as
select 
    e1.pnome || ' ' || e1.minicial || ' ' || e1.unome as empregado_nome,
    d.dnome as departamento_nome,
    string_agg(e2.pnome || ' ' || e2.minicial || ' ' || e2.unome, ', ') as supervisionados
from empregado e1
join departamento d on e1.dno = d.dnumero
left join empregado e2 on e2.superssn = e1.ssn
group by e1.ssn, e1.pnome, e1.minicial, e1.unome, d.dnome
order by d.dnome, e1.unome;

-- 3.b) Visão que mostra o nome de todos os departamentos e para aqueles que tem empregados mostre também os 
-- nomes e quantidade de horas alocadas em projetos. 
create or replace view departamentos_com_horas as
select 
    d.dnome as departamento_nome,
    e.pnome || ' ' || e.minicial || ' ' || e.unome as empregado_nome,
    te.total_horas as horas_alocadas
from departamento d
left join empregado e on e.dno = d.dnumero
left join (
    select essn, sum(horas) as total_horas
    from trabalha_em
    group by essn
) te on te.essn = e.ssn
order by d.dnome, e.unome;
