-- 1
select c.nome, c.endereco
from cliente c

-- 2
select m.nome, m.funcao
from mecanico m
	where m.cods = 2

-- 3
select m.cpf, m.nome from mecanico m
intersect
select c.cpf, c.nome from cliente c

-- 4
select m.cidade from mecanico m
union
select c.cidade from cliente c

-- 5	
select distinct v.marca
from veiculo v join cliente c using (codc)
where c.cidade ilike 'joinville'

-- 6
select distinct m.funcao
from mecanico m
	
-- 7
select * from cliente
where idade > 25

-- 8 
select m.cpf, m.nome
from mecanico m join setor s using(cods)
where s.nome in ('mecanica', 'mecânica')

-- 9
select m.cpf, m.nome, m.data
from mecanico m , conserto c
where m.codm = c.codm and
	c.data = '13-06-2014'

-- 10
select c.nome as nome_cli, v.modelo, m.nome as nome_mec, m.funcao
from cliente c join veiculo v using(codc)
	join coserto con using(codv)
	join mecanico m using(codm)

-- 11
select c.nome as nome_cli, v.modelo, m.nome as nome_mec
from cliente c join veiculo v using(codc)
	join coserto con using(codv)
	join mecanico m using(codm)
where con.data = '19/06/2014'
order by con.hora

-- 12
select s.cods, s.nome, c.data
from setor s join mecanico m using (cods)
	join conserto c using (codm)
where c.data between '12/06/2014' and '14/06/2014'
order by c.data