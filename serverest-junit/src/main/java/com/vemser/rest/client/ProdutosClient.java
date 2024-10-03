package com.vemser.rest.client;

import com.vemser.rest.model.ProdutosModel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProdutosClient extends BaseClient{

	private final String PRODUTO = "/produtos";

	public Response cadastrarProduto(ProdutosModel produto, String token) {
		if (token != null) {
			return
					given()
							.spec(super.set())
							.contentType(ContentType.JSON)
							.body(produto)
							.header("authorization", token)
					.when()
							.post("/produtos");
		} else {
			return
					given()
							.spec(super.set())
							.contentType(ContentType.JSON)
							.body(produto)
					.when()
							.post("/produtos");
		}
	}

	public Response cadastrarProdutoSemAutenticacao(ProdutosModel produto) {

		return
				given()
						.spec(super.set())
						.body(produto)
						.contentType(ContentType.JSON)
				.when()
						.post(PRODUTO)
				;
	}

	public Response buscarTodosProdutos() {

		return
				given()
						.spec(super.set())
				.when()
						.get(PRODUTO)
				;
	}

	public Response BuscarProdutoPorID(String id) {

		return
				given()
						.spec(super.set())
						.pathParam("id", id)
				.when()
						.get(PRODUTO + "/{id}")
				;
	}

	public Response atualizarProduto(ProdutosModel produto, String id, String token) {

		return
				given()
						.spec(super.set())
						.contentType(ContentType.JSON)
						.body(produto)
						.pathParam("id", id)
						.header("authorization", token)
				.when()
						.put(PRODUTO + "/{id}");
	}

	public Response excluirProduto(String id, String token) {

		return
				given()
						.spec(super.set())
						.pathParam("id", id)
						.header("authorization", token)
				.when()
						.delete(PRODUTO + "/{id}");
	}

}
