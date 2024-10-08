package com.vemser.rest.tests.carrinhos;

import com.vemser.rest.client.CarrinhosClient;
import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.LoginModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ExcluirCarrinhoTest {

	private final LoginClient loginClient = new LoginClient();
	private final CarrinhosClient carrinhosClient = new CarrinhosClient();
	private String token;

	@BeforeMethod
	public void setUp() {
		LoginModel login = LoginDataFactory.loginValido();

		token = loginClient.realizarLogin(login)
				.then()
					.extract()
					.path("authorization");
	}

	@Test(description = "CT-001: Deve retornar 200 ao excluir carrinho com sucesso")
	public void testDeveValidarContratoExcluirCarrinhoComSucesso() {

		carrinhosClient.excluirCarrinho(token)
				.then()
					.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/carrinhos/excluir_carrinho.json"));

	}

	@Test(description = "CT-002: Deve retornar 200 ao excluir carrinho com sucesso")
	public void testDeveExcluirCarrinhoComSucesso() {

		carrinhosClient.excluirCarrinho(token)
				.then()
					.statusCode(200);
	}

	@Test(description = "CT-003: Deve retornar 401 ao tentar excluir carrinho sem estar autenticado")
	public void testDeveTentarExcluirCarrinhoSemAutenticacao() {

		carrinhosClient.excluirCarrinho(StringUtils.EMPTY)
				.then()
					.statusCode(401);
	}

}
