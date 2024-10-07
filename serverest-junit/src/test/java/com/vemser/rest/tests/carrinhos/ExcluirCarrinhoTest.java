package com.vemser.rest.tests.carrinhos;

import com.vemser.rest.client.CarrinhosClient;
import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.LoginModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExcluirCarrinhoTest {

	private final LoginClient loginClient = new LoginClient();
	private final CarrinhosClient carrinhosClient = new CarrinhosClient();
	private String token;

	@BeforeEach
	public void setUp() {
		LoginModel login = LoginDataFactory.loginValido();

		token = loginClient.realizarLogin(login)
				.then()
					.extract()
					.path("authorization");
	}

	@Test
	@DisplayName("CT-002: Deve retornar 200 ao excluir carrinho com sucesso")
	public void testExcluirCarrinhoComSucesso() {

		carrinhosClient.excluirCarrinho(token)
				.then()
					.statusCode(200);
	}

}
