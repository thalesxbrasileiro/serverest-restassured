# Projeto de Testes Automatizados com REST Assured - ServeRest

Este repositório contém dois projetos de testes automatizados desenvolvidos com **REST Assured** para validar os endpoints da API pública **ServeRest**. Um dos projetos utiliza o framework **JUnit5** e o outro **TestNG**, garantindo uma cobertura completa dos testes da API.

## Estrutura do Repositório

- **junit5-restassured**: Projeto de testes automatizados utilizando **REST Assured** com **JUnit5**.
- **testng-restassured**: Projeto de testes automatizados utilizando **REST Assured** com **TestNG**.

## Descrição dos Testes

Os testes são projetados para validar os principais endpoints da API ServeRest, simulando um ambiente de e-commerce. Cada conjunto de testes aborda diferentes funcionalidades da API, como:

- **/usuarios**: Criação, listagem, atualização e exclusão de usuários.
- **/login**: Autenticação de usuários e geração de tokens.
- **/produtos**: Criação, listagem, atualização e exclusão de produtos.
- **/carrinhos**: Criação, manipulação e exclusão de carrinhos de compras.

## Tecnologias Utilizadas

As tecnologias e frameworks usados neste projeto são:

- **Java 11+**: Linguagem de programação.
- **REST Assured**: Biblioteca para automação de testes de APIs RESTful.
- **JUnit5**: Framework de testes para o projeto `junit5-restassured`.
- **TestNG**: Framework de testes para o projeto `testng-restassured`.
- **Maven**: Ferramenta de build e gerenciamento de dependências.
- **ServeRest API**: API pública usada como base para os testes. Mais informações em [https://serverest.dev](https://serverest.dev).

## Como Executar os Testes

### Pré-requisitos

- **JDK 17** instalado.
- **Maven** instalado.

### Configuração

Antes de executar os testes, é necessário configurar as credenciais de usuário no arquivo de propriedades `dados-teste.properties`. Esse arquivo deve ser colocado no seguinte caminho:

```bash
src/test/resources/dados-teste.properties
```
O arquivo `dados-teste.properties` deve conter as seguintes informações:

```bash
EMAIL_USUARIO=insiraoemail@email.com
SENHA_USUARIO=suaSenha
```

