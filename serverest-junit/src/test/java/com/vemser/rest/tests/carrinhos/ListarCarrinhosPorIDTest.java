//package com.vemser.rest.tests.carrinhos;
//
//import com.vemser.rest.client.CarrinhosClient;
//import com.vemser.rest.client.LoginClient;
//import com.vemser.rest.data.factory.CarrinhosDataFactory;
//import com.vemser.rest.data.factory.LoginDataFactory;
//import com.vemser.rest.model.CarrinhosModel;
//import com.vemser.rest.model.LoginModel;
//import org.apache.http.HttpStatus;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//@DisplayName("Testes de listar carrinhos por ID")
//public class ListarCarrinhosPorIDTest {
//
//	private final CarrinhosClient carrinhosClient = new CarrinhosClient();
//	private final LoginClient loginClient = new LoginClient();
//
//	CarrinhosModel carrinho;
//	private String carrinhoId;
//	private String token;
//
//	@BeforeEach
//	public void setUp() {
//		LoginModel login = LoginDataFactory.loginValido();
//
//		token =
//				loginClient.realizarLogin(login)
//						.then()
//						.extract()
//						.path("authorization");
//
//		carrinho = CarrinhosDataFactory.carrinhoValido();
//
//		carrinhoId =
//				carrinhosClient.CadastrarCarrinho(carrinho, token)
//					.then()
//						.statusCode(HttpStatus.SC_CREATED)
//						.extract()
//						.path("idProduto");
//	}
//
//	@Test
//	@DisplayName("CT-002: Deve retornar 200 ao buscar carrinho por ID com sucesso")
//	public void testBuscarCarrinhoPorIDComSucesso() {
//
//		carrinhosClient.BuscarCarrinhoPorID(carrinhoId)
//				.then()
//					.header("Content-Type", "application/json; charset=utf-8")
//					.statusCode(HttpStatus.SC_OK);
//
//
//	}
//}
