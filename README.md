# Projeto Concorrência e Consistência em Banco de Dados

Este projeto foi desenvolvido para demonstrar, de forma prática e didática, problemas de concorrência em aplicações transacionais utilizando Spring Boot, JPA/Hibernate e banco de dados H2.

O objetivo principal é comparar o comportamento de operações simultâneas:

* sem controle de concorrência
* com controle de concorrência otimista utilizando `@Version`

---

# 👩‍💻 Integrantes da Dupla

## Belém Ribeiro

Responsável por:

* Implementação da entidade `ContaBancaria`
* Desenvolvimento dos endpoints sem controle de concorrência
* Implementação das operações de depósito e saque sem `@Version`
* Configuração do cenário de concorrência sem controle
* Análise do problema de Lost Update
* Testes de inconsistência de saldo

## Stherfanny

Responsável por:

* Implementação da entidade `ContaBancariaVersionada`
* Configuração do controle otimista com `@Version`
* Tratamento da exceção `ObjectOptimisticLockingFailureException`
* Implementação do retorno HTTP `409 Conflict`
* Configuração dos testes concorrentes com controle otimista
* Documentação e análise comparativa dos resultados

---

# 🚀 Tecnologias Utilizadas

* Java 21
* Spring Boot 3.2.5
* Spring Data JPA
* H2 Database
* Lombok
* Maven
* Apache JMeter

---

# 📌 Objetivo do Projeto

Demonstrar na prática:

1. O problema de concorrência conhecido como **Lost Update**
2. A solução utilizando **Controle de Concorrência Otimista** com `@Version`

---

# ⚠️ Problema de Concorrência: Lost Update

O problema de Lost Update acontece quando duas transações acessam o mesmo registro simultaneamente.

Exemplo:

1. Dois usuários leem o mesmo saldo bancário.
2. Ambos alteram o valor.
3. O último salvamento sobrescreve o primeiro.
4. Parte das alterações é perdida.

Isso gera inconsistência de dados no banco.

---

# 🛡️ Solução Utilizada: Optimistic Locking com @Version

Foi utilizada a anotação `@Version` do JPA/Hibernate.

Com isso:

* cada registro possui uma versão
* o Hibernate verifica se o dado foi alterado antes de salvar
* caso outro processo tenha modificado o registro, uma exceção é lançada

Dessa forma:

* evita-se sobrescrever alterações concorrentes
* o sistema mantém consistência dos dados

---

# 🛠️ Como Executar a Aplicação

## Pré-requisitos

É necessário possuir instalado:

* Java 21
* Maven

---

## Passos para execução

### 1. Clonar o repositório

```bash
git clone https://github.com/belemdebelem/trabalho-concorrencia-bd.git
```

---

### 2. Entrar na pasta do projeto

```bash
cd trabalho-concorrencia-bd
```

---

### 3. Executar a aplicação

```bash
mvn spring-boot:run
```

---

### 4. Acessar a aplicação

A aplicação iniciará em:

```text
http://localhost:8080
```

---

# 🗄️ Banco de Dados H2

## Acesso ao console

```text
http://localhost:8080/h2-console
```

## Configurações

### JDBC URL

```text
jdbc:h2:mem:concorrenciadb
```

### Usuário

```text
sa
```

### Senha

```text
(em branco)
```

---

# 📡 Endpoints Disponíveis

# Parte 1 — Sem Controle de Concorrência

## Consultar conta

```http
GET /contas/1
```

## Realizar depósito

```http
POST /contas/1/deposito
```

## Realizar saque

```http
POST /contas/1/saque
```

---

# Parte 2 — Controle Otimista com @Version

## Consultar conta

```http
GET /contas-versionadas/1
```

## Realizar depósito

```http
POST /contas-versionadas/1/deposito
```

## Realizar saque

```http
POST /contas-versionadas/1/saque
```

---

# 📥 Exemplo de Requisição

## Corpo JSON

```json
{
  "valor": 100.00
}
```

---

# 🧪 Testes de Concorrência com JMeter

O arquivo de testes do Apache JMeter:

