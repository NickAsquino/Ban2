// 1
db.cliente.find({}, { nome: 1, endereco: 1, _id: 0 })

// 2
db.mecanico.find({ cods: 2 }, { nome: 1, funcao: 1, _id: 0 })

// 3
db.mecanico.aggregate([
  {
    $lookup: {
      from: "cliente",
      localField: "cpf",
      foreignField: "cpf",
      as: "info_cliente"
    }
  },
  {
    $match: {
      "info_cliente": { $ne: [] }
    }
  },
  {
    $project: {
      _id: 0,
      cpf: "$cpf",
      nome: "$nome"
    }
  }
])

// 4
db.mecanico.aggregate([
  { $project: { _id: 0, cidade: "$cidade" } },
  {
    $unionWith: {
      coll: "cliente",
      pipeline: [
        { $project: { _id: 0, cidade: "$cidade" } }
      ]
    }
  },
  { $group: { _id: "$cidade" } },
  { $project: { _id: 0, cidade: "$_id" } }
])

// 5
db.cliente.aggregate([
  { $match: { cidade: "Joinville" } },
  {
    $lookup: {
      from: "veiculo",
      localField: "codc",
      foreignField: "codc",
      as: "veiculos"
    }
  },
  { $unwind: "$veiculos" },
  { $group: { _id: "$veiculos.marca" } },
  { $project: { _id: 0, marca_distinta: "$_id" } }
])

// 6
db.mecanico.distinct("funcao")

// 7
db.cliente.find({ idade: { $gt: 25 } })

// 8
db.mecanico.aggregate([
  {
    $lookup: {
      from: "setor",
      localField: "cods",
      foreignField: "cods",
      as: "setor_info"
    }
  },
  { $unwind: "$setor_info" },
  {
    $match: {
      "setor_info.nome": "Mecânica"
    }
  },
  {
    $project: {
      _id: 0,
      cpf: 1,
      nome: 1
    }
  }
])

// 9
db.conserto.aggregate([
  {
    $match: {
      data: "2014-06-13"
    }
  },
  {
    $lookup: {
      from: "mecanico",
      localField: "codm",
      foreignField: "codm",
      as: "mecanico_info"
    }
  },
  { $unwind: "$mecanico_info" },
  {
    $group: {
      _id: "$codm",
      nome: { $first: "$mecanico_info.nome" },
      cpf: { $first: "$mecanico_info.cpf" }
    }
  },
  {
    $project: {
      _id: 0,
      cpf: 1,
      nome: 1
    }
  }
])

// 10
db.conserto.aggregate([
  {
    $lookup: {
      from: "mecanico",
      localField: "codm",
      foreignField: "codm",
      as: "mecanico_info"
    }
  },
  { $unwind: "$mecanico_info" },
  {
    $lookup: {
      from: "veiculo",
      localField: "codv",
      foreignField: "codv",
      as: "veiculo_info"
    }
  },
  { $unwind: "$veiculo_info" },
  {
    $lookup: {
      from: "cliente",
      localField: "veiculo_info.codc",
      foreignField: "codc",
      as: "cliente_info"
    }
  },
  { $unwind: "$cliente_info" },
  {
    $project: {
      _id: 0,
      cliente_nome: "$cliente_info.nome",
      veiculo_modelo: "$veiculo_info.modelo",
      mecanico_nome: "$mecanico_info.nome",
      mecanico_funcao: "$mecanico_info.funcao"
    }
  }
])

// 11
db.conserto.aggregate([
  {
    $match: {
      data: "2014-06-19"
    }
  },
  {
    $lookup: {
      from: "mecanico",
      localField: "codm",
      foreignField: "codm",
      as: "mecanico_info"
    }
  },
  { $unwind: "$mecanico_info" },
  {
    $lookup: {
      from: "veiculo",
      localField: "codv",
      foreignField: "codv",
      as: "veiculo_info"
    }
  },
  { $unwind: "$veiculo_info" },
  {
    $lookup: {
      from: "cliente",
      localField: "veiculo_info.codc",
      foreignField: "codc",
      as: "cliente_info"
    }
  },
  { $unwind: "$cliente_info" },
  {
    $project: {
      _id: 0,
      mecanico_nome: "$mecanico_info.nome",
      cliente_nome: "$cliente_info.nome",
      hora_conserto: "$hora"
    }
  }
])

// 12
db.conserto.aggregate([
  {
    $match: {
      data: {
        $gte: "2014-06-12",
        $lte: "2014-06-14"
      }
    }
  },
  {
    $lookup: {
      from: "mecanico",
      localField: "codm",
      foreignField: "codm",
      as: "mecanico_info"
    }
  },
  { $unwind: "$mecanico_info" },
  {
    $lookup: {
      from: "setor",
      localField: "mecanico_info.cods",
      foreignField: "cods",
      as: "setor_info"
    }
  },
  { $unwind: "$setor_info" },
  {
    $group: {
      _id: "$setor_info.cods",
      nome: { $first: "$setor_info.nome" }
    }
  },
  {
    $project: {
      _id: 0,
      codigo_setor: "$_id",
      nome_setor: "$nome"
    }
  }
])