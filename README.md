# Avaliação para admissão de Desenvolvedores para a Neurotech

## Objetivo

Este documento tem como objetivo descrever um projeto Java, a ser realizado em um período máximo de 3 dias, para ser utilizado como critério de avaliação técnica para admissão de novos desenvolvedores aqui para a Neurotech.

## Descrição

Trata-se de um projeto que avalia e aplica modalidades diferentes de crédito a clientes PF, de acordo com critérios específicos. As modalidades diferentes de crédito estão descritas a seguir:

-   Crédito com Juros fixos: Aplicado a clientes com idade entre 18 e 25 anos, independente de renda. Taxa de 5% a.a
-   Crédito com Juros variáveis: Aplicado a clientes com idade entre 21 e 65 anos, com renda entre R$ 5000,00 e R$ 15000,00.
-   Crédito Consignado: Aplicado a clientes acima de 65 anos, independente de renda.

O projeto deve ser implantado como uma API RESTful, utilizando a linguagem Java e o framework Springboot. Atentar para implementações típicas de uma API RESTful, como códigos HTTP para cada tipo de endpoint, validação de dados, Documentação utilizando Swagger, e também testes automáticos para os endpoints implementados.

De maneira obrigatória, os seguintes endpoints devem ser implementados:

-   Endpoint para cadastro de clientes: Deve receber Informações como Nome, idade, renda. Como retorno, uma entrada no header da resposta contendo a URL que identifica o cliente (Ex: [http://localhost/api/client/050](http://localhost/api/client/050)). O nome do header deve ser “Location”.
    
-   Endpoint para retornar os dados do cliente de acordo com seu ID, indicado na URL (Ex: [http://localhost/api/client/050](http://localhost/api/client/050)). O retorno deve ser um objeto JSON contendo os dados do cliente. Por exemplo:

```
{ 
  "Name": "Bob",
  "Age": 40,
  "Income": 10000
}
```
-   Endpoint para definir se um determinado cliente está apto a oferecer um crédito automotivo para determinado modelo de veículo.
    -   Hatch: Renda entre R$ 5000,00 e R$15000,00.
    -   SUV: Renda acima de R$8000,00 e idade superior a 20 anos.
    

Como adicional, mas não obrigatório, implemente um endpoint para se determinar todos os clientes entre 23 e 49 anos que possuem Crédito com juros fixos e estão aptos a adquirirem crédito automotivo para veículos do tipo Hatch. O objeto de retorno deve conter uma lista com o nome e a renda de cada um destes clientes.
