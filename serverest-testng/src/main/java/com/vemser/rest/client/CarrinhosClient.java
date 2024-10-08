package com.vemser.rest.client;

import com.vemser.rest.model.CarrinhosModel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CarrinhosClient extends BaseClient {

	private final String CARRINHOS = "/carrinhos";

	public Response BuscarTodosCarrinhos() {

		return

				given()
						.spec(super.set())
				.when()
						.get(CARRINHOS);
	}

	public Response BuscarCarrinhoPorID(String id) {

		return

				given()
						.spec(super.set())
						.pathParam("id", id)
				.when()
						.get(CARRINHOS + "/{id}");
	}

	public Response cadastrarCarrinho(CarrinhosModel carrinho, String token) {

		return

				given()
						.spec(super.set())
						.contentType(ContentType.JSON)
						.header("authorization", token)
						.body(carrinho)
				.when()
						.post(CARRINHOS);
	}

	public Response excluirCarrinho(String token) {
		return
				given()
						.spec(super.set())
						.header("authorization", token)
				.when()
						.delete(CARRINHOS + "/concluir-compra");
	}

	public Response cancelarCompra(String token) {
		return
				given()
						.spec(super.set())
						.header("authorization", token)
				.when()
						.delete(CARRINHOS + "/cancelar-compra");
	}

}
