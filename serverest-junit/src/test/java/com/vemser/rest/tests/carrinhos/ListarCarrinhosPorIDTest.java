package com.vemser.rest.tests.carrinhos;

import com.vemser.rest.client.CarrinhosClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Testes de listar carrinhos por ID")
public class ListarCarrinhosPorIDTest {

	private final CarrinhosClient carrinhosClient = new CarrinhosClient();

	private String idCarrinho;

	@BeforeEach
	public void setUp() {
		idCarrinho =
				carrinhosClient.BuscarTodosCarrinhos()
						.then()
							.extract()
							.path("carrinhos[0]._id");

		if (idCarrinho == null) {
			throw new IllegalArgumentException("idCarrinho não pode ser nulo");
		}
	}

	@Test
	@DisplayName("CT-001: Deve validar contrato de listar carrinho por ID com sucesso")
	public void testDeveValidarContratoListarCarrinhoPorIDComSucesso() {

		carrinhosClient.BuscarCarrinhoPorID(idCarrinho)
				.then()
					.statusCode(HttpStatus.SC_OK)
					.body(matchesJsonSchemaInClasspath("schemas/carrinhos/listar_carrinhos_por_id.json"));

	}

	@Test
	@DisplayName("CT-002: Deve retornar 200 ao listar carrinho por ID com sucesso")
	public void testDeveListarCarrinhoPorIDComSucesso() {

		carrinhosClient.BuscarCarrinhoPorID(idCarrinho)
				.then()
					.statusCode(HttpStatus.SC_OK);
	}

	@Test
	@DisplayName("CT-003: Deve retornar 400 ao tentar listar carrinho com ID inválido")
	public void testDeveTentarListarCarrinhoPorComIDInvalido() {

		carrinhosClient.BuscarCarrinhoPorID("idInvalido")
				.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST)
					.body("message", equalTo("Carrinho não encontrado"));
	}

}
