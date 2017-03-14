# Fyxture [![BuildStatus](https://travis-ci.org/fyxture/fyxture.svg?branch=master)](https://travis-ci.org/fyxture/fyxture) [![gitrepo](https://assets-cdn.github.com/favicon.ico)](https://github.com/fyxture/fyxture)

Se você já implementou testes de integração automatizados já precisou realizar uma das operações:
* Criar, recuperar, atualizar ou excluir dados de um banco;
* Realizar requisições para uma API REST;
* Ler ou escrever dados em um arquivo;
* Obter ou configurar valores de um arquivo de propriedades;
* Ler propriedades de arquivos YAML ou JSON.

Para ~~a maioria~~ **todas** essas tarefas existem inúmeros códigos/projetos/frameworks espalhados pela internet.

Mas nenhum deles abstrai toda a parafernália de configurações para focar apenas no que interessa a quem está implementando testes (ou une todos eles sobe uma mesma interface - simples e fluente).

Esse é o objetivo de ***Fyxture***, que conta com 6 módulos principais (outros estão previstos para releases futuras):
* [Fyxture.db](#Fyxture.db)
* [Fyxture.rest](#Fyxture.rest)
* [Fyxture.file](#Fyxture.file)
* [Fyxture.properties](#Fyxture.properties)
* [Fyxture.yaml](#Fyxture.yaml)
* [Fyxture.json](#Fyxture.json)

## <a name="Fyxture.db"></a>Fyxture.db

Esse módulo utiliza o bastante maduro [Liquibase](http://liquibase.org) como executor de scripts banco.

Nada melhor que um exemplo para saber como usar:
```java
Fyxture
  .db("integration/db")
    .go("integration/db/criar-pessoa.yml");
```

O parâmetro de **db**, `integration/db`, é uma convenção para que ***Fyxture*** encontre o arquivo _integration/db.properties_ no classpath.

Já o parâmetro de **go**, `integration/db/criar-pessoa.yml`, é um caminho, dentro do classpath, para o [*changelog*](http://www.liquibase.org/documentation/databasechangelog.html) a ser executado.

## <a name="Fyxture.rest"></a>Fyxture.rest

Já este módulo utiliza o já conhecido subprojeto da Apache [HTTPComponents](https://hc.apache.org/) para realizar requisições a API REST.
```java
Fyxture
  .rest("http://localhost:8080/api/servico")
    .post("integration/rest/servico.yml")
      .contentType("application/json")
      .go();
```

O parâmetro de **rest**, `http://localhost:8080/api/servico`, como o padrão sugere, é a url do serviço a ser invocado.

O próximo método a ser chamado, **post** (apenas um dentre os 7 disponíveis, quais sejam os demais: **get**, **put**, **delete**, **options**, **head**, **trace**) é o verbo do protocolo http a ser utilizada. O seu parâmetro, `integration/rest/servico.yml`, é um caminho, dentro do classpath, para um arquivo cujo conteúdo será carregado no corpo (body) da requisição (válido apenas para os verbos **post** e **put**, como sugere a especificação daquele protocolo).

A depender da extensão o conteúdo pode vir a ser convertido (é o caso do exemplo, que converte o conteúdo do arquivo YAML para JSON).

O terceiro método, **contentType**, é usado para definir o tipo do conteúdo a ser submetido, no caso `application/json`.

Por fim, o método **go**, efetiva a requisição.

O retorno é um objeto que encapsula a resposta bem como a transforma, se assim solicitado, em um objeto JSON, permitindo percorrer suas propriedades como tal.

## <a name="Fyxture.file"></a>Fyxture.file

O método file faz uso da biblioteca [commons-io](https://commons.apache.org/proper/commons-io/), também da Apache, para mapear e criar uma cache dos arquivos referenciados.
```java
Fyxture
  .file("integration/rest/servico.yml");
```

Esse método é muito utilizado na implementação dos demais, permitindo facilmente carregar seu conteúdo e utiliza-lo conforme a necessidade.

## <a name="Fyxture.properties"></a>Fyxture.properties

Esse método abstrai a lógica por trás do processo de carga de um arquivo de propriedades que se encontra no classpath.

## <a name="Fyxture.yml"></a>Fyxture.yml

Esse método carrega um arquivo do tipo YAML e disponibiliza uma forma de percorrer suas propriedades. Além de conversores para outros formatos conhecidos, a saber, JSON.

## <a name="Fyxture.json"></a>Fyxture.json

Esse método carrega um arquivo do tipo JSON e disponibiliza uma forma de percorrer suas propriedades.
