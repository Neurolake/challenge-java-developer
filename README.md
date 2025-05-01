# 🚀 Neurotech Credit Evaluation API

API desenvolvida para o desafio técnico da Neurotech: sistema para **cadastro de clientes e avaliação de crédito** para modelos de veículos.

## 📝 Descrição

Esta API permite:

- ✅ Cadastrar novos clientes
- ✅ Consultar clientes pelo ID
- ✅ Avaliar a elegibilidade de crédito para veículos dos modelos `HATCH` e `SUV`
- ✅ Listar clientes especiais (com critérios de idade, juros fixos e renda)
- ✅ Respostas detalhadas de erro para entradas inválidas

---

## ⚙️ Tecnologias Utilizadas

- Java 21
- Spring Boot 3.2.4
- Spring Web
- Spring Validation
- Springdoc OpenAPI (Swagger)
- JUnit 5 & Mockito
- Jacoco (cobertura de testes)
- Lombok

---

## 🚀 Como Executar o Projeto

### 1️⃣ Pré-requisitos

- Java 21+
- Maven 3.8+

### 2️⃣ Clone o repositório

```bash
git clone https://github.com/chicaodw/challenge-java-developer.git
```

### 3️⃣ Execute o projeto

```bash
./mvnw spring-boot:run
```

> 🔗 A API estará disponível em: `http://localhost:8080`

---

## 🧪 Como Rodar os Testes

Para executar todos os testes unitários:

```bash
./mvnw test
```

---

## 📈 Gerar Relatório de Cobertura Jacoco

Após rodar os testes, para gerar o relatório de cobertura:

```bash
./mvnw test
```

O relatório estará em:

```
target/site/jacoco/index.html
```

Abra no navegador para visualizar a cobertura de código.

Para atualizar o relatório caso atualize ou crie novos testes:

```bash
./mvnw jacoco:report
```

---

## 📚 Documentação Swagger

Após iniciar a aplicação, acesse a documentação Swagger/OpenAPI em:

🔗 [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

---

## 🗂️ Estrutura Básica do Projeto

```bash
src/
 ├── main/
 │   ├── java/br/com/neurotech/challenge/
 │   │    ├── controller/
 │   │    ├── DTO/
 │   │    ├── entity/
 │   │    ├── exception/
 │   │    ├── mapper/
 │   │    ├── repository/
 │   │    ├── service/
 │   │    ├── service/impl/
 │   │    └── ChallengeJavaDeveloperApplication.java
 │   └── resources/
 │        └── application.yaml
 └── test/
      ├── java/br/com/neurotech/challenge/
      │    ├── controller/
      │    ├── DTO/
      │    ├── repository/
      │    └── service/
      │    └── ChallengeJavaDeveloperApplicationTests.java
```

---

## ✅ Testes Implementados

- **ClientControllerTest**

  - Cadastro de cliente
  - Validações (nome, idade, renda)
  - Consulta de cliente
  - Avaliação de crédito (válido, inválido)
  - Lista de clientes especiais
  - Tratamento de exceções

- **ClientRequestDTOTest**

  - Teste simples de criação e acesso aos campos do DTO de requisição (nome, idade, renda).

- **ClientResponseDTOTest**

  - Teste simples de criação e acesso aos campos do DTO de resposta (nome, idade, renda).

- **InMemoryClientRepositoryTest**

  - Testa salvar cliente e recuperar pelo ID
  - Testa busca por cliente inexistente (retorna vazio)
  - Testa listagem completa de todos os clientes salvos

- **CreditServiceImplTest**

  - Regras de crédito para HATCH e SUV
  - Exceção para cliente não encontrado
  - Cobertura total dos casos de regra

- **ClientServiceImplTest**
  - Filtro de clientes especiais

---

## 🔍 Observação sobre o endpoint `/api/client/special`

O enunciado descreve o seguinte requisito adicional:

> **"Implemente um endpoint para determinar todos os clientes entre 23 e 49 anos que possuem Crédito com Juros Fixos e estão aptos a adquirirem crédito automotivo para veículos do tipo Hatch. O objeto de retorno deve conter uma lista com o nome e a renda de cada um destes clientes."**

No entanto, segundo as regras fornecidas no próprio desafio:

| **Modalidade**               | **Critério**                                         |
| ---------------------------- | ---------------------------------------------------- |
| **Crédito com Juros Fixos**  | Clientes entre 18 e 25 anos, independente da renda.  |
| **Crédito Automotivo Hatch** | Clientes com renda entre R$ 5.000,00 e R$ 15.000,00. |

### 🔚 Inconsistência identificada:

O requisito pede **clientes entre 23 e 49 anos com juros fixos**, mas a modalidade de **juros fixos só se aplica a clientes de 18 a 25 anos**.

➡️ **Logo, apenas clientes entre 23 e 25 anos** podem satisfazer todos os critérios ao mesmo tempo.

---

## ✅ Como foi implementado

Para atender ao requisito da forma **mais fiel e lógica possível**, o filtro faz:

1. Idade entre 23 e 49 anos (conforme o enunciado);
2. Garante também que esteja na faixa de juros fixos (18 a 25 anos);
3. Renda entre R$ 5.000 e R$ 15.000 (crédito Hatch).

### 🛠️ Resultado prático:

Na prática, **apenas clientes entre 23 e 25 anos** aparecem no resultado, pois são os únicos que satisfazem todos os requisitos ao mesmo tempo.

## 💡 Nota final

Caso o real objetivo fosse listar **todos os clientes entre 23 e 49 anos aptos para crédito Hatch**, o filtro seria ajustado removendo a verificação da faixa etária de juros fixos.

---

## 📅 Desenvolvedor

**Francisco das Chagas Costa da Silva**

- Email: francyscocosta@gmail.com
- LinkedIn: [https://www.linkedin.com/in/chicaodw/](https://www.linkedin.com/in/chicaodw/)
- GitHub: [https://github.com/chicaodw](https://github.com/chicaodw)

---

Este projeto foi desenvolvido para fins de avaliação técnica e segue as especificações fornecidas pela Neurotech. Todos os fluxos de negócio estão devidamente cobertos por testes unitários e validados via Jacoco.
