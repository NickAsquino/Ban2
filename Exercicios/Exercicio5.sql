-- 1
select m.cpf, m.nome
from mecânico m
where m.cods in (1,2)

select m.cpf, m.nome
from mecanico m
where m.cods = 1 or cods = 2

-- 2
select m.cpf, m.nome
from mecanico m
where cods in (select cods
				from setor
				where nome in ('Funilaria', 'Pintura'))

select m.cpf, m.nome
from mecanico m join setor s using(cods)
where s.nome in ('Funilaria', 'Pintura')

-- 3
select m.cpf, m.nome
from mecanico m join conserto c using(codm)
where c.data = '13/06/2014'
	
-- 4
select m.nome, c.nome, s.hora
from conserto s 
join mecanico m using(codm)
join veiculo v using(codv)
join cliente c using(codc)
where s.data = '12/06/2014'
	
	
-- 5
select m.nome, m.funcao, s.cods, s.nome
from mecanico m left join setor s using(cods)

-- 6 
select distinct m.nome, c.data
from mecanico m left join conserto c using (codm)

-- 7
select round (cast (avg(v.quilometragem) as numeric), 2)
from veiculo v

-- 8
select c.cidade, sum(v.quilometragem)
from cliente join veiculo v on (c.codc = v.codc)
group by c.cidade

-- 9
select m.nome, count(*)
from mecanico m left join conserto c using (codm)
where c.data between '12/06/2014' and '19/06/2014'
group by m.nome

-- 10
select v.marca, count(*)
from veiculo v left join conserto c using (codv)
group by v.marca

-- 11
slect v.modelo, v.marca, v.ano
from veiculo v
where v.quilometragem > (select avg(v.quilometragem)
							froom veiculo v)

-- 12 
select m.nome, c.data, count(*)
from mecanico m join conserto c using (codm)
group by m.nome, c.data
having count(*) > 1