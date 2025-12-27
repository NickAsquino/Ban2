-- 1
create or replace view mec_nome_funcao as
select m.nome, m.funcao
from mecanico m;

select * from mec_nome_funcao;

-- 2
create or replace view cli_mod_marca as
select v.modelo, v.marca, c.nome
from veiculo v join cliente c using(codc);

select * from cli_mod_marca;

-- 3
create or replace view consertos as
select m.nome as mecanico, cli.nome as cliente, v.modelo, con.data, con.hora
from conserto con
join mecanico m using(codm)
join veiculo v using(codv)
join cliente cli using(codc);

select * from consertos;

-- 4
create or replace view veiculo_ano_km as
select v.ano, avg(v.quilometragem) as km
from veiculo v;
group by v.ano
order by v.ano

drop view veiculo_ano_km
	
select * from veiculo_ano_km
where km > 50000

-- 5
create or replace view mec_consertos as
select m.nome, c.data, count(c.codm) as consertos
from mecanico m join conserto c using(codm)
group by m.nome, c.data;

select * from mec_consertos;

-- 6
create or replace view setor_consertos as
select s.nome, c.data, count(*) as consertos
from setor s 
join mecanico m using(cods)
join conserto c using(codm)
group by s.nome, c.data;

select * from setor_consertos;

-- 7
create or replace view mecanicos_funcao as
select funcao, count (*) total_mecanicos
from mecanico
group by funcao;

select * from mecanicos_funcao;

-- 8
create or replace view mecanicos_funcoes as
select nome, funcao, cods as setor
from mecanico;

select * from mecanicos_funcoes;

-- 9
create or replace view mecanico_consertos_funcao as
select m.funcao, count (c.codm) qtd_consertos
from mecanico m
join conserto c using(codm)
group by m.funcao;

select * from mecanico_consertos_funcao;