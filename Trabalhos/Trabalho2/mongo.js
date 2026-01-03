db.Endereco.insertMany([
  {
    _id: 1,
    cidade: "Joinville",
    rua: "Rua A",
    nroCasa: 123,
    telefone: "47999999999"
  },
  {
    _id: 2,
    cidade: "Florianópolis",
    rua: "Rua B",
    nroCasa: 99,
    telefone: "48988888888"
  }
]);

db.Musico.insertMany([
  {
    _id: 1,
    nome: "João",
    idEndereco: 1
  },
  {
    _id: 2,
    nome: "Maria",
    idEndereco: 2
  }
]);

db.Instrumento.insertMany([
  { _id: 1, nome: "Violão" },
  { _id: 2, nome: "Piano" },
  { _id: 3, nome: "Bateria" }
]);

db.Autor.insertMany([
  { _id: 1, nome: "Autor 1" },
  { _id: 2, nome: "Autor 2" }
]);

db.Banda.insertMany([
  { _id: 1, nome: "Banda A" },
  { _id: 2, nome: "Banda B" }
]);

db.Musica.insertMany([
  {
    _id: 1,
    titulo: "Música 1",
    duracao: 200,
    autores: "Autor 1, Autor 2"
  },
  {
    _id: 2,
    titulo: "Música 2",
    duracao: 180,
    autores: "Autor 1"
  }
]);

db.Produtor.insertMany([
  { _id: 1, nome: "Produtor A" },
  { _id: 2, nome: "Produtor B" }
]);

db.Disco.insertMany([
  {
    _id: 1,
    formato: "CD",
    data: ISODate("2020-01-01"),
    titulo: "Disco 1",
    idProdutor: 1,
    nroRegistro: 1,
    idBanda: 1,
    numMusicas: 2
  }
]);

db.MusicoInstrumento.insertMany([
  { nroRegistro: 1, codInterno: 1 },
  { nroRegistro: 1, codInterno: 2 },
  { nroRegistro: 2, codInterno: 3 }
]);

db.MusicoBanda.insertMany([
  { nroRegistro: 1, idBanda: 1 },
  { nroRegistro: 2, idBanda: 2 }
]);

db.DiscoMusica.insertMany([
  { identificador: 1, idMusica: 1 },
  { identificador: 1, idMusica: 2 }
]);

db.Participacao.insertMany([
  { idMusica: 1, nroRegistro: 1, idBanda: 1 },
  { idMusica: 1, nroRegistro: 2, idBanda: 2 }
]);

db.MusicaAutor.insertMany([
  { idMusica: 1, idAutor: 1 },
  { idMusica: 1, idAutor: 2 },
  { idMusica: 2, idAutor: 1 }
]);
