package com.vemser.rest.tests.carrinhos;

import com.vemser.rest.client.CarrinhosClient;
import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.CarrinhosDataFactory;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.CarrinhosModel;
import com.vemser.rest.model.LoginModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CadastrarCarrinhosTest {

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
	@DisplayName("CT-002: Deve retornar 201 ao cadastrar carrinho com sucesso")
	public void testCadastrarCarrinhoComSucesso() {

		CarrinhosModel carrinho = CarrinhosDataFactory.criarCarrinhoComProdutos(2);

		carrinhosClient.cadastrarCarrinho(carrinho, token)
				.then()
					.statusCode(201);
	}

}
