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

//	@DisplayName("Cenário 01: Deve validar contrato listar produtos por ID com sucesso")
	@Test
	public void testDeveValidarContratoListarProdutosPorIDComSucesso() {

		String idProduto = ProdutosDataFactory.buscarPrimeiroProdutoId();

		produtosClient.BuscarProdutoPorID(idProduto)
			.then()
				.statusCode(HttpStatus.SC_OK)
				.body(matchesJsonSchemaInClasspath("schemas/produtos/listar_produtos_por_id.json"));
	}

//	@DisplayName("Cenário 02: Deve retornar 200 ao listar produto por ID com sucesso")
	@Test
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

//	@DisplayName("Cenário 03: Deve retornar 400 ao tentar listar produto com ID em branco")
	@Test
	public void testTentarListarProdutoComIDEmBranco() {

		produtosClient.BuscarProdutoPorID(" ")
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(400)
				.body("message", equalTo("Produto não encontrado"));
	}

//	@DisplayName("Cenário 04: Deve retornar 400 ao tentar listar produto com ID inválido")
	@Test
	public void testTentarListarProdutoComIDInvalido() {

		produtosClient.BuscarProdutoPorID("idInvalido")
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(400)
				.body("message", equalTo("Produto não encontrado"));
	}

}
