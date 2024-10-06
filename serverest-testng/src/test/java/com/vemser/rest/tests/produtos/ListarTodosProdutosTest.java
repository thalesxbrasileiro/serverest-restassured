package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutosClient;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ListarTodosProdutosTest {

	private final ProdutosClient produtosClient = new ProdutosClient();

	@Test(description = "CT-001: Deve validar contrato listar todos os produtos com sucesso")
	public void testDeveValidarContratoListarTodosProdutosComSucesso() {

		produtosClient.buscarTodosProdutos()
			.then()
				.statusCode(HttpStatus.SC_OK)
				.body(matchesJsonSchemaInClasspath("schemas/produtos/listar_todos_produtos.json"));
	}

	@Test(description = "CT-002: Deve retornar 200 ao listar todos os produtos com sucesso")
	public void testListarTodosProdutosComSucesso() {

		produtosClient.buscarTodosProdutos()
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(200)
				.time(lessThan(3000L))
				.body("quantidade", notNullValue())
				.body("quantidade", greaterThan(0))
				.body("produtos.nome", notNullValue())
				.body("produtos.preco", notNullValue())
				.body("produtos.descricao", notNullValue())
				.body("produtos.quantidade", notNullValue())
				.body("produtos._id", notNullValue());
	}

}
