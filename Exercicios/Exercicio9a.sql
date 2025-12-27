-- 1
begin transaction;

INSERT INTO setor VALUES (1, 'Elétrica'),
(2, 'Mecânica'),
(3, 'Funilaria'),
(4, 'Pintura');

INSERT INTO mecanico VALUES 
(1, '11111111111', 'Miguel', 35, 'Kensington', 'Chicago', 'alinhamento', 1),
(2, '22222222222', 'Sophia', 29, 'Brooklyn', 'New York', 'freios', 2),
(3, '33333333333', 'Lucas', 46, 'Centro', 'Curitiba', 'injeção', 3),
(4, '44444444444', 'Bianca', 31, 'Estreito', 'Florianópolis', 'polimento', NULL),
(5, '55555555555', 'Rafael', 39, 'Campinas', 'São José', 'pintura', 4);


end transaction;

-- 2
begin transaction;

INSERT INTO cliente VALUES 
(1, '99999999999', 'Marina', 22, 'Kensington', 'Chicago'),
(2, '88888888888', 'Eduardo', 27, 'Queens', 'New York'),
(3, '77777777777', 'Camila', 33, 'Brooklyn', 'New York'),
(4, '66666666666', 'Gustavo', 45, 'Estreito', 'Florianópolis'),
(5, '55555666666', 'Patrícia', 36, 'Centro', 'Florianópolis'),
(6, '44444555555', 'Leonardo', 41, 'Campinas', 'São José'),
(7, '33333444444', 'Fernanda', 29, 'Queens', 'New York');

INSERT INTO veiculo VALUES 
(1, 'ABC1D23', 'Onix', 'Chevrolet', '2018', 35000.500, 1),
(2, 'XYZ2E34', 'HB20', 'Hyundai', '2019', 27000.750, 2),
(3, 'JKL3F45', 'Sandero', 'Renault', '2016', 62000.200, 3),
(4, 'MNO4G56', 'Corolla', 'Toyota', '2017', 89000.900, 4),
(5, 'PQR5H67', 'Golf', 'Volkswagen', '2015', 78000.300, 5),
(6, 'STU6I78', 'Uno', 'Fiat', '2013', 110000.000, 6),
(7, 'VWX7J89', 'Civic', 'Honda', '2014', 99000.800, 7);

INSERT INTO conserto VALUES 
(1, 1, '05/02/2020', '09:00'),
(1, 4, '06/02/2020', '14:30'),
(2, 1, '07/02/2020', '10:00'),
(2, 2, '07/02/2020', '15:00'),
(2, 3, '08/02/2020', '13:00'),
(2, 4, '08/02/2020', '16:00'),
(3, 1, '10/02/2020', '08:30'),
(3, 3, '05/02/2020', '11:00'),
(3, 4, '10/02/2020', '13:30'),
(4, 4, '11/02/2020', '10:00');

end transaction;

select * from mecanico;
select * from setor;
rollback;


-- 3
begin;
	update mecanico set idade = idade + 1;
	update cliente set idade = idade + 1;
end;

-- 4
begin;
	insert into setor values(5, 'Teste');
	update setor set cods = 5
		where cods = 2;
end;

rollback

-- 4
begin transaction;

insert into setor values (5, 'Seguro');

update mecanico set cods = 5 where cods = 2;

end transaction;

-- 5
begin transaction;

update mecanico set cidade = upper(cidade);
update cliente set cidade = upper(cidade);

end transaction;

-- 6
begin transaction;

update mecanico set nome = concat('Mecanico ', nome);
update cliente set nome = concat('Cliente ', nome);

end transaction;

-- 7
begin transaction;

update mecanico set cods = 4 where cods = 3;
delete from setor where cods = 3;

end transaction;