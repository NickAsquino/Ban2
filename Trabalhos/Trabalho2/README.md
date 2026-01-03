# Trabalho 2 — Sistema de Gestão Musical

**Resumo**

Este repositório contém o Trabalho 2 do curso BAN2 — um sistema simples de gestão musical implementado em Java com Maven. O projeto fornece telas de apresentação (Swing/console) para gerenciar entidades como Autor, Banda, Disco, Endereço, Instrumento, Música, Músico e Produtor.

## Estrutura do repositório

- `DDL-SQL-atualizado-E2.sql` — Scripts DDL do banco de dados (E2).
- `FuncoesGatilhos-atualizado-E3.sql` — Funções e gatilhos (E3).
- `mongo.js` — Script/auxílio para operações com MongoDB (se aplicável).
- `demo/` — Módulo Maven principal contendo a aplicação Java:
  - `src/main/java/apresentacao` — Código da apresentação (telas e `App.java`).
  - `src/main/java/dados` — Entidades (Autor, Banda, Disco, etc.).
  - `src/main/java/gerenciamento` — Lógica de gerenciamento/serviços.
  - `src/test/java` — Testes unitários.

## Requisitos

- Java 8+ (JDK instalado)
- Maven 3.x

## Como compilar e executar ✅

1. Entre na pasta `demo`:

```powershell
cd demo
```

2. Compilar o projeto e copiar dependências:

```powershell
mvn compile dependency:copy-dependencies
```

3. Executar a aplicação (exemplo usando classpath local):

```powershell
java -cp "target/classes;target/dependency/*" apresentacao.App
```

Observação: Você também pode empacotar com `mvn package` e executar conforme preferir.

## Testes

Para rodar a suíte de testes:

```powershell
cd demo
mvn test
```

## Banco de Dados e Scripts

- Use `DDL-SQL-atualizado-E2.sql` para criar as tabelas necessárias
- `mongo.js` contém utilitários/trechos para trabalhar com MongoDB.

## Uso e funcionalidades principai

- Inserir, listar, editar e excluir entidades: Autor, Banda, Disco, Endereço, Instrumento, Música, Músico, Produtor.
- Interface de apresentação localizada em `apresentacao`.
