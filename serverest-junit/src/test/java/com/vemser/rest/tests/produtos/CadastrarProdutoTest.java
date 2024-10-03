package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import com.vemser.rest.model.LoginModel;
import com.vemser.rest.model.ProdutosModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CadastrarProdutoTest {

	private final LoginClient loginClient = new LoginClient();
	private final ProdutosClient produtosClient = new ProdutosClient();
	private String produtoId;
	private String token;

	@BeforeEach
	public void autenticar() {
		LoginModel login = LoginDataFactory.loginValido();

		token =
				loginClient.realizarLogin(login)
					.then()
						.extract()
							.path("authorization");
	}

	@AfterEach
	public void excluirProduto() {
		if (produtoId != null) {
			produtosClient.excluirProduto(produtoId, token)
					.then()
					.statusCode(200);
			produtoId = null;
		}
	}

	@Test
	@DisplayName("Cenário 01: Deve validar contrato cadastrar produtos com sucesso")
	public void testDeveValidarContratoCadastrarProdutosComSucesso() {

		ProdutosModel produto = ProdutosDataFactory.produtoValido();

		produtoId =

				produtosClient.cadastrarProduto(produto, token)
					.then()
						.statusCode(HttpStatus.SC_CREATED)
						.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/produtos/cadastrar_produtos.json"))
						.extract()
							.path("_id");
		;
	}

	@Test
	@DisplayName("Cenário 02: Deve retornar 201 ao cadastrar produto com sucesso")
	public void testCadastrarProdutoComSucesso() {

		ProdutosModel produto = ProdutosDataFactory.produtoValido();

		produtoId =

				produtosClient.cadastrarProduto(produto, token)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(201)
						.body("message", equalTo("Cadastro realizado com sucesso"))
						.body("_id", notNullValue())
						.extract()
							.path("_id");
	}

	@Test
	@DisplayName("Cenário 03: Deve retornar 401 ao tentar cadastrar produto sem estar autenticado")
	public void testTentarCadastrarProdutoSemEstarAutenticado() {

		ProdutosModel produto = ProdutosDataFactory.produtoValido();

		produtosClient.cadastrarProdutoSemAutenticacao(produto)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(401)
				.body("message", equalTo("Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"));
	}

	@Test
	@DisplayName("Cenário 04: Deve retornar 400 ao tentar cadastrar produto sem informar o nome")
	public void testTentarCadastrarProdutoSemInformarOnome() {

		ProdutosModel produto = ProdutosDataFactory.produtoSemNome();

		produtosClient.cadastrarProduto(produto, token)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(400)
				.body("nome", equalTo("nome não pode ficar em branco"));
	}

	@Test
	@DisplayName("Cenário 05: Deve retornar 400 ao tentar cadastrar produto sem informar a descrição")
	public void testTentarCadastrarProdutoComNomeExistente() {

		ProdutosModel produto = ProdutosDataFactory.produtoJaCadastrado();

		produtosClient.cadastrarProduto(produto, token)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(400)
				.body("message", equalTo("Já existe produto com esse nome"));
	}

	@ParameterizedTest
	@MethodSource("com.vemser.rest.data.provider.ProdutosDataProvider#produtoDataProvider")
	@DisplayName("Cenário 06: Deve retornar 400 ao tentar cadastrar produto sem informar a descrição")
	public void testDeveCadastrarProdutosComDataProvider(ProdutosModel produto, String key, String value) {

		produtosClient.cadastrarProduto(produto, token)
			.then()
				.statusCode(400)
				.body(key, Matchers.equalTo(value));
	}

}
