package com.vemser.rest.tests.carrinhos;

import com.vemser.rest.client.CarrinhosClient;
import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.CarrinhosDataFactory;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.CarrinhosModel;
import com.vemser.rest.model.LoginModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class CadastrarCarrinhosTest {

	private final LoginClient loginClient = new LoginClient();
	private final CarrinhosClient carrinhosClient = new CarrinhosClient();

	private String token;

	@BeforeEach
	public void setUp() {
		LoginModel login = LoginDataFactory.loginValido();

		token =
				loginClient.realizarLogin(login)
					.then()
						.extract()
						.path("authorization");
	}

	@AfterEach
	public void tearDown() {
		carrinhosClient.excluirCarrinho(token)
			.then()
				.statusCode(200);
	}

	@Test
	@DisplayName("CT-001: Deve validar contrato de cadastrar carrinho com sucesso")
	public void testDeveValidarContratoCadastrarCarrinhoComSucesso() {

		CarrinhosModel carrinho = CarrinhosDataFactory.carrinhoComProdutosValido();

		carrinhosClient.cadastrarCarrinho(carrinho, token)
				.then()
					.statusCode(201)
					.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/carrinhos/cadastrar_carrinho.json"));
	}

	@Test
	@DisplayName("CT-002: Deve retornar 201 ao cadastrar carrinho com sucesso")
	public void testDeveCadastrarCarrinhoComSucesso() {

		CarrinhosModel carrinho = CarrinhosDataFactory.carrinhoComProdutosValido();

		carrinhosClient.cadastrarCarrinho(carrinho, token)
			.then()
				.statusCode(201)
				.body("message", equalTo("Cadastro realizado com sucesso"));
	}

	@Test
	@DisplayName("CT-003: Deve retornar 400 ao tentar cadastrar carrinho com produto duplicado")
	public void testDeveCadastrarCarrinhoComProdutosDuplicado() {

		CarrinhosModel carrinho = CarrinhosDataFactory.carrinhoComProdutosDuplicado();

		carrinhosClient.cadastrarCarrinho(carrinho, token)
				.then()
					.statusCode(400)
					.body("message", equalTo("Não é permitido possuir produto duplicado"));
	}

	@Test
	@DisplayName("CT-004: Deve retornar 400 ao tentar cadastrar mais de um carrinho")
	public void testDeveTentarCadastrarMaisDeUmCarrinho() {

		CarrinhosModel carrinho = CarrinhosDataFactory.carrinhoComProdutosValido();
		carrinhosClient.cadastrarCarrinho(carrinho, token)
				.then()
					.statusCode(201);

		carrinhosClient.cadastrarCarrinho(carrinho, token)
				.then()
					.statusCode(400)
					.body("message", equalTo("Não é permitido ter mais de 1 carrinho"));
	}

	@Test
	@DisplayName("CT-005: Deve retornar 400 ao tentar cadastrar carrinho com produto inexistente")
	public void testDeveTentarCadastrarCarrinhoComProdutoInexistente() {

		CarrinhosModel carrinho = CarrinhosDataFactory.carrinhoComProdutosInvalido();

		carrinhosClient.cadastrarCarrinho(carrinho, token)
			.then()
				.statusCode(400)
				.body("message", equalTo("Produto não encontrado"));
	}

	@Test
	@DisplayName("CT-006: Deve retornar 400 ao tentar cadastrar carrinho com produto com quantidade insuficiente")
	public void testDeveTentarCadastrarCarrinhoComProdutoComQuantidadeInsuficiente() {

		CarrinhosModel carrinho = CarrinhosDataFactory.carrinhoComProdutoComQtdInsuficiente();

		carrinhosClient.cadastrarCarrinho(carrinho, token)
				.then()
					.statusCode(400)
					.body("message", equalTo("Produto não possui quantidade suficiente"));
	}

	@Test
	@DisplayName("CT-007: Deve retornar 401 ao tentar cadastrar carrinho sem estar autenticado")
	public void testDeveTentarCadastrarCarrinhoSemAutenticacao() {

		CarrinhosModel carrinho = CarrinhosDataFactory.carrinhoComProdutosValido();

		carrinhosClient.cadastrarCarrinho(carrinho, StringUtils.EMPTY)
				.then()
					.statusCode(401)
					.body("message", equalTo("Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"));
	}

}
