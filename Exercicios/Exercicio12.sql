-- 1
create index id_mec_cods on mecanico using hash(cods)

-- veiculo
-- codc

create index idc_vei_codc on veiculo using hash(codc)

-- 2
create index idx_mec_funcao on mecanico
    using hash (substr(funcao, 1, 10))
    
-- 3
create index idx_con_data_hora on conserto using
    btree (data, hora)

-- 4
create index idx_cli_cpf on cliente using
    btree (cpf)

-- 5
create index idx_cli_nome on cliente using
    btree (substr(nome, 1, 5))

-- 6
create index idx_cli_cpf_2 on cliente using
    btree (cpf);
    where cpf = '%55'

drop index idx_cli_cpf_2



SELECT idexnome, indexdef
FROM pg_indexes
WHERE tablename = 'cliente';
