package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class ListarProdutoPorIDTest {

	private final ProdutosClient produtosClient = new ProdutosClient();
	private SoftAssert softAssert = new SoftAssert();

	@Test(description = "CT-001: Deve validar contrato listar produtos por ID com sucesso")
	public void testDeveValidarContratoListarProdutosPorIDComSucesso() {

		String idProduto = ProdutosDataFactory.buscarPrimeiroProdutoId();

		produtosClient.BuscarProdutoPorID(idProduto)
			.then()
				.statusCode(HttpStatus.SC_OK)
				.body(matchesJsonSchemaInClasspath("schemas/produtos/listar_produtos_por_id.json"));
	}

	@Test(description = "CT-002 02: Deve retornar 200 ao listar produto por ID com sucesso")
	public void testListarProdutoPorIDComSucesso() {

		String idProduto = ProdutosDataFactory.buscarPrimeiroProdutoId();

		Response response =
				produtosClient.BuscarProdutoPorID(idProduto)
					.then()
						.statusCode(200)
						.extract()
						.response();

		String id = response.path("_id");
		String nome = response.path("nome");
		String descricao = response.path("descricao");
		Integer preco = response.path("preco");

		softAssert.assertEquals(id, response.path("_id"));
		softAssert.assertEquals(nome, response.path("nome"));
		softAssert.assertEquals(descricao, response.path("descricao"));
		softAssert.assertNotNull(preco);
		softAssert.assertAll();
	}

	@Test(description = "CT-003: Deve retornar 400 ao tentar listar produto com ID em branco")
	public void testTentarListarProdutoComIDEmBranco() {

		produtosClient.BuscarProdutoPorID(" ")
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(400)
				.body("message", equalTo("Produto não encontrado"));
	}

	@Test(description = "CT-004: Deve retornar 400 ao tentar listar produto com ID inválido")
	public void testTentarListarProdutoComIDInvalido() {

		produtosClient.BuscarProdutoPorID("idInvalido")
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(400)
				.body("message", equalTo("Produto não encontrado"));
	}

}
