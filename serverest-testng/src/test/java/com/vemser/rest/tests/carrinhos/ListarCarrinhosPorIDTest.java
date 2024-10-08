package com.vemser.rest.tests.carrinhos;

import com.vemser.rest.client.CarrinhosClient;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class ListarCarrinhosPorIDTest {

	private final CarrinhosClient carrinhosClient = new CarrinhosClient();

	private String idCarrinho;

	@BeforeMethod
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

	@Test(description = "CT-001: Deve validar contrato de listar carrinho por ID com sucesso")
	public void testDeveValidarContratoListarCarrinhoPorIDComSucesso() {

		carrinhosClient.BuscarCarrinhoPorID(idCarrinho)
				.then()
					.statusCode(HttpStatus.SC_OK)
					.body(matchesJsonSchemaInClasspath("schemas/carrinhos/listar_carrinhos_por_id.json"));

	}

	@Test(description = "CT-002: Deve retornar 200 ao listar carrinho por ID com sucesso")
	public void testDeveListarCarrinhoPorIDComSucesso() {

		carrinhosClient.BuscarCarrinhoPorID(idCarrinho)
				.then()
					.statusCode(HttpStatus.SC_OK);
	}

	@Test(description = "CT-003: Deve retornar 400 ao tentar listar carrinho com ID inválido")
	public void testDeveTentarListarCarrinhoPorComIDInvalido() {

		carrinhosClient.BuscarCarrinhoPorID("idInvalido")
				.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST)
					.body("message", equalTo("Carrinho não encontrado"));
	}

}
