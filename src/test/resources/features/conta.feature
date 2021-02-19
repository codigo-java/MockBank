# language: pt

@ContaTeste
Funcionalidade: Testar as operacoes basicas de conta

  Cenario: Testar deposito
    Dado a conta criada para a agencia 1 e numero 111111
    Quando realiza um deposito no valor de 200 na conta
    Entao o saldo eh de 1700.0 na conta
