package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutosClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Testes de listar todos os produtos")
public class ListarTodosProdutosTest {

	private final ProdutosClient produtosClient = new ProdutosClient();

	@Test
	@DisplayName("CT-001: Deve validar contrato listar todos os produtos com sucesso")
	public void testDeveValidarContratoListarTodosProdutosComSucesso() {

		produtosClient.buscarTodosProdutos()
			.then()
				.statusCode(HttpStatus.SC_OK)
				.body(matchesJsonSchemaInClasspath("schemas/produtos/listar_todos_produtos.json"));
	}

	@Test
	@DisplayName("CT-002: Deve retornar 200 ao listar todos os produtos com sucesso")
	public void testListarTodosProdutosComSucesso() {

		produtosClient.buscarTodosProdutos()
				.then()
					.header("Content-Type", "application/json; charset=utf-8")
					.statusCode(200)
					.body("produtos._id", notNullValue());
	}

}
