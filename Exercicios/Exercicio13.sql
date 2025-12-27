-- 1
select cpf, m.cods, s.nome
from mecanico m
left join setor s on m.cods = s.cods
where m.cods > 100 and m.cods < 200;

-- 2
drop index if exists idx_data_conserto;
create index idx_data_conserto on conserto using hash(data);

select m.cpf, m.nome
from (select * from conserto where data = '2018-06-13') as c
join mecanico m on m.codm = c.codm;

-- 3
drop index if exists idx_conserto;
create index idx_conserto on conserto using btree(data);

drop index if exists idx_cliente;
create index idx_cliente on cliente using hash(codc);

select m.nome as nome_mecanico, c.nome as nome_cliente, cs.hora
from mecanico m
left join conserto cs using(codm)
left join veiculo using(codv)
left join cliente c using(codc)
where cs.data between '2018-06-12' and '2018-09-25';

-- 4
select m.nome, m.funcao, m.cods, s.nome as nome_setor
from mecanico m
left join setor s using(cods);

-- 5
select distinct m.nome, c.data
from mecanico m
left join conserto c using(codm);

-- 6
select avg(quilometragem) as media_quilometragem
from veiculo;

-- 7
select c.cidade, sum(v.quilometragem) as soma_quilometragem
from veiculo v
join cliente c using(codc)
group by c.cidade;

-- 8
drop index if exists idx_conserto;
create index idx_conserto on conserto using btree(data, codm);

select m.nome, count(c.codm) as qtd_consertos
from mecanico m
left join conserto c using(codm)
where c.data between '2018-06-12' and '2018-10-19'
group by m.codm, m.nome;

-- 9
select v.marca, count(*) as qtd_consertos
from conserto c
left join veiculo v using(codv)
group by v.marca;

-- 10
drop index if exists idx_veiculo;
create index idx_veiculo on veiculo using btree(quilometragem);

select v.modelo, v.marca, v.ano
from veiculo v
where v.quilometragem > (select avg(quilometragem) from veiculo)
group by v.modelo, v.marca, v.ano
order by v.modelo, v.marca, v.ano;

-- 11
drop index if exists idx_conserto;
create index idx_conserto on conserto using btree(data, codm);

select m.nome
from mecanico m
left join conserto c using(codm)
where (
    select count(*)
    from conserto c2
    where c2.codm = m.codm and c2.data = c.data
) > 1
group by m.codm, m.nome;

-- 12
select distinct m.nome
from mecanico m
join conserto c using(codm);