# language: pt
Funcionalidade: Inicial

  Cenário: Carregar arquivo simples
    Dado um arquivo simples
    Quando solicito uma carga
    Então um novo arquivo com o mesmo conteúdo é gerado

  Cenário: Carregar arquivo que referencia outro
    Dado um arquivo que referencia outro
    Quando solicito uma carga
    Então um novo arquivo com o mesmo do que foi referenciado é gerado