```text
concorrencia-testes.jmx
```

está salvo na raiz do repositório, conforme solicitado na atividade.

---

# ▶️ Como Executar os Testes

1. Abrir o Apache JMeter
2. Ir em:

   ```text
   File > Open
   ```
3. Selecionar o arquivo:

   ```text
   concorrencia-testes.jmx
   ```
4. Executar o teste clicando no botão verde "Start"

O cenário utiliza:

* múltiplas threads simultâneas
* depósitos concorrentes
* simulação de conflito de escrita

---

# 📊 Relatório de Conclusão

## Parte 1 — Sem Controle de Concorrência

### Comportamento observado

* Todas as requisições retornam `200 OK`
* O saldo final fica inconsistente
* O valor final não corresponde à soma correta das operações realizadas

### Motivo

As threads acessam e modificam o mesmo registro simultaneamente sem nenhuma validação de concorrência.

### Resultado

Ocorre o problema de:

```text
Lost Update (Atualização Perdida)
```

---

## Parte 2 — Com Controle Otimista (@Version)

### Comportamento observado

* Algumas requisições retornam `200 OK`
* Outras retornam `409 Conflict`
* O saldo final permanece consistente

### Motivo

O Hibernate verifica a versão do registro antes de salvar.

Quando duas threads tentam atualizar o mesmo dado:

* apenas a primeira consegue salvar
* as demais recebem conflito de versão

### Resultado

O sistema evita sobrescrita concorrente e mantém a integridade dos dados.

---

# 🖼️ Prints dos Resultados

## Parte 1 — Sem Controle

> Inserir aqui print do JMeter demonstrando saldo inconsistente e requisições simultâneas.

---

## Parte 2 — Com @Version

> Inserir aqui print do JMeter demonstrando erros `409 Conflict` e controle otimista funcionando.

---

# 📂 Estrutura de Pastas

```text
src/main/java/com/exemplo/concorrencia/
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
└── service
```

---

# 📊 Resultados Esperados

## Cenário 1 — Conta Sem Controle

* Todas as requisições retornam `200 OK`
* O saldo final fica incorreto
* Demonstra o problema de concorrência

---

## 📸 Evidências dos Testes e Análise Comparativa

Esta seção documenta o comportamento do sistema sob estresse, utilizando 50 requisições simultâneas para simular concorrência.

### Parte 1 — Sem Controle (Lost Update)
Neste cenário, todas as transações tentam ler e gravar o saldo ao mesmo tempo sem nenhuma restrição.

*   **Comportamento Observado:** O JMeter registra **0% de erros**, ou seja, todas as 50 requisições retornaram `HTTP 200 OK`.
*   **Problema Gerado:** Apesar do falso sucesso, o banco de dados sofreu **Lost Update (Atualização Perdida)**. O saldo final não bate com a soma real das transações (ex: esperado R$ 1500, obtido R$ 1050), pois as transações simultâneas sobrescreveram os dados umas das outras.

> **[INSERIR PRINT AQUI: Árvore de Resultados do JMeter verde + Saldo Inconsistente]**

### Parte 2 — Com @Version (Controle Otimista)
Neste cenário, a anotação `@Version` do JPA adiciona uma coluna de versão ao registro. Se a versão no banco mudar entre a leitura e a escrita, o sistema rejeita a alteração.

*   **Comportamento Observado:** O JMeter registra uma alta taxa de falhas. As requisições rejeitadas retornam **`HTTP 409 Conflict`**.
*   **Solução Atingida:** Ao contrário da Parte 1, o saldo final permanece **estritamente consistente**. O banco de dados só aceita depósitos baseados na versão mais recente, forçando as threads concorrentes a falharem com segurança ao invés de corromperem o saldo.

> **[INSERIR PRINT AQUI: Árvore de Resultados do JMeter com erros 409 Conflict + Saldo Consistente]**

---

# 📚 Finalidade Acadêmica

Projeto desenvolvido exclusivamente para fins acadêmicos e estudo de concorrência em sistemas transacionais utilizando Spring Boot e JPA/Hibernate.
